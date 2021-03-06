package writeupExperiment;

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
 *        TODO: Replace this comment with your own as appropriate.
 * 
 *        1. You may implement HashTable with open addressing discussed in
 *        class; You can choose one of those three: linear probing, quadratic
 *        probing or double hashing. The only restriction is that it should not
 *        restrict the size of the input domain (i.e., it must accept any key)
 *        or the number of inputs (i.e., it must grow as necessary).
 * 
 *        2. Your HashTable should rehash as appropriate (use load factor as
 *        shown in the class).
 * 
 *        3. To use your HashTable for WordCount, you will need to be able to
 *        hash strings. Implement your own hashing strategy using charAt and
 *        length. Do NOT use Java's hashCode method.
 * 
 *        4. HashTable should be able to grow at least up to 200,000. We are not
 *        going to test input size over 200,000 so you can stop resizing there
 *        (of course, you can make it grow even larger but it is not necessary).
 * 
 *        5. We suggest you to hard code the prime numbers. You can use this
 *        list: http://primes.utm.edu/lists/small/100000.txt NOTE: Make sure you
 *        only hard code the prime numbers that are going to be used. Do NOT
 *        copy the whole list!
 * 
 *        TODO: Develop appropriate tests for your HashTable.
 */
public class HashTable_OA extends DataCounter {
	private Comparator<String> stringComp;
	private Hasher stringHash;
	private DataCount[] stringTable;
	private int sizeMult;
	private int numOfUnique;
	
	public HashTable_OA(Comparator<String> c, Hasher h) {
		stringComp = c;
		stringHash = h;
		stringTable = new DataCount[5347]; //5347
		sizeMult = 0;
		numOfUnique = 0;
	}

	@Override
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
	
	
	@Override
	public int getSize() {
		return numOfUnique;
	}

	@Override
	
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

	@Override
	public SimpleIterator getIterator() {
		return new SimpleIterator(){
			int startIndex = 0;
			int elementsOut = 0;
			@Override
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

			@Override
			public boolean hasNext() {
				return (startIndex < stringTable.length) && elementsOut < numOfUnique;
			}
		};
	}
}