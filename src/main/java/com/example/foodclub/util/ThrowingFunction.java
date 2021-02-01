package com.example.foodclub.util;

import java.util.function.Function;

import static java.util.Objects.requireNonNull;

@FunctionalInterface
public interface ThrowingFunction<T, R, E extends Exception> {
    R apply(T arg) throws E;

    static <T, R> Function<T, R> unchecked(final ThrowingFunction<? super T, ? extends R, ?> function) {
        requireNonNull(function);
        return t -> {
            try {
                return function.apply(t);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

}