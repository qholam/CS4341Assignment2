
public class FirstChoiceHillClimbing {

	public Node firstChoiceHillClimbingSearch(Bins initBin) {
		int curScore = initBin.calBins(initBin.bins);// find score of initBin
		Node current = new Node(initBin.bins, curScore);

		int numMovesGenerated = 0;

		while (numMovesGenerated < 100) {
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); // find score of
																// neighbor
			if (neighborScore > curScore) {
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				numMovesGenerated = 0;
			} else
				numMovesGenerated++;
		}

		//TODO: add restart
		
		return current;
	}

	public int[][] generateNeighbor(int[][] bins){
         int a = 0 + (int)(Math.random() * bins.length);//random # between 0 and bins.length
         int b = 0 + (int)(Math.random() * bins[0].length);//random # between 0 and bins[0].length
         int c = 0 + (int)(Math.random() * bins.length);//random # between 0 and bins.length
         int d = 0 + (int)(Math.random() * bins[0].length);//random # between 0 and bins[0].length
         
         int[][] neighbor = bins;
         int temp = neighbor[a][b];
         //swap to numbers
         neighbor[a][b] = neighbor[c][d];
         neighbor[c][d] = temp;
         
         return neighbor;
       }
}
