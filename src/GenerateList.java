import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateList {

	private Random rand = new Random();
	private int index;
	//private ArrayList<Integer> al = new ArrayList(); 
	private int[] al;
	private int listlength;
	private static int input = 150;
	
	/**
	 * Constructor
	 * @param listlength
	 * @throws IOException 
	 */
	public GenerateList(int listlength) throws IOException
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
		this.FillArray();
		File fp = new File("test");
		this.writeFile(fp);
	}

	public int[] FillArray()
	{
		for (int j = 0; j<this.listlength; j++)
		{
			index = rand.nextInt(19) - 9;
			al[j] = index;
		}
		return al;
	}

	/**
	 * Create a txt file if does not exit
	 * @param  filename File
	 * @return boolean
	 */
	private static boolean createFile(File fn) throws IOException
	{
		boolean flag = false;
		try{  
			if(!fn.exists())
			{  
				fn.createNewFile();  
				flag = true;
			}  
		}catch(IOException e){  
			e.printStackTrace();  
		}  
		return true;
	}
	/**
	 * Write into a txt file
	 * @param String [][]
	 * @param filename
	 */
	private boolean writeFile(File fn) throws IOException
	{
		createFile(fn);
		boolean flag = false;
		int arraylength = al.length;
		FileWriter fp = null;
		try{
			fp = new FileWriter(fn);  
			for(int i = 0;i < arraylength; i++){
				fp.write(al[i] + " ");
			}
			fp.close();
			flag = true;
		}catch (IOException e){
			e.printStackTrace();  
		}
		return flag;
	}

	public static void main(String argv[]) throws IOException
	{
		new GenerateList(input);
	}
}