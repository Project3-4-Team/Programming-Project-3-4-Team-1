import java.util.Arrays;
/**
 * Selects experiments for project 3: Knapsack Problem
 * This class uses the highest rating first and will keep adding
 * experiments as long as the total weight stays under the limit.
 * 
 * 
 * Team Name: Team 1
 * Team Members: Luis Agramonte,
 *               Mubarak Alrashdi,
 *               Moriancumer Hurtado,
 *               Jacob Gorham
 * 
 * Course: CS 2430-502, Fall 2026
 * Project: Programming Project 3
 */
public class HighestRatingFirst {
	
	/**
	 * Executes the Highest Rating First greedy selection strategy.
	 * Sorts a copy of the array in descending order by rating (breaking ties by
	 * lighter weight first), adds experiments that fit within the weight limit,
	 * and prints a formatted summary report.
	 * 
	 * @param experiments	an array of Experiment objects to evaluate and sort
	 * @param weightLimit	weight limit in kilograms
	 */
	public static void use(Experiment[] experiments, double weightLimit) {
		RevisedRules.validateInputs(experiments, weightLimit);
		
		Experiment[] sorted = experiments.clone();
		
		Arrays.sort(sorted, (a, b) -> {
			int comparison = Integer.compare(b.getRating(), a.getRating());
			return (comparison != 0) ? comparison : Double.compare(a.getWeightKg(), b.getWeightKg());
		});
		
		double currentWeightKg = 0;
		int totalRating = 0;
		
		System.out.println();
		System.out.println("Highest Rating First's Chosen Experiments");
		System.out.println();
		
		for(Experiment e : sorted) {
			if (currentWeightKg + e.getWeightKg() <= weightLimit) {
				currentWeightKg += e.getWeightKg();
				totalRating += e.getRating();
				System.out.printf("- %-26s (Weight: %.1f kg, Rating: %d)%n", 
                        e.getExperimentName(), e.getWeightKg(), e.getRating());
			}
		}
		
		System.out.println();
		System.out.println("Total Weight: " + currentWeightKg + " kg / " + weightLimit + " kg");
		System.out.println("Total Rating: " + totalRating);
		System.out.println();
			
	}

	
	// Order Change (helper class)
	
	/**
	 * Executes the Highest Rating First greedy strategy under the Week 3 revised rules.
	 * Ranking Rule: Sorts experiments in descending order by rating, breaking ties by
	 * ascending weight (lighter experiment first). Each tentative addition is evaluated
	 * by RevisedRules using adjusted mass (accounting for the -7.0 kg thermal credit
	 * for {6, 12}), the 2-instrument solar cap ({2, 3, 7}), and deferred buffering of
	 * Experiment 11a until Experiment 11 is selected.
	 *
	 * @param experiments 		an array of Experiment objects to evaluate and sort
	 * @param weightLimit 		weight limit in kilograms
	 */
	public static void revisedRuleUse(Experiment[] experiments, double weightLimit) {
		RevisedRules.validateInputs(experiments, weightLimit);
		
		Experiment[] sorted = experiments.clone();
			
		Arrays.sort(sorted, (a, b) -> {
			int cmp = Integer.compare(b.getRating(), a.getRating());
			return (cmp != 0) ? cmp : Double.compare(a.getWeightKg(), b.getWeightKg());
		});

		RevisedRules newRules = new RevisedRules(weightLimit);
		for (Experiment e: sorted) {
			newRules.revisedUse(e);
		}
			
			
		newRules.printReport("Highest Rating First's Chosen Experiments", false);
				
	}

}
