package writeupExperiment;

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
 *        TODO: REPLACE this comment with your own as appropriate.
 * 
 *        This should be done using a *SINGLE* iterator. This means only 1
 *        iterator being used in Correlator.java, *NOT* 1 iterator per
 *        DataCounter (You should call dataCounter.getIterator() just once in
 *        Correlator.java). Hint: Take advantage of DataCounter's method.
 * 
 *        Although you can share argument processing code with WordCount, it
 *        will be easier to copy & paste it from WordCount and modify it here -
 *        it is up to you. Since WordCount does not have states, making private
 *        method public to share with Correlator is OK. In general, you are not
 *        forbidden to make private method public, just make sure it does not
 *        violate style guidelines.
 * 
 *        Make sure WordCount and Correlator do not print anything other than
 *        what they are supposed to print (e.g. do not print timing info, input
 *        size). To avoid this, copy these files into package writeupExperiment
 *        and change it there as needed for your write-up experiments.
 */
public class Correlator {

	public static void main(String[] args) {
		// TODO: Compute this variance
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
		//System.out.println(textLength1);
		int textLength2 = countWords(args[2], counter2);
		//System.out.println(textLength2);


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
		
		// IMPORTANT: Do not change printing format. Just print the double.
		System.out.println(variance);
	}
	
	public static void usage() {
		System.err
				.println("Usage: [-s | -o] <filename1> <filename2>");
		System.err.println("-s for hashtable with separate chaining");
		System.err.println("-o for hashtable with open addressing");
		System.exit(1);
	}
	
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
	
	public static boolean checkFreq(double frequency) {
		return frequency >= 0.0001 && 0.01 >= frequency && frequency != 0;
	}
}
