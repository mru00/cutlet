/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.PanelInstance;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import com.cutlet.lib.errors.OptimizationFailedException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import lombok.NonNull;
import xyz.thepathfinder.simulatedannealing.InfeasibleProblemException;
import xyz.thepathfinder.simulatedannealing.LinearDecayScheduler;
import xyz.thepathfinder.simulatedannealing.Problem;
import xyz.thepathfinder.simulatedannealing.Scheduler;
import xyz.thepathfinder.simulatedannealing.SearchState;
import xyz.thepathfinder.simulatedannealing.Solver;

/**
 *
 * @author rmuehlba
 */
public class SAOptimizationStrategy2 extends AbstractOptimizationStrategy {

    private static final Logger log = Logger.getLogger(SAOptimizationStrategy2.class.getName());
    public static java.util.Random random = new java.util.Random();

    @Override
    public OptimizationResult optimize(@NonNull Project project, @NonNull FitnessFunction fitness) throws OptimizationFailedException {
        try {
            // higher numbers will break travis CI unit tests...
            int NUMBER_OF_STEPS = 10000;
            double INITIAL_TEMPERATURE = 10;

            Scheduler scheduler = new LinearDecayScheduler(INITIAL_TEMPERATURE, NUMBER_OF_STEPS);
            Problem<OptState> problem = new OptProblem(project, new SimpleFitnessFunction());
            Solver<OptState> solver = new Solver(problem, scheduler);
            OptState solution = solver.solve();

            return solution.result;
        } catch (InfeasibleProblemException ex) {
            throw new OptimizationFailedException(ex);
        }
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

    class OptProblem implements Problem<OptState> {

        Project project;
        FitnessFunction fitness;

        public OptProblem(@NonNull Project project, @NonNull FitnessFunction fitness) {
            this.project = project;
            this.fitness = fitness;
        }

        @Override
        public double energy(@NonNull OptState t) {
            t.result = optimizeAux(project, t.panels);
            return fitness.fitness(t.result.getStats());
        }

        @Override
        public OptState initialState() throws InfeasibleProblemException {
            OptState initial = new OptState();
            initial.panels = project.getPanelInstances();
            return initial;
        }
    }

    class OptState implements SearchState<OptState> {

        OptimizationResult result = null;
        List<PanelInstance> panels;

        @Override
        public OptState step() {
            final int posA = random.nextInt(panels.size());
            int posB = random.nextInt(panels.size());
            while (posA == posB) {
                posB = random.nextInt(panels.size());
            }

            OptState other = new OptState();
            other.result = result;
            other.panels = new ArrayList<>(panels);
            other.panels.set(posA, panels.get(posB));
            other.panels.set(posB, panels.get(posA));
            return other;
        }

    }

}
