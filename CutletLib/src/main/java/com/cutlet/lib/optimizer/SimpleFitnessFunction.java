/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.optimizer;

/**
 *
 * @author rmuehlba
 */
public class SimpleFitnessFunction implements FitnessFunction {

    @Override
    public double fitness(OptimizationResultStats stats) {
        return stats.getSheetArea() * stats.getTotalCutLength() / 1e5;
    }
}
