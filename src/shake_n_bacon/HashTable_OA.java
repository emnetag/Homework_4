package shake_n_bacon;

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
	Comparator<String> stringComp;
	Hasher stringHash;
	DataCount[] stringTable;
	int sizeMult;
	int numOfUnique;
	
	public HashTable_OA(Comparator<String> c, Hasher h) {
		stringComp = c;
		stringHash = h;
		stringTable = new DataCount[86311]; 
		sizeMult = 0;
		numOfUnique = 0;
	}

	@Override
	public void incCount(String data) {
		if (numOfUnique / stringTable.length > 0.5) {
			int[] primeNum = new int[]{164233, 331523};
			
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
				    int index = insert(dCount.data, temp);
					temp[index].count = dCount.count;
				}
			}
			stringTable = temp;
		}
		insert(data, stringTable);
	}
	
	private int insert(String data, DataCount[] arr) {
		int index = stringHash.hash(data) % arr.length;
	
		if(arr[index] == null) {
			arr[index] = new DataCount(data, 1);
			numOfUnique++;
//			System.out.println("insert new");
		} else if(stringComp.compare(arr[index].data, data) == 0) {
			arr[index].count++;
//			System.out.println("counter plus");
		} else {
			int i = index;
			while(arr[i] != null) {
				i = (i + 1) % arr.length;
			}
			arr[i] = new DataCount(data, 1);
			numOfUnique++;
//			System.out.println("probing");
		}
		System.out.println(data);
		System.out.println(arr[index].count);
		return index;
	}

	@Override
	public int getSize() {
		System.out.println(numOfUnique);
		return numOfUnique;
	}

	@Override
	public int getCount(String data) {
		int index = stringHash.hash(data) % stringTable.length;
		if (stringTable[index] == null) {
			return 0;
		} else { 
			return stringTable[index].count;
		} 
	}

	@Override
	public SimpleIterator getIterator() {
		//im still a bit not sure on what should I do here.
		return new SimpleIterator(){

			@Override
			public DataCount next() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}
			
		};
	
	}

}