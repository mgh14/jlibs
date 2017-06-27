package com.mgh14.maybe;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * TODO: Document
 */
public class Maybe<T> {

    public static void main(String[] args) {
        Integer x = 1;
        Integer y = null;

        if (x != null) {
            if (y != null) {
                System.out.println("Addition: " + (x + y));
            } else {
                System.out.println("Y is null");
            }
        } else {
            System.out.println("X is null");
        }

        Maybe<Integer> addedMe = Maybe.ofValue(x)
                .presentThenDo(y, (integer, integer2) -> Maybe.ofValue(y)
                        .presentThenDo(() -> integer + integer2)
                        .getValue());

        System.out.println("Added1: " + addedMe.getValue());

        Integer added = Maybe.ofValue(x)
                .presentThenDo(y, (integer, integer2) -> Maybe.ofValue(y)
                        .presentThenDo(() -> integer + integer2)
                        .getValue())
                .orElseThrow(new IllegalArgumentException("Added integers cannot be null32"))
                .getValue();
        System.out.println("Added: " + added);
    }

    private final T operand;

    public Maybe(T operand) {
        this.operand = operand;
    }

    public Maybe() {
        this(null);
    }

    public static <V> Maybe<V> ofValue(V value) {
        return new Maybe<>(value);
    }

    public T getValue() {
        return this.operand;
    }

    public <V, W> Maybe<W> presentThenDo(V secondArg, BiFunction<T, V, W> biFunction) {
        return (this.operand != null) ? thenDo(secondArg, biFunction) :
                ofValue(null);
    }

    public <V> Maybe<V> presentThenDo(Function<T, V> function) {
        return presentThenDo(null, (BiFunction<T, T, V>) (t, t2) -> {
            return function.apply(t);   // intentionally ignore arg t2,
            // which should be null here anyways
        });
    }

    public Maybe<T> presentThenDo(Consumer<T> consumer) {
        return presentThenDo(null, (t, t2) -> {
            consumer.accept(t);

            return this.operand;
        });
    }

    public <V> Maybe<V> presentThenDo(Supplier<V> supplier) {
        return presentThenDo(null, (t, t2) -> supplier.get());
    }

    public <V, U> Maybe<U> thenDo(V secondaryArg, BiFunction<T, V, U> biFunction) {
        return ofValue(biFunction.apply(this.operand, secondaryArg));
    }

    public <V, U> Maybe<U> elseDo(V secondaryArg, BiFunction<T, V, U> biFunction) {
        return thenDo(secondaryArg, biFunction);
    }

    public <V> Maybe<V> thenDo(Function<T, V> failFunction) {
        return elseDo(null, (BiFunction<T, T, V>) (t1, t2) ->
                failFunction.apply(t1)   // intentionally ignore (null) t2
        );
    }

    public <V> Maybe<V> elseDo(Function<T, V> failFunction) {
        return thenDo(failFunction);
    }

    public void thenDo(Consumer<T> consumable) {
        thenDo(null, (BiFunction<T, T, T>) (t, t2) -> {
            consumable.accept(t);     // intentionally ignore (null) t2
            return null;
        });
    }

    public void elseDo(Consumer<T> failRunnable) {
        thenDo(failRunnable);
    }

    public void thenDo(Runnable failRunnable) {
        thenDo(null, (BiFunction<T, T, T>) (t, t2) -> {
            failRunnable.run();     // intentionally ignore (null) t and t2
            return null;
        });
    }

    public void elseDo(Runnable failRunnable) {
        thenDo(failRunnable);
    }

    public <V extends Throwable> Maybe<T> orElseThrow(V exception) throws V {
        if (this.operand == null) {
            Objects.requireNonNull(exception);
            throw exception;
        } else {
            return this;
        }
    }

}

