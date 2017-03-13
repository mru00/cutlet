/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
