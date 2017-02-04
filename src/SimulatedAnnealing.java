import java.util.Arrays;

public class SimulatedAnnealing {
	
	/*public static void main(String[] args){
		int[] nums = { 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5 };
		Bins cur = new Bins(nums);
		
		//how long search runs
		int runTime = 4000;//1 second

		//do search
		Node best = simulatedAnnealingSearch(cur, runTime);
		
		//print out best config of bins
		System.out.println("Bin1: " + Arrays.toString(best.bins[0]));
		System.out.println("Bin2: " +Arrays.toString(best.bins[1]));
		System.out.println("Bin3: " +Arrays.toString(best.bins[2]));
		System.out.println("Score: " + new Bins().calBins(best.bins));
	}*/
	
	/**
	 * Performs simulated annealing
	 * @param initBin
	 * @return
	 */
	public static Node simulatedAnnealingSearch(Bins initBin, int runTime) {
		double startingTemp = 1000;//helpful to have this for restarts
		
		// find score of initBin
		int curScore = initBin.calBins(initBin.bins);
		//set initial bin to be current node
		Node current = new Node(initBin.bins, curScore); 
		
		//get time that we should stop running search
		long endTime = System.currentTimeMillis() + runTime;
		
		//set best to be current
		Node best = current;
		
		//set initial temperature
		double t = startingTemp;
		//set a cooling rate;
		double coolingRate = 0.003;

		while (System.currentTimeMillis() < endTime) {
			//get a neighbor and find its score
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); 
					
			//see if we should accept move
			double a = acceptanceProbability(curScore, neighborScore, t);
			if(a > Math.random()){
				//select move
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				
				//keep track of best move across all restarts
				if(curScore > best.score){
					best = current;
				}
			}
			
			//restart search when t gets really small
			if(t < 0.000000001){
				curScore = initBin.calBins(initBin.bins);// find score of initBin
				//set initial bin to be current node
				current = new Node(initBin.bins, curScore); 
				
				//reset the temperature
				t = startingTemp;
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
