package com.mgh14.nn;

import com.mgh14.maybe.Maybe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.function.Function;

/**
 * TODO: document
 */
public class Node {

    private Float[] weights;
    private final float bias;
    private final Function<Float, Float> activationFunc;

    public Node(Float[] weights, float bias, Function<Float, Float> activationFunction) {
        this.weights = weights;
        this.bias = bias;
        this.activationFunc = activationFunction;
    }

    public float getBias() {
        return bias;
    }

    public Float[] getWeights() {
        return weights;
    }

    public Float getOutput(Float... inputVars) {
        //if (inputVars.length != weights.length) {
        //    throw new IllegalArgumentException("");
        //}

        double activationInput = bias;
        for (int i = 0; i < weights.length; i++) {
            activationInput += (weights[i] * inputVars[i]);
        }

        return activationFunc.apply((float) activationInput);
    }

    /*public static Node initializeRandomNode(int numWeights) {
        return initializeRandomNode(numWeights, SIGMOID_FUNCTION);
    }

    public static Node initializeRandomNode(int numWeights, Function<Float, Float> activationFunc) {
        Float[] weights = new Float[numWeights];
        for (int i = 0; i < numWeights; i++) {
            weights[i] = weightGenerator.get();
        }

        return new Node(weights, weightGenerator.get(), activationFunc);
    }*/

    public static byte[] serializeNode(Node node) throws IOException {
        return serializeNode(node, Maybe.of(null));
    }

    public static byte[] serializeNode(Node node, Maybe<Float[]> possibleInputs) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write("<node>\n".getBytes());

        outputStream.write(("\t<weights value=\"" + Arrays.toString(node.getWeights()) + "" + "\" />\n").getBytes());
        outputStream.write(("\t<bias value=\"" + node.getBias() + "\" />\n").getBytes());
        outputStream.write(("\t<activation-function>" + node.activationFunc +
                "</activation-function>\n").getBytes());

        possibleInputs.ifPresent().thenDo((Float[] inputs) -> {
            try {
                outputStream.write("<calculation>\n".getBytes());
                    outputStream.write("<formula>".getBytes());
                    outputStream.write(("(w_1)(input_1) + (w_2)(input_2) + ... + (w_n)(input_n) + bias").getBytes());
                    outputStream.write("<formula>\n".getBytes());

                    outputStream.write("<numbers>".getBytes());
                        Float[] weights = node.getWeights();
                        for (int i = 0; i < inputs.length; i++) {
                            outputStream.write(("(" + weights[i] + ")(" + inputs[i] + ") + ").getBytes());
                        }
                        outputStream.write(String.valueOf(node.getBias()).getBytes());

                        Float weightAndInputProduct = calculateProduct(weights, inputs);
                        Float total = weightAndInputProduct + node.getBias();
                        outputStream.write((" = " + weightAndInputProduct + " + " + node.getBias()).getBytes());
                        outputStream.write((" = " + total + "\n").getBytes());
                        outputStream.write(("<activation>\n").getBytes());
                            outputStream.write(("activation(" + total + ") = " +
                                    node.activationFunc.apply(total) + "\n").getBytes());
                        outputStream.write(("</activation>\n").getBytes());

                    outputStream.write("</numbers>\n".getBytes());

                outputStream.write("</calculation>\n".getBytes());
            } catch (IOException e) {

            }
            return null;
        });

        outputStream.write("</node>\n".getBytes());

        byte[] array = outputStream.toByteArray();
        outputStream.close();
        return array;
    }

    public static Float calculateProduct(Float[] weights, Float[] inputs) {
        Float sum = 0f;
        for (int i = 0; i < weights.length; i++) {
            Float weight = weights[i];
            if (weight == null) {
                weight = 0f;
            }
            sum += (weight * inputs[i]);
        }

        return sum;
    }

    public static class OutputNode extends Node {

        public OutputNode(Function<Float, Float> activationFunction) {
            super(new Float[0], 0, activationFunction);
        }
    }

    public static class InputNode extends Node {

        public InputNode(int numInputs) {
            super(new Float[numInputs], 0, Function.identity());
            Arrays.fill(super.weights, 1, super.weights.length - 1, 0f);
            super.weights[0] = 1f;

        }

        public static Node buildInputNode(int numInputs) {
            return new InputNode(numInputs);
        }
    }

}
