package P3;

import java.util.ArrayList;
import java.util.List;

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
	private List<Experiment> experiments;
	private double capacityKg;

	
	/**
	 * Constructor for HighestRatingFirst including the experiments to pick from
	 * and the maximum weight we can carry.
	 * 
	 * @param experiments  all the experiments we can pick from
	 * @param capacityKg   the max weight we can carry (700 kg)
	 */
	public HighestRatingFirst(List<Experiment> experiments, double capacityKg) {
		super();
		this.experiments = experiments;
		this.capacityKg = capacityKg;
	}
	
	/**
	 * Goes through the experiments from the highest to lowest rating,
	 * then it will add each one if it still fits under the weight limit.
	 * 
	 * @return   the experiments that got picked
	 */
	public List<Experiment> select() {
        
		// making a copy so that we do not mes up the original list
		List<Experiment> sortedList = new ArrayList<>(experiments);
        
		//sorting list so that the highest ratings come first
		sortedList.sort((a, b) -> b.getRating() - a.getRating());

		List<Experiment> payload = new ArrayList<>();
		double totalWeight = 0;
        
		// checks the list and adds whatever can still fit
		for (Experiment e : sortedList) {
			if (totalWeight + e.getWeightKg() <= capacityKg) {
				payload.add(e);
				totalWeight += e.getWeightKg();
			}

		}

		return payload;
	}
	
	/**
	 * Prints out the experiments that got picked with
	 * the total weight and total rating.
	 * 
	 * @param payload
	 */
	public void printResult(List<Experiment> payload) {
		double totalweight = 0;
		int totalRating = 0;
		
		System.out.println("--- Highest Rating First ---");
		
		for(Experiment e: payload) {
			System.out.println(e);
			totalweight += e.getWeightKg();
			totalRating += e.getRating();
		}
		System.out.printf("Total Weight: %.1f kg%n", totalweight);
		
		System.out.println("Total Rating: " + totalRating);
		
		System.out.println();
	}

}
