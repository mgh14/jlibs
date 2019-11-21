package com.mgh14.nn;

import com.mgh14.junction.Maybe;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

/**
 * TODO: document
 */
class Layer<T extends Node> implements Serializable {

    private final T[] nodes;

    Layer(T[] nodes) {
        this.nodes = nodes;
    }

    int getNumberOfNodes() {
        return nodes.length;
    }

    T[] getNodes() {
        return nodes;
    }

    public static <V extends Node> byte[] serializeLayer(Layer<V> layer) throws IOException {
        return serializeLayer(layer, Maybe.of(null));
    }

    public static <V extends Node> byte[] serializeLayer(Layer<V> layer, Maybe<Float[]> inputs)
            throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        outputStream.write("<layer>\n".getBytes());

        for (V node : layer.getNodes()) {
            outputStream.write(Node.serializeNode(node, inputs));
        }

        outputStream.write("</layer>\n".getBytes());

        return outputStream.toByteArray();
    }

}
