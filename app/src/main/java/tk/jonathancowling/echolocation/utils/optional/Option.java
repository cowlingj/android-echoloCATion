package tk.jonathancowling.echolocation.utils.optional;

/**
 * Created by jonathan on 17/12/17.
 */

public interface Option<T> {
    public T get();
    public T getOrElse(T other);
    public boolean err();
    public Option<T> add(T item);

}
