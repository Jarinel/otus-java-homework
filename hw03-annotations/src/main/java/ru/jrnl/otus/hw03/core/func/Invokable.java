package ru.jrnl.otus.hw03.core.func;

@FunctionalInterface
public interface Invokable<T> {
    void invoke(T object) throws Exception;

    default Invokable<T> compose(Invokable<T> other) {
        return obj -> {
            this.invoke(obj);
            other.invoke(obj);
        };
    }
}
