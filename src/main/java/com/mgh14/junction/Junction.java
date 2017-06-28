package com.mgh14.junction;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
        return (condition != null && condition.test(operand)) ? of(operand) : of();
    }

    public Junction<T> ifFails(Predicate<T> condition) {
        return ifSucceeds((condition != null) ? condition.negate() : s -> true);
    }

    public <V> Junction<V> doOperation(Function<T, V> function) {
        return doOperation(true, function);
    }

    public <V> Junction<V> doOperation(boolean checkForNull, Function<T, V> function) {
        if (function == null) {
            return of();
        }

        Junction<T> startJunction = (checkForNull) ? ifSucceeds(Objects::nonNull) : this;
        T startOperand = startJunction.getValue();
        if (startOperand != null) {
            return of(function.apply(startJunction.getValue()));
        } else {
            return of();
        }
    }

    public Junction<T> doSameTypeOperation(boolean checkForNull, Function<T, T> function) {
        if (function == null) {
            return of(operand);
        }

        Junction<T> startJunction = (checkForNull) ? ifSucceeds(Objects::nonNull) : this;
        T startOperand = startJunction.getValue();
        if (startOperand != null) {
            return of(function.apply(startJunction.getValue()));
        } else {
            return of(operand);
        }
    }

    // alias
    public <V> Junction<V> thenDo(Function<T, V> function) {
        return doOperation(function);
    }

    /*// alias
    public <V> Junction<V> orElseDo(Function<T, V> failureFunction) {
        return doOperation(failureFunction);
    }*/

    public <E extends Exception> Junction<T> orElseThrow_returnJunction(E exception) throws E {
        throw exception;
    }

    public <E extends Exception> T orElseThrow_returnValue(E exception) throws E {
        throw exception;
    }

    public Stream<T> stream() {
        return Stream.of(operand);
    }

    @Override
    public boolean equals(Object other) {
        if (null == other || !(other instanceof Junction)) {
            return false;
        }

        Junction otherJunction = (Junction) other;
        if (this == other) {
            return true;
        }

        Object thisValue = this.getValue();
        Object otherValue = otherJunction.getValue();
        if (null == thisValue && null == otherValue) {
            return true;
        } else if (null == thisValue) {
            return false;
        }

        return thisValue.equals(otherValue);
    }

    @Override
    public int hashCode() {
        return (operand != null) ? operand.hashCode() : 112358;
    }

}
