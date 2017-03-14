/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */

package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Position;

/**
 *
 * @author rmuehlba
 */
public abstract class AbstractCutTreeNode implements CutTreeNode {

    private final CutTreeNode parent;

    private final Position position;
    private final Dimension dimension;

    public AbstractCutTreeNode(CutTreeNode parent, Position position, Dimension dimension) {
        this.dimension = dimension;
        this.position = position;
        this.parent = parent;
    }

    public boolean canHold(Panel p) {
        return getDimension().canHold(p.getDimension());
    }

    public CutTreeNode getParent() {
        return parent;
    }

    public Position getPosition() {
        return position;
    }

    public Dimension getDimension() {
        return dimension;
    }
}
