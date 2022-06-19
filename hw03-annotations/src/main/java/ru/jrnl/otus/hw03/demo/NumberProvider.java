package ru.jrnl.otus.hw03.demo;

import lombok.Data;

@Data
public class NumberProvider {
    private final int number;

    public int getNumber() {
        return Math.min(number, 30);
    }
}
