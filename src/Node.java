
public class Node {
	int[][] bins;
	int score;

	public Node() {
	}

	public Node makeNode(int[][] bin, int score) {
		Node node = new Node();
		node.bins = bin;
		node.score = score;
		
		return node;
	}
}
