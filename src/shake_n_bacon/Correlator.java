package shake_n_bacon;

import java.io.IOException;

import providedCode.*;

/**
 * @author Emnet Gossaye
 * @author Vincent Joe
 * @UWNetID emnetg
 * @UWNetID jonanv
 * @studentID 1221300
 * @studentID 1470087
 * @email emnetg@uw.edu
 * @email jonanv@uw.edu
 * 
 * Correlator.java computes a metric to determine the similarities in word
 * frequencies between two text files. The variance is calculated by taking 
 * the sum of the squares of the differences between the normalized frequencies
 * of words that occur in both texts.
 * 
 */
public class Correlator {

	public static void main(String[] args) {

		double variance = 0.0;
		
		String firstArg = args[0].toLowerCase();
		
		DataCounter counter1 = null;
		DataCounter counter2 = null;
		
		if (firstArg.equals("-s")) {
			counter1 = new HashTable_SC(new StringComparator(),
						new StringHasher());
			counter2 = new HashTable_SC(new StringComparator(),
						new StringHasher());
		} else if (firstArg.equals("-o")) {
			counter1 = new HashTable_OA(new StringComparator(),
						new StringHasher());
			counter2 = new HashTable_OA(new StringComparator(),
						new StringHasher());
		} else {
			usage();
		}
		
		int textLength1 = countWords(args[1], counter1);
		int textLength2 = countWords(args[2], counter2);

		SimpleIterator itr = counter1.getIterator();
		
		while (itr.hasNext()) {
			DataCount dCount = itr.next();
			
			int count2 = counter2.getCount(dCount.data);

			double freq1 = ((double)dCount.count) / textLength1;
			
			double freq2 = ((double) count2) / textLength2;
			
			if (checkFreq(freq1) && checkFreq(freq2)) {
				variance += (freq2 - freq1) * (freq2 - freq1);
			}
		}
		System.out.println(variance);
	}
	
	
	// prints a message indicating the proper syntax of using this class
	public static void usage() {
		System.err
				.println("Usage: [-s | -o] <filename1> <filename2>");
		System.err.println("-s for hashtable with separate chaining");
		System.err.println("-o for hashtable with open addressing");
		System.exit(1);
	}
	
	// takes in a file name and a DataCounter object
	// reads words from the string file and inserts them into the DataCounter object
	// and returns the total number of words that occur in the file
	// if the specified filepath is invalid, an error will be printed
	public static int countWords(String file, DataCounter counter) {
		try {
			FileWordReader reader = new FileWordReader(file);
			String word = reader.nextWord();
			int totalWords = 0;
			while (word != null) {
				totalWords++;
				counter.incCount(word);
				word = reader.nextWord();
			}
			return totalWords;
		} catch (IOException e) {
			System.err.println("Error processing " + file + " " + e);
			System.exit(1);
		}
		return 0;
	}
	
	// takes in a double and checks if the value is not zero 
	// and below 1% and above 0.01%
	public static boolean checkFreq(double frequency) {
		return frequency >= 0.0001 && 0.01 >= frequency && frequency != 0;
	}
}
