
public class FirstChoiceHillClimbing {
	public Node firstChoiceHillClimbing(int[][] intitBin){
         int curScore = ;//find score of initBin
         Node current = makeNode(initBin, initScore);
         
         int numMovesGenerated = 0;
         
         while(numMovesGenerated < 100){
           int[][] neighbor = generateNeighbor(current.bins);
           int neighborScore = ; //find score of neighbor
           if( neighborScore > curScore){
             current = makeNode(neighbor, neighbor score);
             curScore = neighborScore;
             numMovesGenerated = 0;
           }
           else
             numMovesGenerated++;
         }
         if(time allows){
           //redo first choice hill climbing
         }
         else{
        	 return current;
         }
       }

	public int[][] generateNeighbor(int[][] bins){
         int a = ;//random # between 0 and bins.length
         int b = ;//random # between 0 and bins[0].length
         int c = ;//random # between 0 and bins.length
         int d = ;//random # between 0 and bins[0].length
         
         int[][] neighbor = bins;
         int temp = neighbor[a][b];
         //swap to numbers
         neighbor[a][b] = neighbor[c][d];
         neighbor[c][d] = temp;
         
         return neighbor;
       }
}
