package ru.jrnl.otus.hw03.core.runner;

import ru.jrnl.otus.hw03.core.testcase.TestCase;

public interface TestCaseRunner {
    <T> TestResult runTest(TestCase<T> testCase);
}
