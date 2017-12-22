package tk.jonathancowling.echolocation.utils.optional;

import java.util.NoSuchElementException;

/**
 * Created by jonathan on 17/12/17.
 */

public class None<T> implements Option<T> {
    @Override
    public T get() throws NoSuchElementException {
        throw new NoSuchElementException();
    }

    @Override
    public T getOrElse(T other) {
        return other;
    }

    @Override
    public boolean err() {
        return true;
    }

    @Override
    public None<T> add(T other){
        return this;
    }
}
