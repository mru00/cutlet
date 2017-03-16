/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.testing;

import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.model.StockSheet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rmuehlba
 */
public class DataNoPanels extends AbstractTestData {

    @Override
    public Project getData() {
        StockSheet sheet = makeSheet(800, 600);

        List<Panel> inputs = new ArrayList<>();

        return makeProject(inputs, 1);
    }

    @Override
    public String getTitle() {
        return "Data1";
    }

}
