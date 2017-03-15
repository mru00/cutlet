/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */

package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Layout;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.data.cuttree.CutTreeStats;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class OptimizationResult implements Serializable {

    private final List<Layout> layouts = new ArrayList<>();
    private double runtime;

    public void createNewLayout(@NonNull Panel p) {
        addLayout(new Layout(p.getSheet(), layouts.size()));
    }

    private void addLayout(Layout l) {
        layouts.add(l);
    }

    public OptimizationResultStats getStats() {
        int numberOfCuts = 0;
        int numberOfLayouts = layouts.size();
        double totalCutLength = 0;
        double sheetArea = 0;
        double usedArea = 0;
        for (Layout layout : layouts) {
            CutTreeStats substats = layout.getCutTree().getStats();
            numberOfCuts += substats.getNumberOfCuts();
            totalCutLength += substats.getTotalCutLength();
            sheetArea += substats.getSheetArea();
            usedArea += substats.getUsedArea();
        }
        OptimizationResultStats stats = new OptimizationResultStats(numberOfLayouts,
                numberOfCuts,
                totalCutLength,
                sheetArea,
                usedArea);
        return stats;
    }

    public List<Layout> getLayouts() {
        return layouts;
    }

    public double getRuntime() {
        return runtime;
    }

    public void setRuntime(double runtime) {
        this.runtime = runtime;
    }


}
