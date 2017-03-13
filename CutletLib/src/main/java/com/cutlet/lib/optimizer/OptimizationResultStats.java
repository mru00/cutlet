/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    public OptimizationResultStats(int numberOfLayouts, int numberOfCuts, double totalCutLength, double sheetArea, double usedArea) {
        this.numberOfLayouts = numberOfLayouts;
        this.numberOfCuts = numberOfCuts;
        this.totalCutLength = totalCutLength;
        this.sheetArea = sheetArea;
        this.usedArea = usedArea;
        this.wastagePercent = 100 * (1 - (usedArea / sheetArea));
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

}
