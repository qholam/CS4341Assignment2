
public class SimulatedAnnealing {
	
	/**
	 * Performs simulated annealing
	 * @param initBin
	 * @return
	 */
	public Node simulatedAnnealingSearch(Bins initBin) {
		int curScore = initBin.calBins(initBin.bins);// find score of initBin
		//set initial bin to be current node
		Node current = new Node(initBin.bins, curScore); 
		
		//set best to be current
		Node best = current;
		
		//set initial temperature
		double t = 1000;
		//set a cooling rate;
		double coolingRate = 0.001;

		while (t != 0) {
			//get a neighbor and find its score
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); 
					
			//see if we should accept move
			if(acceptanceProbability(curScore, neighborScore, t) > Math.random()){
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				
				//keep track of best move
				if(neighborScore > curScore){
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
	public double acceptanceProbability(int curScore, int newScore, double t){
		//if new score is better always accept
		if(newScore > curScore)
			return 1.0;
		
		//otherwise return some probability(found this function in the book)
		return Math.exp((newScore-curScore)/t);
	}
	
	/**
	 * Generates a random neighbor bin by swapping two elements in the 2d array
	 * @param bins
	 * @return
	 */
	public int[][] generateNeighbor(int[][] bins) {
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
