package labyrinthsolver;

public class MyRun implements Runnable {
  private MyMonitor monitor;
  private Square startSquare;
  private Square previousSquare;
  private String coordinates;

  public MyRun(MyMonitor m, Square start, Square prev, String coords) {
    monitor = m;
    startSquare = start;
    previousSquare = prev;
    coordinates = coords;
  }

  @Override
  public void run() {
     // Every thread calls run() recursively and have an overview of
     // already added routes and visited squares through the monitor
    startSquare.run(previousSquare, coordinates);
  }

}
