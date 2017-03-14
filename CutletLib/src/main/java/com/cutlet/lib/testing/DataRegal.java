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
public class DataRegal extends AbstractTestkData {

    @Override
    public Project getData() {

        final StockSheet sheet = makeSheet(2500, 1250);
        int i;
        
        final double w = 600;
        
        List<Panel> inputs = new ArrayList<>();
        for (i = 0; i < 8; i++) {
            inputs.add(new Panel(sheet, new Dimension(500, w), "side short"));
        }
        for (i = 0; i < 4; i++) {
            inputs.add(new Panel(sheet, new Dimension(560, w), "side long"));
        }
        for (i = 0; i < 12; i++) {
            inputs.add(new Panel(sheet, new Dimension(450, w), "top_bottom"));
        }
        for (i = 0; i < 16; i++) {
            inputs.add(new Panel(sheet, new Dimension(25, w), "halter"));
        }

        return makeProject(inputs, 4);
    }

    @Override
    public String getTitle() {
        return "DataRegal";
    }

}
