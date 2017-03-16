/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.optimizer;

// uses http://cs.gettysburg.edu/~tneller/resources/sls/index.html
import com.cutlet.lib.model.PanelInstance;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import com.cutlet.lib.tneller.SimulatedAnnealer;
import com.cutlet.lib.tneller.State;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import lombok.NonNull;

/**
 *
 * @author rmuehlba
 */
public class SAOptimizationStrategy extends AbstractOptimizationStrategy {

    private final Logger log = Logger.getLogger(SAOptimizationStrategy.class.getName());
    public static java.util.Random random = new java.util.Random();

    @Override
    public OptimizationResult optimize(@NonNull Project project, @NonNull FitnessFunction fitness) {

        final int ITERATIONS = 10000;

        // Uncomment the desired problem below:
        OptState state = new OptState(project, fitness);
        //State state = new BinPackingProblem();
        state.panels = project.getPanelInstances();

        // Uncomment the desired stochastic local search below:
        //OptState minState = (OptState) new HillDescender(state).search(ITERATIONS);
        OptState minState = (OptState) new SimulatedAnnealer(state, 1, .99999).search(ITERATIONS);

        return minState.result;
    }

    public OptimizationResult optimizeAux(@NonNull Project project, @NonNull List<PanelInstance> panels) {

        OptimizationResult optimizationResult = new OptimizationResult();

        for (PanelInstance p : panels) {
            FreeNode candidate = findSheet(optimizationResult, p);
            if (candidate == null) {
                optimizationResult.createNewLayout(p);
                candidate = findSheet(optimizationResult, p);
            }

            candidate = cutToFit(candidate, project, p);
            candidate.setPanel(p);
        }

        return optimizationResult;
    }

    class OptState implements State {

        Project project;
        FitnessFunction fitness;
        List<PanelInstance> panels, panelsPrev;
        OptimizationResult result;

        public OptState(@NonNull Project project, @NonNull FitnessFunction fitness) {
            this.project = project;
            this.fitness = fitness;
            this.panelsPrev = this.panels;
        }

        @Override
        public void step() {

            panelsPrev = new ArrayList<>(panels);

            final int posA = random.nextInt(panels.size());
            int posB = random.nextInt(panels.size());
            while (posA == posB) {
                posB = random.nextInt(panels.size());
            }

            panels.set(posA, panelsPrev.get(posB));
            panels.set(posB, panelsPrev.get(posA));
        }

        @Override
        public void undo() {
            panels = panelsPrev;
        }

        @Override
        public double energy() {
            result = optimizeAux(project, panels);
            return fitness.fitness(result.getStats());
        }

        public Object clone() {
            OptState copy = new OptState(project, fitness);
            copy.panels = new ArrayList<>(panels);
            copy.panelsPrev = new ArrayList<>(panelsPrev);
            copy.result = result;
            return copy;
        }

    }

}
