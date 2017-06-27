package com.mgh14.junction;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * TODO: Document
 */
public class Junction<T, V> {

    private T operand;
    private V returnValue;

    public Junction(T operand) {
        setOperand(operand);
        setReturnValue(null);
    }

    public Junction() {
        this(null);
    }

    public void setOperand(T operand) {
        this.operand = operand;
    }

    private void setReturnValue(V returnValue) {
        this.returnValue = returnValue;




    }

    public Junction<V, Object> ifSucceeds(Predicate<Object> condition,
                                          Function<T, V> successFunction) {
        setReturnValue(successFunction.apply(this.operand));
        return new Junction<>(returnValue);
    }

    public Junction<V, Object> elseIfFails(Function<T, V> failFunction) {
        return new Junction<>(failFunction.apply(this.operand));
    }

}
