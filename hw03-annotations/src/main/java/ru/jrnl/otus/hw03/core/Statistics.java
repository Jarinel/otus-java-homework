package ru.jrnl.otus.hw03.core;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class Statistics {
    private int testsRun = 0;
    private int testsSucceeded = 0;
    private int testsFailed = 0;

    public Statistics addSucceededTest() {
        this.testsRun += 1;
        this.testsSucceeded += 1;

        return this;
    }

    public Statistics addFailedTest() {
        this.testsRun += 1;
        this.testsFailed += 1;

        return this;
    }

    public Statistics combine(Statistics other) {
        this.testsRun += other.testsRun;
        this.testsSucceeded += other.testsSucceeded;
        this.testsFailed += other.testsFailed;

        return this;
    }
}
