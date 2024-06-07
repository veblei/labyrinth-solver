package labyrinthsolver;

public interface List<T> extends Iterable<T> {
  public int size();

  public void add(int post, T x);

  public void add(T x);

  public void set(int pos, T x);

  public T get(int pos);

  public T remove(int pos);

  public T remove();
}
