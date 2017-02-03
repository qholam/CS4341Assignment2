import java.util.Arrays;

public class SimulatedAnnealing {
	
	public static void main(String[] args){
		runSearch(new Bins());
	}
	
	/**
	 * Creates bins and runs simulated annealing with repeats
	 */
	public static void runSearch(Bins initBin) {
		int[] nums = { 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5 };
		//Bins cur = initBin;
		Bins cur = new Bins(nums);

		//do initial search
		Node best = simulatedAnnealingSearch(cur);
		
		//repeat simulated annealing 10 more times with new start point
		for(int i = 0; i < 0; i++){
			//get a new starting point
			//cur = new Bins(b.num);
			cur = new Bins(nums);
			
			Node temp = simulatedAnnealingSearch(cur);
			if(temp.score > best.score)
				best = temp;
		}
		
		//print out best config of bins
		System.out.println("Bin1: " + Arrays.toString(best.bins[0]));
		System.out.println("Bin2: " +Arrays.toString(best.bins[1]));
		System.out.println("Bin3: " +Arrays.toString(best.bins[2]));
		System.out.println("Score: " + new Bins().calBins(best.bins));
	}
	
	/**
	 * Performs simulated annealing
	 * @param initBin
	 * @return
	 */
	public static Node simulatedAnnealingSearch(Bins initBin) {
		int curScore = initBin.calBins(initBin.bins);// find score of initBin
		//set initial bin to be current node
		Node current = new Node(initBin.bins, curScore); 
		
		//set best to be current
		Node best = current;
		
		//set initial temperature
		double t = 10000;
		//set a cooling rate;
		double coolingRate = 0.0001;

		while (t > 1) {
			//get a neighbor and find its score
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); 
					
			//see if we should accept move
			double a = acceptanceProbability(curScore, neighborScore, t);
			if(a > Math.random()){
				//select move
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				
				//keep track of best move
				if(curScore > best.score){
					best = current;
				}
			}
			
			//reduce the temperature
			t *= 1 - coolingRate;
		}

		// TODO: add restart
		return best;
	}
	
	/**
	 * Returns the probability of accepting the newScore
	 * @param curScore
	 * @param newScore
	 * @param t
	 * @return
	 */
	public static double acceptanceProbability(int curScore, int newScore, double t){
		//if new score is better always accept
		if(newScore > curScore)
			return 1.0;
		else{
			//otherwise return some probability(found this function in the book)
			//This returns some number between 0.000....1 and 0.999..
			//The higher t is, the closer the probability of taking the move will be
			return Math.exp((newScore-curScore)/t);
		}
	}
	
	/**
	 * Generates a random neighbor bin by swapping two elements in the 2d array
	 * @param bins
	 * @return
	 */
	public static int[][] generateNeighbor(int[][] bins) {
		int a = 0 + (int) (Math.random() * bins.length);// random # between 0
														// and bins.length
		int b = 0 + (int) (Math.random() * bins[0].length);// random # between 0
															// and
															// bins[0].length
		int c = 0 + (int) (Math.random() * bins.length);// random # between 0
														// and bins.length
		int d = 0 + (int) (Math.random() * bins[0].length);// random # between 0
															// and
															// bins[0].length
		
		int[][] neighbor = new int[bins.length][bins[0].length];
		for(int i = 0; i < bins.length; i++){
			for(int j = 0; j < bins[0].length; j++){
				neighbor[i][j] = bins[i][j];
			}
		}
		int temp = neighbor[a][b];
		// swap to numbers
		neighbor[a][b] = neighbor[c][d];
		neighbor[c][d] = temp;

		return neighbor;
	}
}
