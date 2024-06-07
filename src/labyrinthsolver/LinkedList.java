package labyrinthsolver;

import java.util.Iterator;

public class LinkedList<T> implements List<T> {

  class Node {
    T data;
    Node next;
    Node previous;

    Node(T x) {
      data = x;
    }
  }

  class LenkelisteIterator implements Iterator<T> {
    Node node = start;

    // Return true if node has next
    @Override
    public boolean hasNext() {
      if (node == null) {
        return false;
      } else {
        return true;
      }
    }

    // If node has next, make the node pointer point to next
    @Override
    public T next() {
      if (hasNext()) {
        T data = node.data;
        node = node.next;
        return data;
      } else {
        return null;
      }
    }

  }

  protected Node start;
  protected Node end;
  protected int counter = 0;

  // Add node to end of list
  @Override
  public void add(T x) {
    Node newNode = new Node(x);
    // If list is empty, the node is both start and end
    if (counter == 0) {
      start = newNode;
      end = newNode;
    } else {
      end.next = newNode;
      newNode.previous = end;
      end = newNode;
    }
    counter++;
  }

  // Removes first node in list
  @Override
  public T remove() {
    // Throw exception if list is empty
    if (counter == 0) {
      throw new InvalidLinkedList(0);
    } else {
      Node node = start;
      start = start.next;
      counter--;
      return node.data;
    }
  }


  // Add node to a specific position and override existing node in that position
  @Override
  public void set(int pos, T x) {
    Node newNode = new Node(x);
    // Throw exception if given index does not fit in list
    if (pos < 0 || counter == 0 || pos >= counter && pos != 0) {
      throw new InvalidLinkedList(pos);
    } else {
      // If index is null, set node at start
      if (pos == 0) {
        newNode.next = start.next;
        start.next.previous = newNode;
        start = newNode;
        // If index is equal to length of list, set node at end
      } else if (pos == (counter - 1)) {
        end.previous.next = newNode;
        newNode.previous = end.previous;
        end = newNode;
      } else {
        Node node = start;
        for (int i = 0; i < pos; i++) {
          node = node.next;
        }
        // Update pointers
        node.next.previous = newNode;
        node.previous.next = newNode;
        newNode.next = node.next;
        newNode.previous = node.previous;
      }
    }
  }

  // Add node to list at specific postion and push existing node backwards in list
  public void add(int pos, T x) {
    Node newNode = new Node(x);
    // Throw exception if given index does not fit in list
    if (pos < 0 || pos != 0 && pos > counter) {
      throw new InvalidLinkedList(pos);
    } else {
      // If index and counter is 0, the node is both start and end
      if (pos == 0 && counter == 0) {
        end = newNode;
        start = newNode;
        // If index is 0, add node to start
      } else if (pos == 0) {
        newNode.next = start;
        start.previous = newNode;
        start = newNode;
        // If index is equal to counter, add node to end
      } else if (pos == counter) {
        end.next = newNode;
        newNode.previous = end;
        end = newNode;
      } else {
        Node node = start;
        for (int i = 0; i < pos; i++) {
          node = node.next;
        }
        // Update pointers
        newNode.next = node;
        newNode.previous = node.previous;
        node.previous.next = newNode;
        node.previous = newNode;
      }
      counter++;
    }
  }

  // Remove node at specific index
  public T remove(int pos) {
    // Throw exception if invalid index
    if (counter == 0 || pos >= counter && pos != 0 || pos < 0) {
      throw new InvalidLinkedList(pos);
    } else {
      // If given index is 0, and there is only 1 node in list, remove both start and end
      if (counter == 1 && pos == 0) {
        Node remove = start;
        end = null;
        start = null;
        counter--;
        return remove.data;
        // If index is 0, but there is multiple nodes in list, remove start node
      } else if (pos == 0) {
        Node remove = start;
        start = start.next;
        counter--;
        return remove.data;
        // If index is equal to counter, remove end node
      } else if (pos == (counter - 1)) {
        Node remove = end;
        end = end.previous;
        counter--;
        return remove.data;
      } else {
        Node node = start;
        for (int i = 0; i < pos; i++) {
          node = node.next;
        }
        // Update pointers
        node.previous.next = node.next;
        node.next.previous = node.previous;
        counter--;
        return node.data;
      }
    }
  }

  // Get amount of nodes in list
  @Override
  public int size() {
    return counter;
  }

  // Get node at given position
  @Override
  public T get(int pos) {
    // Throw exception if invalid index
    if (counter == 0 || pos >= counter && pos != 0 || pos < 0) {
      throw new InvalidLinkedList(pos);
    } else {
      // If index is 0, return start
      if (pos == 0) {
        return start.data;
      } else {
        Node node = start;
        // Get node at given position
        for (int i = 0; i < pos; i++) {
          node = node.next;
        }
        return node.data;
      }
    }
  }

  @Override
  public Iterator<T> iterator() {
    return new LenkelisteIterator();
  }

}
