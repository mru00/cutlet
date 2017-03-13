/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
public class DataNoPanels extends AbstractTestkData {

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
