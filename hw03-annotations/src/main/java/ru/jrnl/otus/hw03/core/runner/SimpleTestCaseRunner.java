package ru.jrnl.otus.hw03.core.runner;

import lombok.extern.slf4j.Slf4j;
import ru.jrnl.otus.hw03.core.exception.FailFastTestResultException;
import ru.jrnl.otus.hw03.core.testcase.TestCase;
import ru.jrnl.otus.hw03.core.util.ExceptionUtil;

import static ru.jrnl.otus.hw03.core.util.ExceptionUtil.unwrapRealException;

@Slf4j
public class SimpleTestCaseRunner implements TestCaseRunner {
    @Override
    public <T> TestResult runTest(TestCase<T> testCase) {
        log.info(">>> Starting test: " + testCase.getTestName());
        TestResult result = runPhases(testCase);
        log.info("<<< Test ended. Result: " + (result.isSucceeded() ? "success" : "fail"));

        return result;
    }

    private <T> TestResult runPhases(TestCase<T> testCase) {
        try {
            T testObject = instantiatePhase(testCase);

            runBeforePhase(testCase, testObject);
            runTestPhase(testCase, testObject);
            runAfterPhase(testCase, testObject);

            return TestResult.success();
        } catch (FailFastTestResultException e) {
            log.info("> Fail reason: ", unwrapRealException(e.getResult().getFailReason()));
            return e.getResult();
        }
    }

    private <T> T instantiatePhase(TestCase<T> testCase) {
        try {
            return testCase.create();
        } catch (Exception e) {
            log.error("> Failed to create new test instance");
            throw new FailFastTestResultException(TestResult.fail(unwrapRealException(e)));
        }
    }

    private <T> void runBeforePhase(TestCase<T> testCase, T testObject) {
        try {
            testCase.invokeBefore(testObject);
        } catch (Exception e) {
            log.error("> Failed to run before method(s)");
            throw new FailFastTestResultException(TestResult.fail(unwrapRealException(e)));
        }
    }

    private <T> void runTestPhase(TestCase<T> testCase, T testObject) {
        try {
            testCase.invokeTest(testObject);
        } catch (Exception e) {
            throw new FailFastTestResultException(TestResult.fail(unwrapRealException(e)));
        }
    }

    private <T> void runAfterPhase(TestCase<T> testCase, T testObject) {
        try {
            testCase.invokeAfter(testObject);
        } catch (Exception e) {
            log.warn("> Failed to run after method(s)", unwrapRealException(e));
        }
    }
}
