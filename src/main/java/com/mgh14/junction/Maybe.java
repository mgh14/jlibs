package com.mgh14.junction;

import java.util.Objects;

/**
 * TODO: Document
 */
public class Maybe<T> extends Junction<T> {

    private Maybe(T operand) {
        super(operand);
    }

    public static void main(String[] args) {
        /*Integer x = 1;
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
                .ifPresent(y, (integer, integer2) -> Maybe.ofValue(y)
                        .ifPresent(() -> integer + integer2)
                        .getValue());

        System.out.println("Added1: " + addedMe.getValue());

        Integer added = Maybe.ofValue(x)
                .ifPresent(y, (integer, integer2) -> Maybe.ofValue(y)
                        .ifPresent(() -> integer + integer2)
                        .getValue())
                .orElseThrow_returnJunction(new IllegalArgumentException("Added integers cannot be null32"))
                .getValue();
        System.out.println("Added: " + added);*/
    }

    /*@Override
    public <V> Maybe<V> doOperation(Function<T, V> function) {
        V value = Maybe.of(super.getValue())
                .ifPresent()
                .doOperation(function)
                .orElseDo((Function<V, V>) v -> null).getValue();
        return of(value);
    }

    @Override
    public <V> Maybe<V> orElseDo(Function<T, V> failFunction) {
        return Maybe.of((T) null)
                .doOperation(failFunction);
    }*/

    public Maybe<T> ifPresent() {
        Junction<T> result = super.ifSucceeds(Objects::nonNull);
        return of(result.getValue());
    }

    public static <V> Maybe<V> of(V value) {
        return new Maybe<>(value);
    }
}

