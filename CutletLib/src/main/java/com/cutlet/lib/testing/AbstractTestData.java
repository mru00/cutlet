/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.testing;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.model.StockSheet;
import com.cutlet.lib.material.Wood;
import java.util.List;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public abstract class AbstractTestData implements TestData {

    public Project makeProject(@NonNull List<Panel> inputs, final int bladeWidth) {
        Project project = new Project();
        project.setPanels(inputs);
        project.setBladeWidth(bladeWidth);
        return project;
    }
   
    protected StockSheet makeSheet(double length, double width) {
        return new StockSheet("wood", new Wood(), new Dimension(length, width), 18);
    }
}
