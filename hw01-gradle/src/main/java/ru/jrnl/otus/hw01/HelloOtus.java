package ru.jrnl.otus.hw01;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class HelloOtus {
    public static final Logger log = LoggerFactory.getLogger(HelloOtus.class);

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("...", "homework", "Otus", "Doing");
        log.info("Direct order:");
        log.info(list.toString());

        List<String> reversedList = Lists.reverse(list);
        log.info("Reversed order:");
        log.info(reversedList.toString());
    }
}
