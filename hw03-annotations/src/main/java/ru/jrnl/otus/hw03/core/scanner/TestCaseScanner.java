package ru.jrnl.otus.hw03.core.scanner;

import ru.jrnl.otus.hw03.core.testcase.TestCase;

import java.util.Collection;

public interface TestCaseScanner {
    <T> Collection<? extends TestCase<T>> scan(Class<T> clazz);
}
