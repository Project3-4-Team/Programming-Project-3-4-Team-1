import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Exhaustively searches all subsets to find the three best valid payloads.
 *
 * Team Name: Team 1
 * Team Members: Moriancumer Hurtado, Jacob Gorham,
 *               Mubarak Alrashdi, Luis Agramonte Peralta
 * Course: CS 2430-502, Fall 2026
 * Project: Programming Project 3
 * Primary author: Jacob Gorham
 */
public class BruteForce {

    /** Stores a subset and its calculated totals. */
    private static class Result {
        private final List<Experiment> chosen;
        private final double weight;
        private final int rating;
        private final int subsetNumber;

        Result(List<Experiment> chosen, double weight, int rating, int subsetNumber) {
            this.chosen = chosen;
            this.weight = weight;
            this.rating = rating;
            this.subsetNumber = subsetNumber;
        }
    }

    /** Checks every subset and prints the top three valid results. */
    public static void use(Experiment[] experiments, double weightLimit) {
        List<Result> validResults = new ArrayList<>();
        int numberOfSubsets = 1 << experiments.length; // 2 to the power of n

        // Each bit represents whether an experiment is included in a subset.
        for (int subset = 0; subset < numberOfSubsets; subset++) {
            List<Experiment> chosen = new ArrayList<>();
            double totalWeight = 0;
            int totalRating = 0;

            for (int i = 0; i < experiments.length; i++) {
                if ((subset & (1 << i)) != 0) {
                    chosen.add(experiments[i]);
                    totalWeight += experiments[i].getWeightKg();
                    totalRating += experiments[i].getRating();
                }
            }

            if (totalWeight <= weightLimit) {
                validResults.add(new Result(chosen, totalWeight, totalRating, subset));
            }
        }

        // Highest rating first; lighter combinations win a tie.
        Collections.sort(validResults, new Comparator<Result>() {
            @Override
            public int compare(Result a, Result b) {
                int ratingComparison = Integer.compare(b.rating, a.rating);
                if (ratingComparison != 0) {
                    return ratingComparison;
                }
                int weightComparison = Double.compare(a.weight, b.weight);
                if (weightComparison != 0) {
                    return weightComparison;
                }
                return Integer.compare(a.subsetNumber, b.subsetNumber);
            }
        });

        System.out.println("Brute Force / Exhaustive Search");
        System.out.println("Subsets checked: " + numberOfSubsets);
        System.out.println("Valid subsets: " + validResults.size());

        int resultsToShow = Math.min(3, validResults.size());
        for (int i = 0; i < resultsToShow; i++) {
            Result result = validResults.get(i);
            System.out.println();
            System.out.println("Rank " + (i + 1) + (i == 0 ? " - OPTIMAL" : ""));
            for (Experiment experiment : result.chosen) {
                System.out.println("- " + experiment.getExperimentName());
            }
            System.out.printf("Total Weight: %.1f kg / %.1f kg%n", result.weight, weightLimit);
            System.out.println("Total Rating: " + result.rating);
        }
    }
}
