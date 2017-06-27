package com.mgh14.nn;

import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * TODO: document
 */
public class Functions<T extends Node> {

    // ERROR FUNCTIONS
    public static final BiFunction<Float, Float, Float> MEAN_SQUARED_ERROR =
            new BiFunction<Float, Float, Float>() {
                @Override
                public Float apply(Float expected, Float output) {
                    return ((expected - output) * (expected - output)) / 2f;
                }

                @Override
                public String toString() {
                    return "Mean Squared Error Function";
                }
            };

    public static final Function<Float, Float> SIGMOID_FUNCTION =
            new Function<Float, Float>() {
                @Override
                public Float apply(Float input) {
                    return 1f / ((float) (1 + Math.exp(-1 * input)));
                }

                @Override
                public String toString() {
                    return "Sigmoid Function";
                }
            };

    // RANDOM FUNCTIONS
    public static final <T extends Node> Float RANDOM_FLOAT_GENERATOR(T inputNode) {
        return new Random().nextFloat();
    }

}
