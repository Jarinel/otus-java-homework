package ru.jrnl.otus.hw03.core.util;

import lombok.Data;

@Data
public class Pair<L, R> {
    private final L left;
    private final R right;
}
