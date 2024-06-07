package labyrinthsolver;

import java.util.concurrent.locks.*;

public class MyMonitor {
  private LinkedList<String> routes = new LinkedList<String>();
  private LinkedList<Square> openings = new LinkedList<Square>();
  private LinkedList<Square> visited = new LinkedList<Square>();
  private Lock lock = new ReentrantLock();

  public void addRoute(String route, Square opening) {
    lock.lock();
    try {
      for (int i = 0; i < routes.size(); i++) {
        // If the route has the same opening as another route, keep the shortest route
        if (opening.getCol() == openings.get(i).getCol() && opening.getRow() == openings.get(i).getRow()) {
          if (route.length() < routes.get(i).length()) {
            routes.remove(i);
            routes.add(route);
            return;
          } else if (route.length() > routes.get(i).length()) {
            return;
          }
        }
      }
      routes.add(route);
      openings.add(opening);
    } finally {
      lock.unlock();
    }
  }

  public LinkedList<String> getRoutes() {
    return routes;
  }

  public void addVisited(Square rute) {
    lock.lock();
    try {
      visited.add(rute);
    } finally {
      lock.unlock();
    }
  }

  public LinkedList<Square> getVisited() {
    lock.lock();
    try {
      return visited;
    } finally {
      lock.unlock();
    }
  }

  public void resetLists() {
    while (routes.size() > 0) {
      routes.remove();
    }
    while (openings.size() > 0) {
      openings.remove();
    }
    while (visited.size() > 0) {
      visited.remove();
    }
  }

}
