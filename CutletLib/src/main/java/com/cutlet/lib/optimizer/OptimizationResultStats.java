/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.optimizer;

import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class OptimizationResultStats {

    private final int numberOfLayouts;
    private final int numberOfCuts;
    private final double totalCutLength;
    private final double sheetArea;
    private final double usedArea;
    private final double wastagePercent;
    private final double boundingBoxArea;

    public OptimizationResultStats(int numberOfLayouts, 
            int numberOfCuts, 
            double totalCutLength, 
            double sheetArea, 
            double usedArea,
            double boundingBoxArea) {
        this.numberOfLayouts = numberOfLayouts;
        this.numberOfCuts = numberOfCuts;
        this.totalCutLength = totalCutLength;
        this.sheetArea = sheetArea;
        this.usedArea = usedArea;
        this.wastagePercent = 100 * (1 - (usedArea / sheetArea));
        this.boundingBoxArea = boundingBoxArea;
    }

    public int getNumberOfCuts() {
        return numberOfCuts;
    }

    public double getTotalCutLength() {
        return totalCutLength;
    }

    public double getSheetArea() {
        return sheetArea;
    }

    public double getUsedArea() {
        return usedArea;
    }

    public double getWastagePercent() {
        return wastagePercent;
    }

    public int getNumberOfLayouts() {
        return numberOfLayouts;
    }

    public double getBoundingBoxArea() {
        return boundingBoxArea;
    }
}
