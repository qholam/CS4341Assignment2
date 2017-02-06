import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ga {
	public static Bins gaSearch(ArrayList<Bins> population, int runTime, double elitism) {
		long endTime = System.currentTimeMillis() + runTime;
		int size = population.size();
		sortByFitness(population);
		while (System.currentTimeMillis() < endTime) {
			// sort population by fitness (sorts from highest score to lowest
			// score)

			// choose elites to preserve top 20% for now
			ArrayList<Bins> elites = new ArrayList<Bins>(population.subList(0, (int) (elitism * size)));
			ArrayList<Bins> newPopulation = new ArrayList<Bins>();

			for (int i = 0; i < population.size(); i++) {
				// select two bins out random
				Bins x = randomSelection(population);
				Bins y = randomSelection(population);

				// x and y will produce a child
				Bins child = reproduce(x, y);

				// mutate child by swapping two random numbers
				int a = 0 + (int) (Math.random() * child.bins.length);
				int b = 0 + (int) (Math.random() * child.bins[0].length);
				int c = 0 + (int) (Math.random() * child.bins.length);
				int d = 0 + (int) (Math.random() * child.bins[0].length);
				int temp = child.bins[a][b];
				// swap two numbers
				child.bins[a][b] = child.bins[c][d];
				child.bins[c][d] = temp;

				newPopulation.add(child);

				// large population sizes may make this for loop run a long time
				if (System.currentTimeMillis() > endTime){
					break;
				}
			}

			// new population is the preserved list and the new population which
			// consists of the children
			population = new ArrayList<Bins>();
			population.addAll(elites);
			population.addAll(newPopulation);
			sortByFitness(population);
		}

		// first element should be best arrangement of bins
		return population.get(0);
	}

	public static Bins reproduce(Bins x, Bins y) {
		Bins child = new Bins(x.bins);
		int count = 0;

		ArrayList<Integer> newArray1 = new ArrayList<Integer>();
		ArrayList<Integer> childArray1 = new ArrayList<Integer>();
		
		//take best rows from x and y and pass to child
		int row1x = Bins.bin1(x.bins[0]);
		int row2x = Bins.bin2(x.bins[1]);
		int row3x = Bins.bin3(x.bins[2]);
		int row1y = Bins.bin1(y.bins[0]);
		int row2y = Bins.bin2(y.bins[1]);
		int row3y = Bins.bin3(y.bins[2]);
		for(int col = 0; col < x.bins[0].length; col++){
			if(row1x > row1y){
				childArray1.add(x.bins[0][col]);
			}
			else
				childArray1.add(y.bins[0][col]);
			
			newArray1.add(x.bins[0][col]);
		}
		for(int col = 0; col < x.bins[1].length; col++){
			if(row2x > row2y){
				childArray1.add(x.bins[1][col]);
			}
			else
				childArray1.add(y.bins[1][col]);
			
			newArray1.add(x.bins[1][col]);
		}
		for(int col = 0; col < x.bins[2].length; col++){
			if(row3x > row3y){
				childArray1.add(x.bins[2][col]);
			}
			else
				childArray1.add(y.bins[2][col]);
			
			newArray1.add(x.bins[2][col]);
		}
		
		// check if child array has too many of a certain numbers, mark repeats
		// with -10
		for (int n = 0; n < childArray1.size(); n++) {
			Integer cur = new Integer(childArray1.get(n));
			//Integer cur2 = new Integer(childArray2.get(n));
			if (newArray1.contains(cur)) {
				newArray1.remove(cur);
			} else {
				childArray1.set(n, new Integer(-10));
			}
		}

		// loop through child array add elements and replace -10
		count = 0;
		for (int i = 0; i < child.bins.length; i++) {
			for (int j = 0; j < child.bins[0].length; j++) {
				int c1 = childArray1.get(count);
				if(c1 != -10)
					child.bins[i][j] = c1;
				else
					child.bins[i][j] = newArray1.remove(0);
				count++;
			}
		}

		return child;
	}

	/**
	 * Selects a random bin from the given array list of Bins. The array list is
	 * assumed to be ordered from highest to lowest total scores. Bins with
	 * higher total scores will have a higher chance of being selected.
	 * 
	 * @param population
	 * @return
	 */
	public static Bins randomSelection(ArrayList<Bins> population) {
		return population.get((int) (Math.random()*population.size() * 0.1));
	/*
		Bins randBin = new Bins();

		// found this weighted biased random number generation online
		// first sum up all scores
		int totalWeight = 0;
		for (Bins b : population) {
			int score = b.calBins(b.bins) + 10000;
			totalWeight += score;
		}
		// get a random number between 0 and totalWeight
		int randomNum = (int) (Math.random() * totalWeight);

		// find the corresponding bin
		int weightSum = 0;
		Bins cur;
		for (int i = 0; i < population.size(); i++) {
			cur = population.get(i);
			weightSum += cur.calBins(cur.bins) + 10000;

			if (randomNum <= weightSum) {
				randBin = cur;
				break;
			}
		}

		return randBin;*/
	}

	protected static void sortByFitness(ArrayList<Bins> pop) {
		Collections.sort(pop, new Comparator<Bins>() {
			@Override
			public int compare(Bins b1, Bins b2) {
				return b2.calBins(b2.bins) - b1.calBins(b1.bins);
			}
		});
	}
}
