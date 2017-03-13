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
import com.cutlet.lib.material.Wood;
import java.util.List;

/**
 *
 * @author rmuehlba
 */
public abstract class AbstractTestkData implements TestData {

    public Project makeProject(List<Panel> inputs, final int bladeWidth) {
        Project project = new Project();
        project.setPanels(inputs);
        project.setBladeWidth(bladeWidth);
        return project;
    }
   
    protected StockSheet makeSheet(double length, double width) {
        return new StockSheet("wood", new Wood(), new Dimension(length, width), 18);
    }
}
