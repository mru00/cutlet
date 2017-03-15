/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.data.cuttree;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Position;
import com.cutlet.lib.model.StockSheet;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class CutTree implements Iterable<CutTreeNode>, Serializable {

    private final RootNode root;

    public CutTree(@NonNull final StockSheet sheet) {
        root = new RootNode(sheet.getDimension());
        final FreeNode free = new FreeNode(root, new Position(0, 0), sheet.getDimension());
        root.setChild(free);
    }

    public CutTreeNode getRoot() {
        return root;
    }

    public List<FreeNode> getFreeNodes() {
        List<FreeNode> free = new ArrayList<>();
        for (CutTreeNode node : this) {
            if (node instanceof FreeNode) {
                free.add((FreeNode) node);
            }
        }
        return free;
    }

    @Override
    public Iterator<CutTreeNode> iterator() {
        return new CutTreeIterator(this);
    }

    public CutTreeStats getStats() {

        int numberOfCuts = 0;
        double totalCutLength = 0;
        double sheetArea = root.getDimension().getArea();
        double usedArea = 0;
        double boundingBoxMinX = Double.MAX_VALUE;
        double boundingBoxMaxX = Double.MIN_VALUE;
        double boundingBoxMinY = Double.MAX_VALUE;
        double boundingBoxMaxY = Double.MIN_VALUE;

        for (CutTreeNode node : this) {
            if (node instanceof FreeNode) {
            } else if (node instanceof PanelNode) {
                final PanelNode pn = (PanelNode) node;
                usedArea += pn.getDimension().getArea();

                // this is only partly correct...
                // we should also take cuts into consideration!
                Dimension dim = pn.getDimension();
                Position pos = pn.getPosition();
                boundingBoxMinX = Math.min(pos.getX(), boundingBoxMinX);
                boundingBoxMaxX = Math.max(pos.getX() + dim.getLength(), boundingBoxMaxX);
                boundingBoxMinY = Math.min(pos.getY(), boundingBoxMinY);
                boundingBoxMaxY = Math.max(pos.getY() + dim.getWidth(), boundingBoxMaxY);

            } else if (node instanceof CutNode) {
                final CutNode cn = (CutNode) node;
                numberOfCuts++;
                totalCutLength += cn.getCutLength();
            }
        }
        double boundingBoxArea = (boundingBoxMaxX - boundingBoxMinX) * (boundingBoxMaxY - boundingBoxMinY);

        CutTreeStats stats = new CutTreeStats(numberOfCuts,
                totalCutLength,
                sheetArea,
                usedArea,
                boundingBoxArea);

        return stats;
    }

    class CutTreeIterator implements Iterator<CutTreeNode> {

        private final Queue<CutTreeNode> queue = new ArrayDeque<>();

        CutTreeIterator(@NonNull final CutTree tree) {
            queue.add(root.getChild());
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public CutTreeNode next() {

            if (queue.isEmpty()) {
                throw new RuntimeException("can't get element from empty iterator");
            }
            final CutTreeNode node = queue.remove();
            if (node instanceof CutNode) {
                final CutNode cut = (CutNode) node;
                queue.add(cut.getTarget());
                queue.add(cut.getRest());
            }
            return node;
        }
    }
}
