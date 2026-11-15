
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Exhaustively searches all 2^n subsets to find the three best valid payloads
 * under both baseline constraints and the Week 3 revised rules.
 *
 * Team Name: Team 1
 * Team Members: Moriancumer Hurtado, 
 *               Jacob Gorham (primary author),
 *               Mubarak Alrashdi, 
 *               Luis Agramonte Peralta
 * Course: CS 2430-502, Fall 2026
 * Project: Programming Project 3
 */
public class BruteForce {

    /** Stores a valid candidate subset and its calculated totals. */
    private static class Result {
        private final List<Experiment> chosen;
        private final double weight;
        private final int rating;
        private final int subsetNumber;
        private final boolean thermalCreditApplied;

        Result(List<Experiment> chosen, double weight, int rating, int subsetNumber, boolean thermalCreditApplied) {
            this.chosen = chosen;
            this.weight = weight;
            this.rating = rating;
            this.subsetNumber = subsetNumber;
            this.thermalCreditApplied = thermalCreditApplied;
        }
    }

    /** Checks every subset and prints the top three valid results. */
    public static void use(Experiment[] experiments, double weightLimit) {
        RevisedRules.validateInputs(experiments, weightLimit);

        List<Result> validResults = new ArrayList<>();
        int numberOfSubsets = 1 << experiments.length; // 2^n subsets

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
                validResults.add(new Result(chosen, totalWeight, totalRating, subset, false));
            }
        }

        sortAndPrintTopThree("Brute Force / Exhaustive Search", validResults, numberOfSubsets, weightLimit);
    }

    /**
     * Exhaustively checks every subset of experiments under the Week 3 revised rules
     * (at most 2 solar experiments from {2, 3, 7}, -7.0 kg thermal mass credit when both
     * {6, 12} are present, and Experiment 11a requires Experiment 11) and prints the top
     * three valid combinations.
     */
    public static void revisedRuleUse(Experiment[] experiments, double weightLimit) {
        RevisedRules.validateInputs(experiments, weightLimit);

        List<Result> validResults = new ArrayList<>();
        int numberOfSubsets = 1 << experiments.length; // 2^n subsets

        for (int subset = 0; subset < numberOfSubsets; subset++) {
            List<Experiment> chosen = new ArrayList<>();
            double totalWeight = 0;
            int totalRating = 0;

            int solarExperimentCount = 0;
            boolean experiment6 = false;
            boolean experiment12 = false;
            boolean experiment11 = false;
            boolean experiment11a = false;
            

            for (int i = 0; i < experiments.length; i++) {
                if ((subset & (1 << i)) != 0) {
                    Experiment e = experiments[i];
                    String order = e.getOrder();

                    chosen.add(e);
                    totalWeight += e.getWeightKg();
                    totalRating += e.getRating();

                    if (order.equals("2") || order.equals("3") || order.equals("7")) {
                    	solarExperimentCount++;
                    }
                    if (order.equals("6")) experiment6 = true;
                    if (order.equals("12")) experiment12 = true;
                    if (order.equals("11")) experiment11 = true;
                    if (order.equalsIgnoreCase("11a")) experiment11a = true;
                }
            }

            // Rule 1: At most 2 solar instruments ({2, 3, 7})
            if (solarExperimentCount > 2) {
                continue;
            }

            // Rule 3: Experiment 11a requires Experiment 11
            if (experiment11a && !experiment11) {
                continue;
            }

            // Rule 2: -7.0 kg thermal mass credit when both Experiments 6 and 12 are selected
            boolean applyThermalCredit = (experiment6 && experiment12);
            if (applyThermalCredit) {
                totalWeight -= 7.0;
            }

            if (totalWeight <= weightLimit) {
                validResults.add(new Result(chosen, totalWeight, totalRating, subset, applyThermalCredit));
            }
        }

        sortAndPrintTopThree("REVISED RULES - Brute Force / Exhaustive Search", validResults, numberOfSubsets, weightLimit);
    }

    /** Sorts valid subsets by highest rating (lighter weight breaks ties) and prints the top 3. */
    private static void sortAndPrintTopThree(String title, List<Result> validResults,
                                             int numberOfSubsets, double weightLimit) {
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

        System.out.println();
        System.out.println(title);
        System.out.println("Subsets checked: " + numberOfSubsets);
        System.out.println("Valid subsets: " + validResults.size());

        int resultsToShow = Math.min(3, validResults.size());
        for (int i = 0; i < resultsToShow; i++) {
            Result result = validResults.get(i);
            System.out.println();
            System.out.println("Rank " + (i + 1) + (i == 0 ? " - OPTIMAL" : ""));
            for (Experiment experiment : result.chosen) {
                System.out.printf("- %-26s (Weight: %.1f kg, Rating: %d)%n",
                        experiment.getExperimentName(), experiment.getWeightKg(), experiment.getRating());
            }
            if (result.thermalCreditApplied) {
                System.out.println("Note: 7.0 kg Thermal Mass Credit applied (Experiments 6 & 12).");
            }
            System.out.printf("Total Weight: %.1f kg / %.1f kg%n", result.weight, weightLimit);
            System.out.println("Total Rating: " + result.rating);
        }
        System.out.println();
    }
}
