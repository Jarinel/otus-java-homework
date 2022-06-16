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

    private Random random = new Random(ZonedDateTime.now().toEpochSecond());
    private Integer data;

    @Before
    public void before() {
        log.info("Running before method");
        data = random.nextInt(1000);
    }

    @Test
    public void test() {
        log.info("Running test");
        log.info("data = " + data);
    }

    @Test
    public void someVeryImportantTest() {
        log.info("Very important test");
    }

    @Test
    public void testToFail() {
        throw new RuntimeException("This should fail");
    }

    @After
    public void after() {
        log.info("Running after method");
        data = 0;
        log.info("data = " + data);
    }
}
