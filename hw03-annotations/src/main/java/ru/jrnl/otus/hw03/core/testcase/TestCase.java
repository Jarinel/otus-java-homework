package ru.jrnl.otus.hw03.core.testcase;

public interface TestCase<T> {
    String getTestName();
    T create() throws Exception;
    void invokeBefore(T object) throws Exception;
    void invokeTest(T object) throws Exception;
    void invokeAfter(T object) throws Exception;
}
