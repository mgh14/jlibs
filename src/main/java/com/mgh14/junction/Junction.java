package com.mgh14.junction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TODO: Document
 */
public class Junction<T> {

    private T operand;

    protected Junction(T operand) {
        this.operand = operand;
    }

    public static <S> Junction<S> of() {
        return of(null);
    }

    // alias
    public static <S> Junction<S> empty() {
        return of();
    }

    // alias
    /*public static <S> Junction<S> empty(Class<S> classOfValue) {
        return of((classOfValue) null);
    }*/

    public static <S> Junction<S> of(S value) {
        return new Junction<>(value);
    }

    public T getValue() {
        return operand;
    }

    public Junction<T> ifSucceeds(Predicate<T> condition) {
        return condition.test(operand) ? of(operand) : of();
    }

    public Junction<T> ifFails(Predicate<T> condition) {
        return ifSucceeds(condition.negate());
    }

    public <V> Junction<V> doOperation(Function<T, V> function) {
        return doOperation(true, function);
    }

    public <V> Junction<V> doOperation(boolean checkForNull, Function<T, V> function) {
        Junction<T> startJunction = (checkForNull) ? ifSucceeds(Objects::nonNull) : this;
        return of(function.apply(startJunction.getValue()));
    }

    public <V> Junction<V> thenDo(Function<T, V> function) {
        return doOperation(function);
    }

    // alias
    public <V> Junction<V> orElseDo(Function<T, V> failFunction) {
        return doOperation(failFunction);
    }

    public <E extends Exception> Junction<T> orElseThrow_returnJunction(E exception) throws E {
        throw exception;
    }

    public <E extends Exception> T orElseThrow_returnValue(E exception) throws E {
        return (orElseThrow_returnJunction(exception)).getValue();
    }

}
