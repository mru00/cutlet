/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
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
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class CutLayoutDrawer {

    public void drawOptimizationResult(@NonNull final Pane pane, @NonNull final OptimizationResult result) {

        for (Layout layout : result.getLayouts()) {
            Dimension dim = scale(layout.getSheet().getDimension());
            
            final int spaceForBorder = 2;
            Canvas canvas = new Canvas(dim.getLength() + spaceForBorder, 
                    dim.getWidth() + spaceForBorder);

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

        gc.save();
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(1);
        gc.setLineCap(StrokeLineCap.BUTT);
        gc.setLineJoin(StrokeLineJoin.ROUND);
        gc.strokeRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.restore();

        gc.translate(1, 1);

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
                gc.save();
                gc.setFill(Color.BLACK);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(scale(cut.getKerf()));
                gc.setLineDashes(5, 10);

                final double x = cutpos.getX();
                final double y = cutpos.getY();
                final double l = scale(cut.getCutLength());
                final double w2 = scale(cut.getKerf() / 2.0);
                if (cut.getDirection() == CutNode.Direction.HORIZONTAL) {
                    gc.strokeLine(x, y + w2, x + l, y + w2);
                } else {
                    gc.strokeLine(x + w2, y, x + w2, y + l);
                }
                gc.restore();

            } else if (node instanceof PanelNode) {
                final PanelNode panelNode = (PanelNode) node;

                final Position pos = scale(panelNode.getPosition());
                final Dimension dim = scale(panelNode.getDimension());

                gc.save();
                gc.setFill(Color.PINK);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(1);
                gc.fillRect(pos.getX(), pos.getY(), dim.getLength(), dim.getWidth());
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

                gc.save();
                gc.setGlobalAlpha(1);
                gc.setFill(Color.BLACK);
                gc.fillText(String.format("%s (%.1f x %.1f)",
                        panel.getTitle(),
                        panel.getDimension().getLength(),
                        panel.getDimension().getWidth()),
                        pos.getX() + 10,
                        pos.getY() + 20);

                gc.restore();

            }
        }
    }

    private static final double scale = 0.5;

    private double scale(double dim) {
        return dim * scale;
    }

    private Dimension scale(Dimension dim) {
        return new Dimension(dim.getLength() * scale, dim.getWidth() * scale);
    }

    private Position scale(Position dim) {
        return new Position(dim.getX() * scale, dim.getY() * scale);
    }

}
