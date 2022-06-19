package ru.jrnl.otus.hw03.core.runner;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TestResult {
    private final boolean succeeded;
    private final Throwable failReason;

    public static TestResult success() {
        return new TestResult(true, null);
    }

    public static TestResult fail(Throwable reason) {
        return new TestResult(false, reason);
    }
}
