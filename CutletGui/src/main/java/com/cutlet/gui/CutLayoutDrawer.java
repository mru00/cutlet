/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */
package com.cutlet.gui;

import com.cutlet.lib.model.Dimension;
import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Position;
import com.cutlet.lib.data.cuttree.CutNode;
import com.cutlet.lib.data.cuttree.CutTreeNode;
import com.cutlet.lib.data.cuttree.PanelNode;
import com.cutlet.lib.model.Layout;
import com.cutlet.lib.optimizer.OptimizationResult;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class CutLayoutDrawer {

    public void drawOptimizationResult(@NonNull Pane pane, @NonNull OptimizationResult result) {

        for (Layout layout : result.getLayouts()) {
            Dimension dim = scale(layout.getSheet().getDimension());
            Canvas canvas = new Canvas(dim.getLength(), dim.getWidth());

            StackPane canvasContainer = new StackPane(canvas);
            canvasContainer.getStyleClass().add("layoutcanvas");

            pane.getChildren().add(canvasContainer);
            drawOptimizationResult(canvas, layout);
            //canvas.setScaleX(0.5);
            //canvas.setScaleY(0.5);
        }
    }

    public void drawOptimizationResult(@NonNull Canvas canvas, @NonNull Layout layout) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.save();
        gc.setGlobalAlpha(1);
        gc.setLineWidth(1);

        drawLayout(gc, layout, 0);
        drawText(gc, layout, 0);

        gc.restore();
    }

    public void drawLayout(GraphicsContext gc, Layout layout, int level) {

        final Dimension dimSheet = scale(layout.getSheet().getDimension());

        gc.save();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(0, 0, dimSheet.getLength(), dimSheet.getWidth());
        gc.restore();

        for (CutTreeNode node : layout.getCutTree()) {
            if (node instanceof CutNode) {

                final CutNode cut = (CutNode) node;
                final Position cutpos = scale(cut.getCutPos());
                final Dimension cutdim = scale(cut.getCutDim());
                gc.save();
                gc.setFill(Color.BLACK);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(0);

                gc.fillRect((int) (cutpos.getX()),
                        (int) (cutpos.getY()),
                        (int) (cutdim.getLength()),
                        (int) (cutdim.getWidth()));
                gc.restore();

            } else if (node instanceof PanelNode) {
                final PanelNode panelNode = (PanelNode) node;

                final Position pos = scale(panelNode.getPosition());
                final Dimension dim = scale(panelNode.getDimension());

                final int xp = (int) (pos.getX());
                final int yp = (int) (pos.getY());
                final int wp = (int) (dim.getLength());
                final int hp = (int) (dim.getWidth());

                gc.save();
                gc.setFill(Color.PINK);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.fillRect(xp, yp, wp, hp);
                gc.restore();
            }
        }
    }

    public void drawText(GraphicsContext gc, Layout layout, int level) {

        for (CutTreeNode node : layout.getCutTree()) {
            if (node instanceof CutNode) {
                /*
                final CutNode cut = (CutNode) node;
                Rectangle2D cutpos = cut.cutpos;
                gc.save();
                gc.setFill(Color.BLACK);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);

                gc.fillRect((int) (cutpos.getMinX()),
                        (int) (cutpos.getMinY()),
                        (int) (cutpos.getWidth()),
                        (int) (cutpos.getHeight()));
                gc.restore();
                 */
            } else if (node instanceof PanelNode) {
                final PanelNode panelNode = (PanelNode) node;
                final Panel panel = panelNode.getPanel();

                final Position pos = scale(panelNode.getPosition());
                final int xp = (int) (pos.getX());
                final int yp = (int) (pos.getY());

                gc.save();
                gc.setGlobalAlpha(1);
                gc.setFill(Color.BLACK);
                gc.fillText(String.format("%s (%d x %d)",
                        panel.getTitle(),
                        (int) panel.getDimension().getLength(),
                        (int) panel.getDimension().getWidth()),
                        xp + 10, yp + 20);

                gc.restore();

            }
        }
    }

    private Dimension scale(Dimension dim) {
        final double scale = 0.5;
        return new Dimension(dim.getLength() * scale, dim.getWidth() * scale);
    }

    private Position scale(Position dim) {
        final double scale = 0.5;
        return new Position(dim.getX() * scale, dim.getY() * scale);
    }

}
