package ru.jrnl.otus.hw03.core.func;

@FunctionalInterface
public interface Creator<T> {
    T create() throws Exception;
}
