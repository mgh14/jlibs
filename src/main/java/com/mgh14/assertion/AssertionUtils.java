package com.mgh14.assertion;

import java.text.MessageFormat;
import java.util.function.Predicate;
import java.util.function.Supplier;

@SuppressWarnings("WeakerAccess")
public final class AssertionUtils
{
    private AssertionUtils()
    {
    }

    public static <T extends RuntimeException> void require(boolean expression, Supplier<T> exceptionSupplier)
    {
        if (!expression)
        {
            throw exceptionSupplier.get();
        }
    }

    public static <S, T extends RuntimeException> void require(Predicate<S> predicate, S input,
                                                               Supplier<T> exceptionSupplier)
    {
        if (!predicate.test(input))
        {
            throw exceptionSupplier.get();
        }
    }

    public static <S> S requireNonNull(S object, String message)
    {
        return requireNonNull(object, () -> new IllegalArgumentException(message));
    }

    public static <S> S requireNonNull(S object, Class<S> clazz)
    {
        return requireNonNull(object, () ->
                new IllegalArgumentException(MessageFormat.format("[{0}] cannot be null",
                        clazz.getSimpleName())));
    }

    public static <S, T extends RuntimeException> S requireNonNull(S object, Supplier<T> exceptionSupplier)
    {
        require(object != null, exceptionSupplier);
        return object;
    }
}
