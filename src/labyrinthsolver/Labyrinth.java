package labyrinthsolver;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.event.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Labyrinth {
  private Square[][] gridArray;
  private int row;
  private int col;
  private static MyMonitor monitor = new MyMonitor();
  private Text statusInfo;
  private Text routeInfo;

  private Labyrinth(Square[][] arr, int r, int c) {
    gridArray = arr;
    row = r;
    col = c;
    statusInfo = new Text("Choose a route");
    routeInfo = new Text("");
  }

  public int getRowAmount() {
    return row;
  }

  public int getColAmount() {
    return col;
  }

  public static Labyrinth readFile(File file) throws FileNotFoundException {
    Scanner sc = new Scanner(file);
    int r = Integer.parseInt(sc.next());
    int c = Integer.parseInt(sc.next());
    Square[][] arr = new Square[r][c];
    sc.nextLine();
    for (int i = 0; i < r; i++) {
      String[] charArray = sc.nextLine().split("");
      for (int j = 0; j < c; j++) {
        if (charArray[j].equals("#")) {
          arr[i][j] = new BlackSquare(i, j, monitor);
        } else if (charArray[j].equals(".")) {
          if (i == 0 || i == (r - 1) || j == 0 || j == (c - 1)) {
            arr[i][j] = new Opening(i, j, monitor);
          } else {
            arr[i][j] = new WhiteSquare(i, j, monitor);
          }
        }
      }
    }
    sc.close();
    // Gives every square a referanse to their neighbours
    for (int i = 0; i < r; i++) {
      for (int j = 0; j < c; j++) {
        Square square = arr[i][j];
        if (i == 0 || i == (r - 1)) {
          if (i == 0) {
            square.neighbourNorth(null);
            square.neighbourSouth(arr[i + 1][j]);
          } else if (i == (r - 1)) {
            square.neighbourNorth(arr[i - 1][j]);
            square.neighbourSouth(null);
          }
          if (j == 0) {
            square.neighbourEast(arr[i][j + 1]);
            square.neighbourWest(null);
          } else if (j == (c - 1)) {
            square.neighbourEast(null);
            square.neighbourWest(arr[i][j - 1]);
          } else {
            square.neighbourEast(arr[i][j + 1]);
            square.neighbourWest(arr[i][j - 1]);
          }
        } else if (j == 0 || j == (c - 1)) {
          if (j == 0) {
            square.neighbourEast(arr[i][j + 1]);
            square.neighbourWest(null);
          } else if (j == (c - 1)) {
            square.neighbourEast(null);
            square.neighbourWest(arr[i][j - 1]);
          }
          if (i == 0) {
            square.neighbourNorth(null);
            square.neighbourSouth(arr[i + 1][j]);
          } else if (i == (r - 1)) {
            square.neighbourNorth(arr[i - 1][j]);
            square.neighbourSouth(null);
          } else {
            square.neighbourNorth(arr[i - 1][j]);
            square.neighbourSouth(arr[i + 1][j]);
          }
        } else {
          square.neighbourNorth(arr[i - 1][j]);
          square.neighbourSouth(arr[i + 1][j]);
          square.neighbourEast(arr[i][j + 1]);
          square.neighbourWest(arr[i][j - 1]);
        }
      }
    }
    return new Labyrinth(arr, r, c);
  }

  class BlackSquareHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      statusInfo.setText("Choose a white square");
    }
  }

  class WhiteSquareHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent ae) {
      resetSquares();
      monitor.resetLists();
      Square square = (Square) ae.getSource();
      try {
        Thread t = new Thread(new MyRun(monitor, square, null, ""));
        t.start();
        t.join();
      } catch (InterruptedException ie) {
        System.out.println(ie);
      }
      showRoute(0);
    }
  }

  public void showRoute(int teller) {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (gridArray[i][j].charToSymbol().equals(".")) {
          gridArray[i][j].setStyle("-fx-background-color: white;");
        }
      }
    }
    LinkedList<String> routes = monitor.getRoutes();
    if (routes.size() != 0) {
      statusInfo.setText("Found " + routes.size() + " route(s)");
      routeInfo.setText("Route " + (teller + 1));
      String route = routes.get(teller);
      boolean[][] solution = createSolutionTable(route, col, row);
      for (int i = 0; i < row; i++) {
        for (int j = 0; j < col; j++) {
          if (solution[i][j] == true) {
            gridArray[i][j].setStyle("-fx-background-color: blue;");
          }
        }
      }
    } else {
      statusInfo.setText("Found no routes");
    }
  }

  public int getRoutesAmount() {
    return monitor.getRoutes().size();
  }

  public Text getStatusInfo() {
    return statusInfo;
  }

  public Text getRouteInfo() {
    return routeInfo;
  }

  public GridPane createGrid() {
    WhiteSquareHandler clickWhite = new WhiteSquareHandler();
    BlackSquareHandler clickBlack = new BlackSquareHandler();
    GridPane grid = new GridPane();
    grid.setGridLinesVisible(true);
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        grid.add(gridArray[i][j], j, i);
        if (gridArray[i][j].charToSymbol().equals("#")) {
          gridArray[i][j].setOnAction(clickBlack);
          gridArray[i][j].setStyle("-fx-background-color: black;");
        } else if (gridArray[i][j].charToSymbol().equals(".")) {
          gridArray[i][j].setOnAction(clickWhite);
          gridArray[i][j].setStyle("-fx-background-color: white;");
        }
        gridArray[i][j].setPrefSize(20, 20);
      }
    }
    return grid;
  }

  public void resetSquares() {
    for (int i = 0; i < row; i++) {
      for (int j = 0; j < col; j++) {
        if (gridArray[i][j].charToSymbol().equals(".")) {
          gridArray[i][j].setStyle("-fx-background-color: white;");
        }
      }
    }
    routeInfo.setText("");
    monitor.resetLists();
  }

  private static boolean[][] createSolutionTable(String solutionString, int w, int h) {
    boolean[][] solutionTable = new boolean[h][w];
    java.util.regex.Pattern p = java.util.regex.Pattern.compile("\\(([0-9]+),([0-9]+)\\)");
    java.util.regex.Matcher m = p.matcher(solutionString.replaceAll("\\s", ""));
    while (m.find()) {
      int x = Integer.parseInt(m.group(1));
      int y = Integer.parseInt(m.group(2));
      solutionTable[y][x] = true;
    }
    return solutionTable;
  }

}
