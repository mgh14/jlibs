package com.mgh14.nn;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.mgh14.nn.Functions.MEAN_SQUARED_ERROR;

/**
 * TODO: document
 */
public class Network<T extends Node> {

    //private final Layer<T> inputLayer;
    private final Layer<T>[] hiddenLayers;
    //private final Layer<T> outputLayer;

    private final Layer<T> mostNodesLayer;

    private final Layer<T>[] allLayers;

    private final BiFunction<Float, Float, Float> errorFunction;

    /*public NetworkState train(float[] singleInput) {
        // draw initial state

        float[][] outputs = this.feedSingleTrainingInstance(singleInput);
        // draw outputs

        // calculate error
        float error = this.calculateError(boxFloatArray(singleInput),
                boxFloatArray(outputs[outputs.length - 1]));
        // draw error

        this.updateWeights();
        // now draw updated weights in new network state

        return getNetworkState();
    }*/

    public static void main(String[] args) throws IOException {
        int[] nodesPerHiddenLayers = new int[] {3};
        Network<Node> nNetwork = new Network<>(new Node[2], nodesPerHiddenLayers,
                2, MEAN_SQUARED_ERROR);

        NetworkState<Node> initialState = nNetwork.getNetworkState();
        //byte[] initialStateString = NetworkState.serializeNetworkState(initialState);
        //System.out.println("Initial State:\n" + new String(initialStateString));
        // draw initial state

        Float[] singleInput = {.05f, .1f};
        Float[] expectedOutputs = {.01f, .99f};
        byte[] initialTrainStateString = NetworkState.serializeNetworkStateWithInput(initialState, singleInput);
        System.out.println("With first training instance:\n" + new String(initialTrainStateString));

        if (false) {
            Float[] actualOutputs = nNetwork.feedSingleTrainingInstance(singleInput);
            // draw outputs

            // calculate error
            System.out.println("Outputs: " + Arrays.toString(actualOutputs));
            Float totalError = nNetwork.calculateTotalError(singleInput, expectedOutputs, actualOutputs);
            System.out.println("Total error: " + totalError);
            // draw error

            nNetwork.updateWeights();
            // now draw updated weights in new network state
        }
    }

    public Network(Node[] emptyInputs, int[] nodesPerHiddenLayer,
                   int numOutputs, BiFunction<Float, Float, Float> errorFunction) {
        this.errorFunction = errorFunction;

        int numHiddenLayers = nodesPerHiddenLayer.length;
        allLayers = new Layer[2 + numHiddenLayers];

        for (int i = 0; i < emptyInputs.length; i++) {
            emptyInputs[i] = Node.InputNode.buildInputNode(emptyInputs.length);
        }
        //inputLayer = makeLayer(numInputs, nodesPerHiddenLayer,
        //        () -> randomNodeInitializer.apply(0), null, null, Function.identity());
        //inputLayer = new Layer(emptyInputs);
        allLayers[0] = new Layer(emptyInputs);

        hiddenLayers = new Layer[numHiddenLayers];
        Layer<T> mostNodesLayer = allLayers[0];
        for (int i = 0; i < numHiddenLayers; i++) {
            int currentNumLayerNodes = nodesPerHiddenLayer[i];
            hiddenLayers[i] = makeLayer(currentNumLayerNodes, allLayers[i].getNumberOfNodes(),
                    Functions::RANDOM_FLOAT_GENERATOR, Functions::RANDOM_FLOAT_GENERATOR,
                    Functions.SIGMOID_FUNCTION);
            if (currentNumLayerNodes > mostNodesLayer.getNumberOfNodes()) {
                mostNodesLayer = hiddenLayers[i];
            }

            allLayers[i + 1] = hiddenLayers[i];
        }

        allLayers[1 + hiddenLayers.length] = makeLayer(numOutputs,
                hiddenLayers[hiddenLayers.length - 1].getNumberOfNodes(),
                o -> 1f, o -> 0f, Functions.SIGMOID_FUNCTION);
        if (allLayers[allLayers.length - 1].getNumberOfNodes() > mostNodesLayer.getNumberOfNodes()) {
            mostNodesLayer = allLayers[allLayers.length - 1];
        }

        this.mostNodesLayer = mostNodesLayer;
    }

    public NetworkState<T> getNetworkState() {
        return new NetworkState<>(allLayers);
    }

    public Float[] feedSingleTrainingInstance(Float[] inputData) {
        Float[][] outputs = new Float[1 + hiddenLayers.length + 1][mostNodesLayer.getNumberOfNodes()];

        for (int i = 0; i < allLayers.length; i++) {
            Node[] nodes = allLayers[i].getNodes();
            for (int j = 0; j < nodes.length; j++) {
                if (nodes[j] == null) {
                    break;
                } else {
                    outputs[i][j] = nodes[j].getOutput((i == 0 ?
                            new Float[] { inputData[j] } : outputs[i - 1]));
                }
            }
        }

        return outputs[allLayers.length - 1];
    }

    public Float calculateTotalError(Float[] input, Float[] expected, Float[] output) {
        double totalError = 0;
        for (int i = 0; i < input.length; i++) {
            totalError += errorFunction.apply(expected[i], output[i]);
        }

        return (float) totalError;
    }

    private void updateWeights() {

    }

    private static <T extends Node> Layer<T> makeLayer(int numNodes, int previousLayerNumNodes,
                               Function<T, Float> weightInitFunction,
                               Function<T, Float> biasInitFunction,
                               Function<Float, Float> activationFunction) {

        Node[] layerNodes = new Node[numNodes];
        for (int i = 0; i < numNodes; i++) {
            Float[] weights = new Float[previousLayerNumNodes];
            for (int j = 0; j < weights.length; j++) {
                weights[j] = weightInitFunction.apply(null);
            }
            Float bias = biasInitFunction.apply(null);

            layerNodes[i] = new Node(weights, bias, activationFunction);
        }

        return new Layer<>((T[]) layerNodes);
    }

}
