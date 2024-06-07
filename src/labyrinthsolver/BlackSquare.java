package labyrinthsolver;

public class BlackSquare extends Square {

  public BlackSquare(int row, int col, MyMonitor m) {
    super(row, col, m);
  }

  @Override
  public String charToSymbol() {
    return "#";
  }

}
