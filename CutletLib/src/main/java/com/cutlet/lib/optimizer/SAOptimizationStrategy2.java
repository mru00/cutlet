/*
 * Copyright (C) 2017 rudolf.muehlbauer@intel.com
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private final Logger log = Logger.getLogger(SAOptimizationStrategy2.class.getName());
    public static java.util.Random random = new java.util.Random();

    @Override
    public OptimizationResult optimize(Project project, FitnessFunction fitness) {
        try {
            double INITIAL_TEMPERATURE = 10;
            int NUMBER_OF_STEPS = 1000000;
            Scheduler scheduler = new LinearDecayScheduler(INITIAL_TEMPERATURE, NUMBER_OF_STEPS);
            Problem<OptState> problem = new OptProblem(project, new SimpleFitnessFunction());
            Solver<OptState> solver = new Solver(problem, scheduler);
            OptState solution = solver.solve();

            return solution.result;
        } catch (InfeasibleProblemException ex) {
            Logger.getLogger(SAOptimizationStrategy2.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public OptimizationResult optimizeAux(Project project, List<Panel> panels) {

        OptimizationResult optimizationResult = new OptimizationResult();

        for (Panel p : panels) {
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

        public OptProblem(Project project, FitnessFunction fitness) {
            this.project = project;
            this.fitness = fitness;
        }

        @Override
        public double energy(OptState t) {
            t.result = optimizeAux(project, t.panels);
            return fitness.fitness(t.result.getStats());
        }

        @Override
        public OptState initialState() throws InfeasibleProblemException {
            OptState initial = new OptState();
            initial.panels = project.getPanels();
            return initial;
        }
    }

    class OptState implements SearchState<OptState> {

        OptimizationResult result = null;
        List<Panel> panels;

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
