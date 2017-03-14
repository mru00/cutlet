/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Position;
import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class PanelNode extends AbstractCutTreeNode {

    private final Panel panel;

    public PanelNode(CutTreeNode parent, Panel panel, Position position, Dimension dimension) {
        super(parent, position, dimension);
        this.panel = panel;
    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void replaceChild(CutTreeNode from, CutTreeNode to) {
        throw new UnsupportedOperationException("Not supported: Freepanel.replace.");
    }
}
