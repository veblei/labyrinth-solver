package labyrinthsolver;

import javafx.scene.control.Button;

abstract class Square extends Button {
  protected Square[] neighbours = new Square[4];
  protected int col;
  protected int row;
  protected MyMonitor monitor;
  protected LinkedList<Thread> threads = new LinkedList<Thread>();

  protected Square(int r, int c, MyMonitor m) {
    row = r;
    col = c;
    monitor = m;
  }

  abstract String charToSymbol();

  public void neighbourNorth(Square rute) {
    neighbours[0] = rute;
  }

  public void neighbourSouth(Square rute) {
    neighbours[1] = rute;
  }

  public void neighbourEast(Square rute) {
    neighbours[2] = rute;
  }

  public void neighbourWest(Square rute) {
    neighbours[3] = rute;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public void run(Square forrige, String coords) {
    String coordinates = coords + "(" + col + ", " + row + ")";

    // Check if square is already visited. If not, add it to visited.
    for (Square r : monitor.getVisited()) {
      if (r.row == row && r.col == col) {
        return;
      }
    }
    monitor.addVisited(this);
    coordinates += " --> ";

    // Check how many of the neighbour squares are white/valid
    int counter = 0;
    for (int i = 0; i < neighbours.length; i++) {
      if (neighbours[i] != null && neighbours[i].charToSymbol().equals(".") && neighbours[i] != forrige) {
        counter++;
      }
    }

    for (int i = 0; i < neighbours.length; i++) {
      if (neighbours[i] != null && neighbours[i].charToSymbol().equals(".") && neighbours[i] != forrige) {

        // If the square has multiple white/valid neighbours, the current thread will continue for
        // one of them while new threads are created for the other white squares. It's important
        // that the thread is started before the run() method is called.
        if (counter > 1) {
          int continuations = 0;
          if (continuations < (counter - 1)) {
            Thread t = new Thread(new MyRun(monitor, neighbours[i], this, coordinates));
            threads.add(t);
            t.start();
          } else if (continuations == (counter - 1)) {
            neighbours[i].run(this, coordinates);
          }
        }

        // If square only has 1 white/valid square, no new threads will be created
        else if (counter == 1) {
          neighbours[i].run(this, coordinates);
        }

        // If the square has no white/valid neighbours, the thread is ended
        else if (counter < 1) {
          return;
        }
      }
    }

    // Join threads after they are created.
    try {
      for (Thread t : threads) {
        t.join();
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted!");
    }
  }

}
