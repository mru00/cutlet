/*
 * Copyright (C) 2017 rudolf.muehlbauer@gmail.com
 */
package com.cutlet.lib.optimizer;

import com.cutlet.lib.model.Panel;
import com.cutlet.lib.model.Project;
import com.cutlet.lib.data.cuttree.FreeNode;
import java.util.List;
import java.util.logging.Logger;
import lombok.NonNull;
import org.jenetics.EnumGene;
import org.jenetics.Optimize;
import org.jenetics.engine.Codec;
import org.jenetics.engine.Engine;
import org.jenetics.engine.EvolutionResult;
import org.jenetics.engine.codecs;
import org.jenetics.util.ISeq;

/**
 *
 * @author rmuehlba
 */
public class GAOptimizationStrategy extends AbstractOptimizationStrategy {

    private static final Logger log = Logger.getLogger(GAOptimizationStrategy.class.getName());
    public static java.util.Random random = new java.util.Random();

    @Override
    public OptimizationResult optimize(@NonNull final Project project, 
            @NonNull final FitnessFunction fitness) {

        final ISeq<Panel> panels = ISeq.of(project.getPanels());
        final Codec<ISeq<Panel>, EnumGene<Panel>> codec = codecs.ofPermutation(panels);

        final Engine<EnumGene<Panel>, Double> engine
                = Engine.builder((seq) -> fitness.fitness(optimizeAux(project, seq.asList()).getStats()), codec)
                        .optimize(Optimize.MINIMUM)
                        .build();

        // 4.) Start the execution (evolution) and
        //     collect the result.
        ISeq<Panel> result = codec.decode(
                engine.stream()
                        .limit(100)
                        .collect(EvolutionResult.toBestGenotype()));

        return optimizeAux(project, result.asList());
    }

    public OptimizationResult optimizeAux(@NonNull final Project project, @NonNull final List<Panel> panels) {

        final OptimizationResult optimizationResult = new OptimizationResult();

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
}
