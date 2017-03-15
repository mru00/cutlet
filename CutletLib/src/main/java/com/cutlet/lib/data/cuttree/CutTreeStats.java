/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.data.cuttree;

import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class CutTreeStats {

    private final int numberOfCuts;
    private final double totalCutLength;
    private final double sheetArea;
    private final double usedArea;
    private final double wastagePercent;

    public CutTreeStats(
            final int numberOfCuts,
            final double totalCutLength,
            final double sheetArea,
            final double usedArea) {
        
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

}
