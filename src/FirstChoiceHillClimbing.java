import java.util.Arrays;

public class FirstChoiceHillClimbing {
	/*
	public static void main(String[] args) {
		int[] nums = { 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5, 0, -4, 6, 8, 5, -7, -6, -7, 5 };
		Bins cur = new Bins(nums);
		// set how long we want this to run
		int runTime = 1000; // 1 second
		
		//run search
		Node best = firstChoiceHillClimbingSearch(cur, runTime);

		// print out best config of bins
		System.out.println("Bin1: " + Arrays.toString(best.bins[0]));
		System.out.println("Bin2: " + Arrays.toString(best.bins[1]));
		System.out.println("Bin3: " + Arrays.toString(best.bins[2]));
		System.out.println("Score: " + new Bins().calBins(best.bins));
	}
	*/

	/**
	 * Runs a single first choice hill climbing search
	 * 
	 * @param initBin
	 * @return
	 */
	public static Node firstChoiceHillClimbingSearch(Bins initBin, int runTime) {
		// find score of initBin
		int curScore = initBin.calBins(initBin.bins);
		Node current = new Node(initBin.bins, curScore);
		
		//keep track of best score across all restarts
		Node best = current;

		int numMovesGenerated = 0;
		
		//how long we should run program for
		long endTime = System.currentTimeMillis() + runTime;

		//keep running search while we have time
		while (System.currentTimeMillis() < endTime) {
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); // find score of neighbor
			
			//check if we should take this move
			if (neighborScore > curScore) {
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				
				//reset count of number of consecutive moves that were not helpful
				numMovesGenerated = 0;
				
				//keep track of best move across all instances of this search
				if(curScore > best.score){
					best = current;
				}
				
			} 
			else {
				//increment count of number of consecutive generated moves that were not helpful
				numMovesGenerated++;
			}

			//check if we made 100 consecutive unhelpful moves, if so restart search since we know we have time
			if(numMovesGenerated == 100){
				//set current to be a new starting point
				curScore = initBin.calBins(initBin.bins);
				current = new Node(initBin.bins, curScore);
				
				//reset count
				numMovesGenerated = 0;
			}

		}

		return best;
	}

	/**
	 * Generates a neighbor by swapping 2 elements in the 2d array.
	 * 
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
		for (int i = 0; i < bins.length; i++) {
			for (int j = 0; j < bins[0].length; j++) {
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
