package labyrinthsolver;

public class WhiteSquare extends Square {

  public WhiteSquare(int rad, int kol, MyMonitor m) {
    super(rad, kol, m);
  }

  @Override
  public String charToSymbol() {
    return ".";
  }

}
