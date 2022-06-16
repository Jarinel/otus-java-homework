package ru.jrnl.otus.hw03.core;

import lombok.extern.slf4j.Slf4j;
import ru.jrnl.otus.hw03.core.runner.SimpleTestCaseRunner;
import ru.jrnl.otus.hw03.core.scanner.SimpleTestCaseScanner;

@Slf4j
public class SimpleTestRunner {
    public <T> Statistics runTests(Class<T> clazz) {
        SimpleTestCaseRunner caseRunner = new SimpleTestCaseRunner();
        Statistics testsStatistics = new SimpleTestCaseScanner()
                .scan(clazz)
                .stream()
                .map(caseRunner::runTest)
                .reduce(
                        new Statistics(),
                        (stat, res) -> res.isSucceeded() ? stat.addSucceededTest() : stat.addFailedTest(),
                        Statistics::combine
                );

        printStatistics(testsStatistics);

        return testsStatistics;
    }

    private void printStatistics(Statistics statistics) {
        log.info("########################");
        log.info("> Test run:       " + statistics.getTestsRun());
        log.info("> Test succeeded: " + statistics.getTestsSucceeded());
        log.info("> Test failed:    " + statistics.getTestsFailed());
    }
}
