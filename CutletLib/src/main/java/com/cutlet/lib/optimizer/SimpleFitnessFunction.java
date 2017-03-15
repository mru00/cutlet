/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.optimizer;

import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class SimpleFitnessFunction implements FitnessFunction {

    @Override
    public double fitness(@NonNull OptimizationResultStats stats) {
        return stats.getSheetArea() * stats.getTotalCutLength() / 1e5;
    }
}
