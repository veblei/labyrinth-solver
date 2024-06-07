package labyrinthsolver;

public class Opening extends WhiteSquare {

  public Opening(int row, int col, MyMonitor m) {
    super(row, col, m);
  }


  // If the square is an opening, it means that a route has been found.
  // The route is added to the monitor overview of routes.
  @Override
  public void run(Square previous, String coords) {
    String coordinates = coords + "(" + col + ", " + row + ")\n";
    monitor.addRoute(coordinates, this);
  }

}
