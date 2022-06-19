package ru.jrnl.otus.hw03.core.scanner;

import lombok.extern.slf4j.Slf4j;
import ru.jrnl.otus.hw03.core.annotation.After;
import ru.jrnl.otus.hw03.core.annotation.Before;
import ru.jrnl.otus.hw03.core.annotation.Test;
import ru.jrnl.otus.hw03.core.func.Creator;
import ru.jrnl.otus.hw03.core.func.Invokable;
import ru.jrnl.otus.hw03.core.testcase.SimpleTestCase;
import ru.jrnl.otus.hw03.core.testcase.TestCase;
import ru.jrnl.otus.hw03.core.util.Pair;
import ru.jrnl.otus.hw03.core.exception.InvalidStructureException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class SimpleTestCaseScanner implements TestCaseScanner {
    private static final Set<Class<? extends Annotation>> ANNOTATIONS_TO_SCAN = Set.of(
            Before.class,
            Test.class,
            After.class
    );

    @Override
    public <T> Collection<? extends TestCase<T>> scan(Class<T> clazz) {
        Objects.requireNonNull(clazz);

        @SuppressWarnings("unchecked")
        Constructor<T> constructor = (Constructor<T>) getNoArgsConstructor(clazz);
        Map<Class<? extends Annotation>, List<Method>> annotationToMethodMap = getAnnotationToMethodMap(clazz);
        return createTestCases(constructor, annotationToMethodMap);
    }

    private <T> Constructor<?> getNoArgsConstructor(Class<T> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors())
                .filter(c -> c.getParameterCount() == 0)
                .findFirst()
                .orElseThrow(() -> new InvalidStructureException("Test class must have constructor with no parameters"));
    }

    private <T> Map<Class<? extends Annotation>, List<Method>> getAnnotationToMethodMap(Class<T> clazz) {
        return Arrays.stream(clazz.getMethods())
                .flatMap(this::filterLimitations)
                .map(this::toAnnotationWithMethodPair)
                .collect(Collectors.groupingBy(
                        Pair::getLeft,
                        Collectors.mapping(
                                Pair::getRight,
                                Collectors.toList()
                        )
                ));
    }

    private <T> Collection<? extends TestCase<T>> createTestCases(
            Constructor<T> constructor,
            Map<Class<? extends Annotation>, List<Method>> annotationToMethodMap
    ) {
        Creator<T> creator = constructor::newInstance;
        Invokable<T> composedBefore = composeMethodCalls(
                annotationToMethodMap.getOrDefault(Before.class, Collections.emptyList())
        );
        Invokable<T> composedAfter = composeMethodCalls(
                annotationToMethodMap.getOrDefault(After.class, Collections.emptyList())
        );

        return annotationToMethodMap.getOrDefault(Test.class, Collections.emptyList()).stream()
                .map(method -> new SimpleTestCase<>(
                        method::getName,
                        creator,
                        composedBefore,
                        method::invoke,
                        composedAfter
                ))
                .toList();
    }

    private <T> Invokable<T> composeMethodCalls(List<Method> methods) {
        return methods.stream()
                .map(method -> (Invokable<T>) method::invoke)
                .reduce(Invokable::compose)
                .orElse(obj -> {});
    }

    private Stream<Method> filterLimitations(Method method) {
        List<Annotation> annotations = Arrays.stream(method.getDeclaredAnnotations())
                .filter(annotation -> ANNOTATIONS_TO_SCAN.contains(annotation.annotationType()))
                .toList();

        if (annotations.size() == 0) {
            log.debug("Ignoring method " + method.getName() + " due to it has no testing annotations on it");
            return Stream.empty();
        }

        if (annotations.size() > 1) {
            log.warn(
                    "Only one annotation must be present ("
                            + ANNOTATIONS_TO_SCAN.stream()
                            .map(a -> "@" + a.getSimpleName())
                            .collect(Collectors.joining(" or "))
                            + "). Method " + method.getName() + " will be ignored"
            );
            return Stream.empty();
        }

        if (method.getParameterCount() > 0) {
            log.warn("Annotated test methods must not have any arguments. Method " + method.getName() + " will be ignored");
            return Stream.empty();
        }

        if (!Modifier.isPublic(method.getModifiers()) || Modifier.isStatic(method.getModifiers())) {
            log.warn("Annotated test methods must be public and non-static. Method " + method.getName() + " will be ignored");
            return Stream.empty();
        }

        return Stream.of(method);
    }

    private Pair<Class<? extends Annotation>, Method> toAnnotationWithMethodPair(Method method) {
        Annotation testingAnnotation = Arrays.stream(method.getDeclaredAnnotations())
                .filter(annotation -> ANNOTATIONS_TO_SCAN.contains(annotation.annotationType()))
                .findFirst()
                .orElse(null);

        //noinspection ConstantConditions
        return new Pair<>(
                testingAnnotation.annotationType(),
                method
        );
    }
}
