/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Position;
import lombok.NonNull;
import lombok.ToString;

/**
 *
 * @author rmuehlba
 */
@ToString
public class PanelNode extends AbstractCutTreeNode {

    private final Panel panel;

    public PanelNode(@NonNull final CutTreeNode parent,
            @NonNull final Panel panel,
            @NonNull final Position position,
            @NonNull final Dimension dimension) {

        super(parent, position, dimension);
        this.panel = panel;
    }

    public Panel getPanel() {
        return panel;
    }

    @Override
    public void replaceChild(@NonNull final CutTreeNode from, @NonNull final CutTreeNode to) {
        throw new UnsupportedOperationException("Not supported: Freepanel.replace.");
    }
}
