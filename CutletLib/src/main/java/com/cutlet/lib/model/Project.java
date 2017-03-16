/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.model;

import com.cutlet.lib.optimizer.OptimizationResult;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// we use guava's Optional here, because it is serializable, jdks is not
// http://stackoverflow.com/a/39637752/1689451
import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.checkArgument;

import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class Project implements Serializable {

    private List<Panel> panels = new ArrayList<>();
    private double bladeWidth = 3;
    private Optional<OptimizationResult> optimizationResult;

    public List<Panel> getPanels() {
        return panels;
    }

    public void setPanels(@NonNull final List<Panel> inputs) {
        this.panels = inputs;
    }

    public double getBladeWidth() {
        return bladeWidth;
    }

    public void setBladeWidth(final double bladeWidth) {
        checkArgument(bladeWidth >= 0, "bladeWidth can't be negative");
        this.bladeWidth = bladeWidth;
    }

    public Optional<OptimizationResult> getOptimizationResult() {
        return optimizationResult;
    }

    public void setOptimizationResult(@NonNull final Optional<OptimizationResult> optimizationResult) {
        this.optimizationResult = optimizationResult;
    }

    public void setOptimizationResult(java.util.Optional<OptimizationResult> newValue) {
        if (newValue.isPresent()) {
            this.optimizationResult = Optional.of(newValue.get());

        } else {
            this.optimizationResult = Optional.absent();

        }
    }

    public List<PanelInstance> getPanelInstances() {
        List<PanelInstance> pis = new ArrayList<>();
        for (Panel p : panels) {
            for (int i = 0; i < p.getCount(); i++) {
                pis.add(new PanelInstance(p.getSheet(), p.getDimension(), p.getTitle(), i, p.canRotate()));
            }
        }
        return pis;
    }
}
