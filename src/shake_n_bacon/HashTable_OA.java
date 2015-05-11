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
	int numOfElements;
	int sizeMult;
	
	public HashTable_OA(Comparator<String> c, Hasher h) {
		stringComp = c;
		stringHash = h;
		stringTable = new DataCount[86311]; 
		numOfElements = 0;
		sizeMult = 0;
	}

	@Override
	public void incCount(String data) {
		int[] primeNum = new int[]{164233, 331523};
		
		if(numOfElements / stringTable.length > 0.5) {
			DataCount[] temp = new DataCount[primeNum[sizeMult]];
			sizeMult++;
			for(int i = 0; i < stringTable.length; i++) {
				insert(data, temp);
			}
		} 
		insert(data, stringTable);

		numOfElements++; 
	}
	
	private void insert(String data, DataCount[] arr) {
		int index = stringHash.hash(data) % arr.length;
		System.out.println(arr[index] == null);
		if(arr[index] == null) {
			arr[index] = new DataCount(data, 1);
			System.out.println("inserted new");
		} else if(stringComp.compare(arr[index].data, data) == 0) {
			arr[index].count++;
			System.out.println("same word");
		} else {
			int i = index;
			while(arr[i] == null) {
				i = (i + 1) % arr.length;
			}
			arr[i] = new DataCount(data, 1);
			System.out.println("probing");
		}
		
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getCount(String data) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public SimpleIterator getIterator() {
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