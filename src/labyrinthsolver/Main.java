package labyrinthsolver;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;
import javafx.geometry.Insets;

import java.io.File;

public class Main extends Application {
  private Labyrinth labyrinth = null;
  private Text statusInfo;
  private static int counter = 1;
  private Text routeInfo;

  class ResetHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      labyrinth.resetSquares();
      statusInfo.setText("Choose a square");
    }
  }

  class RouteHandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      if (counter == labyrinth.getRoutesAmount()) {
        counter = 0;
      }
      labyrinth.showRoute(counter);
      counter++;
    }
  }

  @Override
  public void start(Stage stage) {
    FileChooser fc = new FileChooser();
    fc.setInitialDirectory(new File("./inputs"));
    File file = fc.showOpenDialog(stage);

    try {
      labyrinth = Labyrinth.readFile(file);
    } catch (Exception e) {
      System.out.println("ERROR: Could not read from " + file.getPath());
      System.exit(1);
    }
    
    Button resetButton = new Button("Reset");
    resetButton.setLayoutX(10);
    resetButton.setLayoutY(10);
    ResetHandler reset = new ResetHandler();
    resetButton.setOnAction(reset);
    
    Button otherRouteButton = new Button("Switch route");
    otherRouteButton.setLayoutX(10);
    otherRouteButton.setLayoutY(40);
    RouteHandler otherRoute = new RouteHandler();
    otherRouteButton.setOnAction(otherRoute);
    
    statusInfo = labyrinth.getStatusInfo();
    statusInfo.setFont(new Font(15));
    statusInfo.setLayoutX(10);
    statusInfo.setLayoutY(85);

    routeInfo = labyrinth.getRouteInfo();
    routeInfo.setFont(new Font(15));
    routeInfo.setLayoutX(10);
    routeInfo.setLayoutY(110);

    GridPane grid = labyrinth.createGrid();
    grid.setLayoutX(0);
    grid.setLayoutY(125);
    grid.setPadding(new Insets(10,10,10,10));

    Pane pane = new Pane();
    pane.getChildren().add(grid);
    pane.getChildren().add(resetButton);
    pane.getChildren().add(otherRouteButton);
    pane.getChildren().add(routeInfo);
    pane.getChildren().add(statusInfo);
    pane.setStyle("-fx-background-color: lightskyblue;");

    Scene scene = new Scene(pane);

    stage.setTitle("LABYRINTH");
    stage.setScene(scene);
    stage.show();
  }

}
