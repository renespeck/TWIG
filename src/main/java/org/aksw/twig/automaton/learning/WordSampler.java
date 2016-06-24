package org.aksw.twig.automaton.learning;

import org.aksw.twig.structs.AVLTree;

import java.util.*;
import java.util.stream.Collectors;

public class WordSampler {

    private Map<String, AVLTree<WordChanceMapping>> sampleMap = new HashMap<>();

    private Random r = new Random();

    public String sample(String predecessor) {
        AVLTree<WordChanceMapping> tree = sampleMap.get(predecessor);
        if (tree == null) {
            throw new IllegalArgumentException("Predecessor not present");
        }

        WordChanceMapping random = tree.findClosest(new WordChanceMapping("", r.nextDouble()));
        return random == null ? null : random.word;
    }

    public void reseed(long seed) {
        r.setSeed(seed);
    }

    public WordSampler(final IWordMatrix matrix) {
        matrix.getPredecessors().stream()
                .forEach(predecessor -> {

                    AVLTree<WordChanceMapping> tree = new AVLTree<>();

                    List<WordChanceMapping> list = matrix.getMappings(predecessor).entrySet().stream()
                            .map(entry -> new WordChanceMapping(entry.getKey(), entry.getValue()))
                            .collect(Collectors.toList());
                    Collections.sort(list, WordChanceMapping::compareTo);

                    double cumulativeChance = 0d;
                    for (WordChanceMapping mapping: list) {
                        mapping.chance += cumulativeChance;
                        cumulativeChance = mapping.chance;
                        tree.add(mapping);
                    }

                    sampleMap.put(predecessor, tree);
                });
    }

    private static class WordChanceMapping implements Comparable<WordChanceMapping> {

        private String word;

        private double chance;

        WordChanceMapping(final String word, final double chance) {
            this.word = word;
            this.chance = chance;
        }

        @Override
        public int compareTo(WordChanceMapping mapping) {
            double delta = chance - mapping.chance;
            return delta < 0 ? -1 : (int) delta;
        }
    }
}
