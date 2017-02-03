import java.util.Arrays;

public class FirstChoiceHillClimbing {
	
	public static void main(String[] args){
		runSearch(new Bins());
	}

	/**
	 * Creates bins and runs first choice hill climbing with repeats
	 */
	public static void runSearch(Bins initBin) {
		int[] nums = { 0, -4, 6, 8, 5, -7, -6, -7, 5 };
		//Bins cur = initBin;
		Bins cur = new Bins(nums);

		//do initial search
		Node best = firstChoiceHillClimbingSearch(cur);
		
		//repeat firstChoiceHillClimbingSearch 10 more times with new start point
		for(int i = 0; i < 10; i++){
			//get a new starting point
			//cur = new Bins(b.num);
			cur = new Bins(nums);
			
			Node temp = firstChoiceHillClimbingSearch(cur);
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
	 * Runs a single first choice hill climbing search
	 * @param initBin
	 * @return
	 */
	public static Node firstChoiceHillClimbingSearch(Bins initBin) {
		int curScore = initBin.calBins(initBin.bins);// find score of initBin
		Node current = new Node(initBin.bins, curScore);
		
		int numMovesGenerated = 0;

		while (numMovesGenerated < 100) {
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); // find score of neighbor


			if (neighborScore > curScore) {
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				numMovesGenerated = 0;
			} else {
				numMovesGenerated++;
			}

		}

		// TODO: add restart
		return current;
	}

	/**
	 * Generates a neighbor by swapping 2 elements in the 2d array.
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
