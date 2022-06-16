package ru.jrnl.otus.hw03.demo;

import lombok.extern.slf4j.Slf4j;
import ru.jrnl.otus.hw03.core.SimpleTestRunner;
import ru.jrnl.otus.hw03.core.annotation.After;
import ru.jrnl.otus.hw03.core.annotation.Before;
import ru.jrnl.otus.hw03.core.annotation.Test;

import java.time.ZonedDateTime;
import java.util.Random;

@Slf4j
public class DemoTest {
    public static void main(String[] args) {
        new SimpleTestRunner().runTests(DemoTest.class);
    }

    private final Random random = new Random(ZonedDateTime.now().toEpochSecond());
    private Integer data;

    @Before
    public void before() {
        log.info("Running before method");
        data = random.nextInt(1000);
        log.info("data = {}", data);
    }

    @Before
    public void before2() {
        log.info("Yet another @Before method");
    }

    @Test
    public void test() {
        NumberProvider provider = new NumberProvider(17);
        if (provider.getNumber() != 17) {
            throw new RuntimeException("Provider must return cached number");
        }
    }

    @Test
    public void someVeryImportantTest() {
        log.info("Very important test");
    }

    @Test
    public void testToFail() {
        NumberProvider provider = new NumberProvider(32);
        if (provider.getNumber() != 32) {
            throw new RuntimeException("Provider must return cached number");
        }
    }

    @Test
    private void privateTest() {
        log.info("This test shouldn't be run due to it is private");
    }

    @Test
    public static void staticTest() {
        log.info("This test shouldn't be run due to it is static");
    }

    @Test
    public String weirdTest() {
        String s = "It's a really weird test, but still test";
        log.info(s);
        return s;
    }

    @Test
    @After
    @Before
    public void annotationMix() {
        log.info("This test shouldn't be run due to it has too much test annotations");
    }

    public void notATest() {
        log.info("This method shouldn't be run due to it is not a test method");
    }

    @Test
    public void testWithParams(int value) {
        log.info("This test shouldn't be run due to it requires arguments being passed");
    }

    @After
    public void after() {
        log.info("Running after method");
        data = 0;
        log.info("data = {}", data);
    }
}
