package com.newsnow.platform.imagerescale.core.ports.api;

import io.vavr.control.Either;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public final class UseCaseResult<T> {

    private final Either<List<String>, T> value;

    private UseCaseResult(Either<List<String>, T> value) {
        this.value = value;
    }

    public static <T> UseCaseResult<T> success(T value) {
        return new UseCaseResult<>(Either.right(value));
    }

    public static <T> UseCaseResult<T> failure(String... errors) {
        return new UseCaseResult<>(Either.left(Arrays.stream(errors).toList()));
    }

    public boolean hasErrors() {
        return value.isLeft() && !value.getLeft().isEmpty();
    }

    public T get() {
        return value.get();
    }

    public void consumeErrors(Consumer<String> errorConsumer) {
        if (!value.isLeft()) return;

        value.getLeft().forEach(errorConsumer);
    }
}
