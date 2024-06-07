package labyrinthsolver;

public class InvalidLinkedList extends RuntimeException {
  public InvalidLinkedList(int index) {
    super("Invalid index: " + index);
  }
}
