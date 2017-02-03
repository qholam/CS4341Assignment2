import java.util.ArrayList;
import java.util.Random;

public class GenerateList {

	private Random rand = new Random();
	private int index;
	//private ArrayList<Integer> al = new ArrayList(); 
	private int[] al;
	private int listlength;
	private static int input = 6;

	/**
	 * Constructor
	 * @param listlength
	 */
	public GenerateList(int listlength)
	{
		al = new int[listlength];
		if(listlength % 3 == 0)
		{
		this.listlength = listlength;
		} else if(listlength % 3 == 1)
		{
			this.listlength = listlength + 2;
		} else 
		{
			this.listlength = listlength + 1;
		}
	}

	public int[] FillArray()
	{
		for (int j = 0; j<this.listlength; j++)
		{
			index = rand.nextInt(19) - 9;
			System.out.println(index);
			al[j] = index;
		}
		return al;
	}

	public static void main(String argv[])
	{
		new GenerateList(input);
	}
}