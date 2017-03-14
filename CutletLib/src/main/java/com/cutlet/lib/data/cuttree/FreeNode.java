/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Position;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class FreeNode extends AbstractCutTreeNode {

    public FreeNode(@NonNull CutTreeNode parent, @NonNull Position position, @NonNull Dimension dimension) {
        super(parent, position, dimension);
    }

    @Override
    public void replaceChild(@NonNull CutTreeNode from, @NonNull CutTreeNode to) {
        throw new UnsupportedOperationException("Not supported: Freepanel.replace.");
    }

    public CutNode cut(double bladeWidth, double cutPosition, @NonNull CutNode.Direction direction) {
        final CutTreeNode parent = this.getParent();
        final CutNode cut = new CutNode(parent,
                bladeWidth, cutPosition, direction,
                getPosition(),
                getDimension());

        parent.replaceChild(this, cut);

        return cut;
    }

    public PanelNode setPanel(@NonNull Panel panel) {
        final CutTreeNode parent = this.getParent();
        final PanelNode pn = new PanelNode(parent, panel, getPosition(), getDimension());
        parent.replaceChild(this, pn);
        return pn;
    }
}
