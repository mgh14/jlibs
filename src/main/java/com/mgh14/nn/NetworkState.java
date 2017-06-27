package com.mgh14.nn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;

/**
 * TODO: document
 */
public class NetworkState<T extends Node> implements Serializable {


    private final Layer<T> inputLayer;
    private final Layer<T>[] hiddenLayers;
    private final Layer<T> outputLayer;

    private final Layer<T>[] allLayers;

    public NetworkState(Layer<T>[] layers) {
        this(layers[0],
                Arrays.copyOfRange(layers, 1, layers.length - 1),
                layers[layers.length - 1]);
    }

    public NetworkState(Layer<T> inputLayer, Layer<T>[] hiddenLayers,
                        Layer<T> outputLayer) {
        allLayers = new Layer[2 + hiddenLayers.length];

        allLayers[0] = inputLayer;
        this.inputLayer = inputLayer;

        this.hiddenLayers = hiddenLayers;
        for (int i = 0; i < hiddenLayers.length; i++) {
            allLayers[i + 1] = hiddenLayers[i];
        }

        allLayers[allLayers.length - 1] = outputLayer;
        this.outputLayer = outputLayer;
    }

    public Layer<T> getLayer(int layerIndex) {
        if (0 <= layerIndex && layerIndex <= allLayers.length) {
            return allLayers[layerIndex];
        }
        else {
            //return Maybe.empty();
            return null;
        }
    }

    public static <V extends Node> byte[] serializeNetworkState(NetworkState<V> stateToSerialize)
            throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        /*return Maybe.ofValue(stateToSerialize)
            .ifPresent(networkState -> {
                try {
                    outputStream.write("<network-state>\n".getBytes());

                    for (Layer<V> layer : networkState.allLayers) {
                        outputStream.write(Layer.serializeLayer(layer));
                    }

                    outputStream.write("</network-state>\n".getBytes());

                    return outputStream.toByteArray();
                } catch (IOException e) {
                    return null;
                }
            }).orElseThrow_maybePlaceholder(
                    new IOException("Unable to write network state"));*/
        return null;
    }

    public static <V extends Node> byte[] serializeNetworkStateWithInput(NetworkState<V> stateToSerialize,
                                                                    Float[] inputs)
            throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        /*return Maybe.ofValue(stateToSerialize)
            .ifPresent(() -> {
                try {
                    outputStream.write("<network-state>\n".getBytes());

                    //stateToSerialize.allLayers[stateToSerialize.allLayers.length - 1] = null;
                    for (Layer<V> layer : stateToSerialize.allLayers) {
                        //if(layer != null)
                        outputStream.write(Layer.serializeLayer(layer, Maybe.ofValue(inputs)));
                    }

                    outputStream.write("</network-state>\n".getBytes());

                    return outputStream.toByteArray();
                } catch (IOException e) {
                    return null;
                }
            })
            .orElseThrow_maybePlaceholder(new IOException("Unable to write network and input state"));*/
        return null;
    }

}
