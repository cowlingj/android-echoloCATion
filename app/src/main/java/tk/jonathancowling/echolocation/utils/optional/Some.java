package tk.jonathancowling.echolocation.utils.optional;

/**
 * Created by jonathan on 17/12/17.
 */

public class Some<T> implements Option<T> {

    private T data;

    public Some(){
        data = null;
    }

    public Some(T t){
        data = t;
    }

    @Override
    public T get() {
        return data;
    }

    @Override
    public T getOrElse(T other) {
        return data;
    }

    @Override
    public boolean err() {
        return false;
    }

    @Override
    public Some<T> add(T item){
        return new Some<T>(item);
    }
}
