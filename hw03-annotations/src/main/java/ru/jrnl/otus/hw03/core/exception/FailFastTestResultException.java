package ru.jrnl.otus.hw03.core.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.jrnl.otus.hw03.core.runner.TestResult;

@Getter
@AllArgsConstructor
public class FailFastTestResultException extends RuntimeException {
    private final TestResult result;
}
