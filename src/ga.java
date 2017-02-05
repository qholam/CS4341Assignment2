import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class ga {
	public static Bins gaSearch(ArrayList<Bins> population, int runTime, double elitism) {
		long endTime = System.currentTimeMillis() + runTime;
		while (System.currentTimeMillis() < endTime) {
			// sort population by fitness (sorts from highest score to lowest
			// score)
			sortByFitness(population);
			
			// choose elites to preserve top 20% for now
			ArrayList<Bins> elites = new ArrayList<Bins>(population.subList(0, (int) (elitism * population.size())));
			ArrayList<Bins> newPopulation = new ArrayList<Bins>();

			for (int i = 0; i < population.size(); i++) {
				//select two bins out random
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
				// swap to numbers
				child.bins[a][b] = child.bins[c][d];
				child.bins[c][d] = temp;
				
				newPopulation.add(child);
				
				//large population sizes may make this for loop run a long time
				if(System.currentTimeMillis() > endTime)
					break;
			}

			// new population is the preserved list and the new population which
			// consists of the children
			population = new ArrayList<Bins>();
			population.addAll(elites);
			population.addAll(newPopulation);
		}

		// first element should be best arrangement of bins
		return population.get(0);
	}

	public static Bins reproduce(Bins x, Bins y) {
		Bins child = new Bins(x.bins);
		
		//randomly take rows from x and y
		for (int j = 0; j < x.bins.length; j++){
			int randNum = (int) (Math.random() * 2 + 1);
			
			if(randNum%2==0)
				child.bins[j] = x.bins[j].clone();
			else
				child.bins[j] = y.bins[j].clone();
		}
		
		//TODO: check if child is valid, if not fix it
		
		return child;
	}

	/**
	 * Selects a random bin from the given array list of Bins. The array list
	 * is assumed to be ordered from highest to lowest total scores. Bins with
	 * higher total scores will have a higher chance of being selected. 
	 * @param population
	 * @return
	 */
	public static Bins randomSelection(ArrayList<Bins> population) {
		Bins randBin = new Bins();
		
		//found this weighted biased random number generation online
		//first sum up all scores
		int totalWeight = 0;
		for(Bins b: population){
			int score = b.calBins(b.bins) + 10000;
			totalWeight += score;
		}
		//get a random number between 0 and totalWeight
		int randomNum = (int) (Math.random() * totalWeight);
		
		//find the corresponding bin
		int weightSum = 0;
		Bins cur;
		for(int i = 0; i < population.size(); i++){
			cur = population.get(i);
			weightSum += cur.calBins(cur.bins)+ 10000;
			
			if(randomNum <= weightSum){
				randBin = cur;
				break;
			}
		}
		
		return randBin;
	}

	protected static void sortByFitness(ArrayList<Bins> pop) {
		Collections.sort(pop, new Comparator<Bins>() {
	        @Override
	        public int compare(Bins b1, Bins b2)
	        {
	        	return b2.calBins(b2.bins) - b1.calBins(b1.bins);
	        }
	    });
	}
}
