import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Genetic{
	int[] nums;
	//ArrayList<Populations> pop;
	int k = 100; //the initial population size
	ArrayList<Bins> popContain; 
	int elite;
	
  public Genetic (int[] nums, int k, int elite){
    this.nums = nums;
    this.k = k;
    this.elite = elite;
    popContain = generatePop(this.nums, this.k);   
  }
  
  protected ArrayList<Bins> sortByFitness(ArrayList<Bins> pop){
		ArrayList<Bins> eliteSet = new ArrayList<Bins>();
		ArrayList<Bins> toPop = new ArrayList<Bins>();
		ArrayList<Bins> temp = new ArrayList<Bins>();
		int tempScore = 0;
		
		for (int i = 0; i < pop.size(); i++){
			Bins thisBin = pop.get(i);
			tempScore = scoreBinsGen(thisBin);
			thisBin.score = tempScore;
			
			if (temp.isEmpty()){
				temp.add(thisBin);
			}
			else {
				for (int j = 0; j < temp.size(); j++){
					Bins jBin = temp.get(j);
					if (thisBin.score > jBin.score ){
						temp.add(j, thisBin);
					}
					else if (thisBin.score <= jBin.score){
						continue;
					}
				}
			}
		}
		eliteSet = (ArrayList<Bins>) temp.subList(0, elite); // need to add a variable called elite which decides how many to keep;
		toPop = (ArrayList<Bins>) temp.subList(elite, temp.size());
		newPop(toPop, eliteSet);
		System.out.println("high score so far = " + temp.get(0).score);
		return eliteSet;
  }
  
  protected void newPop(ArrayList<Bins> pop, ArrayList<Bins> elites){
	  int [] binA1 = null;
	  int [] binA2 = null;
	  int [] binA3 = null;
	  int [] binB1 = null;
	  int [] binB2 = null;
	  int [] binB3 = null;
	  //shuffle things around in pairs
	  for (int i = 0; i < pop.size(); i+=2){
			Bins binA = pop.get(i);
			Bins binB = pop.get(i+1);
			Random rand = new Random();
			rand.setSeed(System.currentTimeMillis());
			for (int j = 0; j < binA.cols; j++){
				binA1[j] = binA.bins[1][j];
				binA2[j] = binA.bins[2][j];
				binA3[j] = binA.bins[3][j];
				binB1[j] = binB.bins[1][j];
				binB2[j] = binB.bins[2][j];
				binB3[j] = binB.bins[3][j];
			}
			int [][] newA = {binA1, binB2, binA3};
			int [][] newB = {binB2, binB1, binB3};
			//for mutation we make sure there are the right number of numbers
			ArrayList<Integer> binASort = null;
			ArrayList<Integer> binBSort = null;
			ArrayList<Integer> binAOrig = null;
			int temp;

			for (i = 0; i < 3; i++){
				for (int j = 0; j < binA.cols; j++){
					binASort.add(newA [i][j]);
					binBSort.add(newB [i][j]);
					binAOrig.add(binA.bins [i][j]);
				}
			}
			ArrayList<Integer>finalA = mutate(binASort, binAOrig);
			ArrayList<Integer>finalB = mutate(binBSort, binAOrig);
			Integer[] finalANums = convertInt(finalA);
			int[] finalAInts = null;
			for (int j = 0; j < finalANums.length; j++){
				finalAInts[j] = finalANums[j].intValue();
			}
			pop.add(new Bins(finalAInts));
			Integer[] finalBNums = convertInt(finalB);
			int[] finalBInts = null;
			for (int j = 0; j < finalBNums.length; j++){
				finalBInts[j] = finalBNums[j].intValue();
			}
			pop.add(new Bins(finalBInts));
			
	  }
	  pop.addAll(elites);
//	return pop;  
  }
  
  public static Integer[] convertInt(ArrayList<Integer> ints){
	  Integer[] ret = new Integer[ints.size()];
	  for(int i = 0; i < ret.length; i++){
		  ret[i] = ints.get(i).intValue();
	  }
	  return ret;
  }
  
  protected ArrayList<Integer> mutate(ArrayList<Integer> a, ArrayList<Integer> sort){
		ArrayList<Integer> InSort = null;
		ArrayList<Integer> InA = null;
		ArrayList<Integer> diff = null;
		for (int i = 0; i < sort.size(); i++){
			InSort.add(i+9, (sort.get(i) + InSort.get(i+9)));
			}
		for (int i = 0; i < a.size(); i++){
			InA.add(i+9, (a.get(i) + InA.get(i+9)));
			}
		for (int i = 0; i < InA.size(); i++ ){
			diff.add(InSort.get(i) - InA.get(i));
			}
		for (int i = 0; i < diff.size(); i++ ){
			if (diff.get(i) < 0){
				for (int j = 0; j < Math.abs(diff.get(i)); j++){
					a.remove(a.indexOf(i));
				}
			}
			else if (diff.get(i) > 0){
				for (int j = 0; j < Math.abs(diff.get(i)); j++){
					a.add(diff.get(i));
			}
		}
	}
		return a;
  }
  
  protected int scoreBinsGen (Bins bin){
	int [] scores;
	ArrayList<Bins> fitness;
	int size = this.popContain.size() / 3;
	//for each bin in population
	int [] temp1 = new int[size];
	int [] temp2= new int[size];
	int [] temp3= new int[size];
	for (int i = 0; i < size; i++){
		 temp1[i] = bin.bins[1][i]; // this isn't actually right
	}
	int score = Bins.bin1(temp1);
	for (int i = size; i < size*2; i++){
		 temp2[i] = bin.bins[2][i];
	}
	score += Bins.bin2(temp2);
	for (int i = size*2; i < size*3; i++){
		 temp3[i] = bin.bins[3][i];//neither is this
	}
	score += Bins.bin3(temp3);
	return score;
  }
	
//	ArrayList<Bins> eliteSet = new ArrayList<Bins>();
	
  
  protected ArrayList<Bins> generatePop(int[] nums, int k){
      ArrayList<Bins> binList = new ArrayList<Bins>();
      for (int i = 0; i < k; i ++){
        binList.add(new Bins(nums));
        shuffleArray(nums);
      }
      return binList;
  }
  
  //Shuffle the array around to create more options for the algorithm
  private static void shuffleArray(int[] array)
  {
      int index;
      Random random = new Random();
      for (int i = array.length - 1; i > 0; i--)
      {
          index = random.nextInt(i + 1);
          if (index != i)
          {
              array[index] ^= array[i];
              array[i] ^= array[index];
              array[index] ^= array[i];
          }
      }
  }

}