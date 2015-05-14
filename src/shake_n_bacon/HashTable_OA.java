package shake_n_bacon;

import java.util.NoSuchElementException;

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
 *  A hash table that stores words and their counts; implemented using
 *  open addressing and linear probing
 */
public class HashTable_OA extends DataCounter {
	
	// used to compare to strings
	private Comparator<String> stringComp;
	
	// hash function that takes in a string and returns a hash value
	private Hasher stringHash;
	
	// stores words and their associated counts
	private DataCount[] stringTable;
	
	// size multiplier for increasing hash table size
	private int sizeMult;
	
	// number of unique words in the hash table
	private int numOfUnique;
	
	
	// accepts a comparator object and hash function then
	// initiates a new hash table with an initial table length of 5347
	public HashTable_OA(Comparator<String> c, Hasher h) {
		stringComp = c;
		stringHash = h;
		stringTable = new DataCount[5347]; //5347
		sizeMult = 0;
		numOfUnique = 0;
	}

	// increments the count for the word that is passed in 
	// if the load factor is greater that 0.5, the table is resized to 
	// a new prime number value and all words are rehashed and inserted into the
	// new table
	public void incCount(String data) {
		if (numOfUnique / stringTable.length > 0.5) {
			int[] primeNum = new int[]{10159, 20173, 40583, 86311, 164233, 331523};
			if (sizeMult == primeNum.length) {
				System.out.println("Maximum size reached");
				System.exit(0);
			}
			
			DataCount[] temp = new DataCount[primeNum[sizeMult]];
			sizeMult++;
			numOfUnique = 0;
			for(int i = 0; i < stringTable.length; i++) {
				if(stringTable[i] != null) {
					DataCount dCount = stringTable[i];
					insert(dCount, temp);
				
				}
			}
			stringTable = temp;
		}
		insert(new DataCount(data, 1), stringTable);
	}
	
	// inserts a given DataCount object into an array and
	// handles collision by using linear probing
	private void insert(DataCount dCount, DataCount[] arr) {
		int index = stringHash.hash(dCount.data) % arr.length;
		boolean added = false; 
	

		if(arr[index] == null) {
			arr[index] = dCount;
			numOfUnique++;
		} else if(stringComp.compare(arr[index].data, dCount.data) == 0) {

			arr[index].count++;
		} else {
			int i = index;
			while(arr[i] != null) {
				if(stringComp.compare(arr[i].data, dCount.data) == 0) {
					arr[i].count++;
					added = true;
					break;
				} else {
					i = (i + 1) % arr.length;
				}
			}
			if(!added) {
				arr[i] = dCount;
				numOfUnique++;
			}
		}
	}
	
	
	// returns the number of unique words in the hash table
	public int getSize() {
		return numOfUnique;
	}

	// returns the count of the given word
	public int getCount(String data) {
		int index = stringHash.hash(data) % stringTable.length;
		if (stringTable[index] == null) {
			return 0;
		} else if(stringComp.compare(stringTable[index].data, data) == 0) {
			return stringTable[index].count;
		} else {
			int i = index;
			while(stringTable[i] != null) {
				if(stringComp.compare(stringTable[i].data, data) == 0) {
					return stringTable[i].count;
				} else {
					i = (i + 1) % stringTable.length;
				}
			}
			return 0;
		}
	}

	// returns a SimpleIterator that can iterate over the the hash table
	public SimpleIterator getIterator() {
		return new SimpleIterator(){
			// current index of the hash table that the iterator is on
			int startIndex = 0;
			
			// current number of elements iterator has iterated over 
			int elementsOut = 0;

			// returns the next DataCount object in the table that is not null
			public DataCount next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				}
				while(stringTable[startIndex] == null) {
					startIndex++;
				}
				DataCount temp = stringTable[startIndex];
				startIndex++;
				elementsOut++;
				return temp;
			}

			// returns true if there are more DataCount objects left to be iterated over
			public boolean hasNext() {
				return (startIndex < stringTable.length) && elementsOut < numOfUnique;
			}
		};
	}
}