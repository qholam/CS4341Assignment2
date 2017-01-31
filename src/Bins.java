import java.util.Arrays;
import java.util.Collections;

/*This is a few functions to break up assignment 2 in a way I believe will make things easier
 *So far there is a class named Bins with the bins and calculation functions
 *the search will be conducted in main? Maybe? 
 */

public class Bins {

	int[] num;
	int cols = 0;
	int[][] bins;

	// bins is a catch-all that can cover and develop the initial distribution
	// of all bins to be used in the searches
	public Bins(int[] nums) {
		this.num = nums;
		this.cols = nums.length / 3;
		bins = fillBins(num);
		/*
		 * Integer hashmap <number(key), number of those numbers(val) create
		 * hashmap now of number of each which can be changed in inValid
		 */
	}

	// parses the list into three bins and returns them as one 2D array
	protected int[][] fillBins(int[] nums) {
		Collections.shuffle(Arrays.asList(nums));
		int[] bin1 = new int[nums.length / 3];
		int[] bin2 = new int[nums.length / 3];
		int[] bin3 = new int[nums.length / 3];

		for (int i = 0; i < num.length; i++) {
			int j = 0, k = 0, m = 0;
			// if (isValid(num[j])){
			if ((!numIn(bin1, nums[i])) && (bin1.length < cols)) {
				bin1[j] = num[i];
				j++;
			} else if ((!numIn(bin2, nums[k])) && (bin2.length < cols)) {
				bin2[k] = num[i];
				k++;
			} else {
				bin3[m] = num[i];
				m++;
			}
			// }
			int[][] bins = { bin1, bin2, bin3 };
		}
		return bins;
	}

	/*
	 * Just checks if a number is in a bin
	 * 
	 * @todo add a way to check number of each number
	 */
	protected boolean numIn(int[] bin, int val) {
		for (int i = 0; i < bin.length; i++) {
			if (bin[i] == val)
				return true;
		}
		return false;
	}
	// !!!
	// protected boolean isValid (int val, hashmap){
	// numLeft = //value at hash key number;
	// if (numLeft > 0){
	// //val -- at key number;
	// return true;
	// }
	// else return false;
	// }

	protected int calBins(int[][] bins) {
		int score1 = bin1(bins[0]);
		int score2 = bin2(bins[1]);
		int score3 = bin3(bins[2]);
		return score1 + score2 + score3;
	}

	// --------------------------------------------------------
	// This section holds the scoring functions for each bin. Each should be
	// passed on row.
	// This one calculates the first bin's score
	public int bin1(int[] list) {
		int score = 0;
		for (int i = 0; i < list.length; i++) {
			score += list[i];
			i++;
			score -= list[i];
		}
		return score;
	}

	// calculate the second bin's score
	public int bin2(int[] list) throws IndexOutOfBoundsException {
		int eye = 0;
		int plus1 = 0;
		int score = 0;
		for (int i = 0; i < list.length; i++) {
			eye = list[i];
			try {
				plus1 = list[i + 1];
			} catch (Exception e) {

			}
			if (eye < plus1) {
				score += 3;
			} else if (eye == plus1) {
				score += 5;
			} else
				score -= 10;
		}
		return score;
	}

	public int bin3(int[] list) {
		int listlength = list.length;
		int[] firstarray = Arrays.copyOfRange(list, 0, listlength / 2);
		int[] secondarray = Arrays.copyOfRange(list, listlength / 2, listlength);
		int firsthalf_score = 0;
		int secondhalf_score = 0;
		if (listlength % 2 == 0) {
			firstarray = Arrays.copyOfRange(list, 0, listlength / 2);
			secondarray = Arrays.copyOfRange(list, listlength / 2, listlength);
		} else {
			firstarray = Arrays.copyOfRange(list, 0, listlength / 2);
			secondarray = Arrays.copyOfRange(list, listlength / 2 + 1, listlength);
		}
		for (int n : firstarray) {
			if (n < 0) {
				firsthalf_score -= 2;
			} else if (isPrime(n)) {
				firsthalf_score += 4;
			} else {
				firsthalf_score -= n;
			}
		}

		for (int n : secondarray) {
			if (n < 0) {
				secondhalf_score += 2;
			} else if (isPrime(n)) {
				secondhalf_score -= 4;
			} else {
				secondhalf_score += n;
			}
		}

		return firsthalf_score + secondhalf_score;
	}

	private boolean isPrime(int n) {
		if (n == 0 || n == 1) {
			return false;
		} else {
			if (n % 2 == 0)
				return false;
			for (int i = 3; i * i <= n; i += 2) {
				if (n % i == 0)
					return false;
			}
		}
		return true;
	}
}