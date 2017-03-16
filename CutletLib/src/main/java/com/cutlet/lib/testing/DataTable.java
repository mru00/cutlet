/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.testing;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.PanelInstance;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.model.StockSheet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rmuehlba
 */
public class DataTable extends AbstractTestData {

    @Override
    public Project getData() {

        final StockSheet sheet = makeSheet(2500, 600);
        int i;

        /*      
        Input Group	gestell			1															1	1
Input PanelInstance	bein	900 mm	60 mm	4	18.0mm	SameAsSheet	buche leimholz					False							2	1													
Input PanelInstance	einlegebrett	732 mm	564 mm	2	18.0mm	SameAsSheet	buche leimholz					False							3	1													
Input PanelInstance	flansch	750 mm	30 mm	0	18.0mm	Yes	buche leimholz					False							4	1													
Input PanelInstance	seitenstrebe	630 mm	60 mm	4	18.0mm	SameAsSheet	buche leimholz					False							5	1													
Input PanelInstance	stuetze hinten	564 mm	60 mm	2	18.0mm	SameAsSheet	buche leimholz					False							6	1													
Input PanelInstance	bein	900 mm	60 mm	4	18.0mm	SameAsSheet	buche leimholz					False							7	7													
Input PanelInstance	einlegebrett	732 mm	564 mm	2	18.0mm	SameAsSheet	buche leimholz					False							8	8													
Input PanelInstance	flansch	750 mm	30 mm	0	18.0mm	Yes	buche leimholz					False							9	9													
Input PanelInstance	seitenstrebe	630 mm	60 mm	4	18.0mm	SameAsSheet	buche leimholz					False							10	10													
Input PanelInstance	stuetze hinten	564 mm	60 mm	2	18.0mm	SameAsSheet	buche leimholz					False							11	11													
Input Group	top			1															12	12
Input PanelInstance	sims seite	768 mm	136 mm	2	18.0mm	SameAsSheet	buche leimholz					False							13	12													
Input PanelInstance	sims front	600 mm	76 mm	1	18.0mm	SameAsSheet	buche leimholz					False							14	12													
Input PanelInstance	rueckteil	636 mm	136 mm	1	18.0mm	SameAsSheet	buche leimholz					False							15	12													
Input PanelInstance	liegeflaeche	750 mm	600 mm	1	18.0mm	SameAsSheet	buche leimholz					False							16	12													
Input PanelInstance	sims seite	768 mm	136 mm	2	18.0mm	SameAsSheet	buche leimholz					False							17	17													
Input PanelInstance	sims front	600 mm	76 mm	1	18.0mm	SameAsSheet	buche leimholz					False							18	18													
Input PanelInstance	rueckteil	636 mm	136 mm	1	18.0mm	SameAsSheet	buche leimholz					False							19	19													
Input PanelInstance	liegeflaeche	750 mm	600 mm	1	18.0mm	SameAsSheet	buche leimholz					False							20	20													

         */
        final boolean canRotate = true;
        List<Panel> inputs = new ArrayList<>();
        inputs.add(new Panel(sheet, new Dimension(900, 60), "bein", canRotate, 4));
        inputs.add(new Panel(sheet, new Dimension(732, 564), "einlegebrett", canRotate, 2));
        inputs.add(new Panel(sheet, new Dimension(630, 60), "seitenstrebe", canRotate, 4));
        inputs.add(new Panel(sheet, new Dimension(564, 60), "stuetze hinten", canRotate, 2));
        inputs.add(new Panel(sheet, new Dimension(768, 136), "sims seite", canRotate, 2));
        inputs.add(new Panel(sheet, new Dimension(600, 76), "sims front", canRotate, 1));
        inputs.add(new Panel(sheet, new Dimension(636, 136), "sims back", canRotate, 1));
        inputs.add(new Panel(sheet, new Dimension(750, 600), "lf", canRotate, 1));

        return makeProject(inputs, 4);
    }

    @Override
    public String getTitle() {
        return "Tisch";
    }

}
