/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.testing;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.model.StockSheet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rmuehlba
 */
public class Data2 extends AbstractTestkData {

    @Override
    public Project getData() {

        
        StockSheet sheet = makeSheet(800, 600);
        
        List<Panel> inputs = new ArrayList<>();
        inputs.add(new Panel(sheet, new Dimension(100, 100), "p1"));
        inputs.add(new Panel(sheet, new Dimension(200, 250), "p2"));
        inputs.add(new Panel(sheet, new Dimension(200, 250), "p3"));
        inputs.add(new Panel(sheet, new Dimension(300, 250), "p4"));
        inputs.add(new Panel(sheet, new Dimension(50, 250), "p4"));
        inputs.add(new Panel(sheet, new Dimension(15, 13), "p5"));
        inputs.add(new Panel(sheet, new Dimension(15, 13), "p6"));

        return makeProject(inputs, 1);
    }

    @Override
    public String getTitle() {
        return "Data2";
    }

}
