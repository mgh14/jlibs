package com.mgh14.stream;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;


/**
 * TODO: Document
 */
public class StreamPipeline {

    private List<Function<InputStream, InputStream>> operatorFunctions;

    public StreamPipeline(List<Function<InputStream, InputStream>> operatorFunctions) {
        this.operatorFunctions = operatorFunctions;
    }

    public StreamPipeline() {
        this(new ArrayList<>());
    }

    public void registerOperator(Function<InputStream, InputStream> operator) {
        operatorFunctions.add(operator);
    }

    public void removeOperator(Function<InputStream, InputStream> operator) {
        if (operatorFunctions.contains(operator)) {
            operatorFunctions.remove(operator);
        }
    }

    public InputStream executeOperators(InputStream beginningStream) {
        InputStream currentStream = beginningStream;
        for (Function<InputStream, InputStream> operatorFuncs : operatorFunctions) {
            currentStream = operatorFuncs.apply(currentStream);
        }

        return currentStream;
    }

}
