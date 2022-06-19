package ru.jrnl.otus.hw03.core.testcase;

import lombok.Data;
import ru.jrnl.otus.hw03.core.func.Creator;
import ru.jrnl.otus.hw03.core.func.Invokable;

import java.util.function.Supplier;

@Data
public class SimpleTestCase<T> implements TestCase<T> {
    private final Supplier<String> testNameProvider;
    private final Creator<T> creator;
    private final Invokable<T> beforeInvoker;
    private final Invokable<T> testInvoker;
    private final Invokable<T> afterInvoker;

    @Override
    public String getTestName() {
        return testNameProvider.get();
    }

    @Override
    public T create() throws Exception {
        return creator.create();
    }

    @Override
    public void invokeBefore(T object) throws Exception {
        beforeInvoker.invoke(object);
    }

    @Override
    public void invokeTest(T object) throws Exception {
        testInvoker.invoke(object);
    }

    @Override
    public void invokeAfter(T object) throws Exception {
        afterInvoker.invoke(object);
    }
}
