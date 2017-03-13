/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Position;

/**
 *
 * @author rmuehlba
 */
public class FreeNode extends AbstractCutTreeNode {

    public FreeNode(CutTreeNode parent, Position position, Dimension dimension) {
        super(parent, position, dimension);
    }

    @Override
    public void replaceChild(CutTreeNode from, CutTreeNode to) {
        throw new UnsupportedOperationException("Not supported: Freepanel.replace.");
    }

    public CutNode cut(double bladeWidth, double cutPosition, CutNode.Direction direction) {
        final CutTreeNode parent = this.getParent();
        final CutNode cut = new CutNode(parent,
                bladeWidth, cutPosition, direction,
                getPosition(),
                getDimension());

        parent.replaceChild(this, cut);

        return cut;
    }

    public PanelNode setPanel(Panel panel) {
        final CutTreeNode parent = this.getParent();
        final PanelNode pn = new PanelNode(parent, panel, getPosition(), getDimension());
        parent.replaceChild(this, pn);
        return pn;
    }
}
