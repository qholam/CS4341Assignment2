import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;

public class optimize {

	private int[] array;

	public static String readArray(File fn) {
		StringBuilder result = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(fn));
			String s = null;
			while ((s = br.readLine()) != null) {// read a line
				result.append(System.lineSeparator() + s);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	public static int[] storeArray(String str) {
		String[] temp = str.split(" ");
		int[] ary = new int[temp.length];
		int i = 0;
		for (String token : temp) {
			token.replaceAll("\uFEFF", "");
			ary[i++] = Integer.parseInt(token.trim());
		}
		return ary;
	}

	public static void main(String argv[]) {
		// get user input
		optimize opt = new optimize();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter the type of optimization: ");
		String type = scanner.next();
		System.out.print("Enter the filename: ");
		String filename = scanner.next();
		System.out.print("Enter the amount of time: ");
		double time = scanner.nextDouble();

		// open file
		File fn = new File(filename);
		int[] nums = storeArray(readArray(fn));
		Bins cur = new Bins(nums);

		// set how long we want this to run
		int runTime = (int) (time * 1000); // convert to milliseconds

		// select search type based on user input
		if (type.equals("hill")) {
			Node best = FirstChoiceHillClimbing.firstChoiceHillClimbingSearch(cur, runTime);
			System.out.println("Bin1: " + Arrays.toString(best.bins[0]));
			System.out.println("Bin2: " + Arrays.toString(best.bins[1]));
			System.out.println("Bin3: " + Arrays.toString(best.bins[2]));
			System.out.println("Score: " + new Bins().calBins(best.bins));
		} else if (type.equals("annealing")) {
			Node best = SimulatedAnnealing.simulatedAnnealingSearch(cur, runTime);
			System.out.println("Bin1: " + Arrays.toString(best.bins[0]));
			System.out.println("Bin2: " + Arrays.toString(best.bins[1]));
			System.out.println("Bin3: " + Arrays.toString(best.bins[2]));
			System.out.println("Score: " + new Bins().calBins(best.bins));
		} else if (type.equals("ga")) {
			// make population
			ArrayList<Bins> pop = new ArrayList<Bins>();
			for(int i = 0; i < 100; i++){
				pop.add(new Bins(nums));
			}

			double elitism = 0.25;
			Bins best = ga.gaSearch(pop, runTime, elitism);

			System.out.println("Bin1: " + Arrays.toString(best.bins[0]));
			System.out.println("Bin2: " + Arrays.toString(best.bins[1]));
			System.out.println("Bin3: " + Arrays.toString(best.bins[2]));
			System.out.println("Score: " + new Bins().calBins(best.bins));
		} else {
			System.out.println("Wrong optimization type");
		}
		// run search

	}
}
