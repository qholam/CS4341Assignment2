
public class SimulatedAnnealing {
	public static Node firstChoiceHillClimbingSearch(Bins initBin) {
		int curScore = initBin.calBins(initBin.bins);// find score of initBin
		Node current = new Node(initBin.bins, curScore);
		
		int numMovesGenerated = 0;

		while (true) {
			int[][] neighbor = generateNeighbor(current.bins);
			int neighborScore = new Bins().calBins(neighbor); // find score of neighbor

			if(temperature is 0)
				break;
			
			if (higher than some probability) {
				current = new Node(neighbor, neighborScore);
				curScore = neighborScore;
				numMovesGenerated = 0;
			} else {
				numMovesGenerated++;
			}
			
			decrease probability

		}

		// TODO: add restart
		return current;
	}
}
