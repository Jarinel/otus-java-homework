package ru.jrnl.otus.hw03.core.util;

import java.lang.reflect.InvocationTargetException;

public class ExceptionUtil {
    public static Throwable unwrapRealException(Throwable exception) {
        if (exception instanceof InvocationTargetException ite) {
            return ite.getTargetException();
        }

        return exception;
    }
}
