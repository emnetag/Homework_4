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
 *        TODO: Replace this comment with your own as appropriate.
 * 
 *        1. You may implement HashTable with separate chaining discussed in
 *        class; the only restriction is that it should not restrict the size of
 *        the input domain (i.e., it must accept any key) or the number of
 *        inputs (i.e., it must grow as necessary).
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
public class HashTable_SC extends DataCounter {
	private Comparator<String> stringComp;
	private Hasher stringHash;
	private NodeObj[] stringTable; 
	private int sizeMult;
	private int numOfUnique;
	
	public HashTable_SC(Comparator<String> c, Hasher h) {
		stringComp = c;
		stringHash = h;
		stringTable = new NodeObj[5347]; // 5347
		numOfUnique = 0;
		sizeMult = 0;
	}

	@Override
	public void incCount(String data) {
		if (numOfUnique / stringTable.length > 1.5) {
			System.out.println("resize");
			int[] primeNum = new int[]{10159, 20173, 40583, 86311, 164233, 331523};
			if (sizeMult == primeNum.length) {
				System.out.println("Maximum size reached");
				System.exit(0);
			}
			
			NodeObj[] arrTemp = new NodeObj[primeNum[sizeMult]];
			sizeMult++;
			numOfUnique = 0;
			for (int i = 0; i < stringTable.length; i++) {
				if (stringTable[i] != null) {
					NodeObj curr = stringTable[i];
					while (curr != null) {
						insert(curr, arrTemp);
						curr = curr.next;
					}
				}		
			}
			stringTable = arrTemp;
		}
		insert(new NodeObj(data), stringTable);
	}
	
	private void insert(NodeObj aNode, NodeObj[] arr) {
		int index = stringHash.hash(aNode.dataCount.data) % arr.length;

		boolean added = false;
		
		if (arr[index] == null) {
			arr[index] = aNode;
			numOfUnique++;
		} else {
			NodeObj curr = arr[index];
			while (curr.next != null) {
				if (stringComp.compare(curr.dataCount.data, aNode.dataCount.data) == 0) {
					curr.dataCount.count++;
					added = true;
					break;
				}
				curr = curr.next;
			}
			if (stringComp.compare(curr.dataCount.data, aNode.dataCount.data) == 0 && !added) {
				curr.dataCount.count++;
			} else if (stringComp.compare(curr.dataCount.data, aNode.dataCount.data) != 0 && !added){
				curr.next = aNode;
				numOfUnique++;
			}
		}
	}
	
	
//	private int insert(String data, NodeObj[] arr) {
//		int index = stringHash.hash(data) % arr.length;
//		
//		if (arr[index] == null) {
//			arr[index] = new NodeObj(data);
//			numOfUnique++;
//		} else {
//			NodeObj curr = arr[index];
//			while (curr != null) {
//				if (stringComp.compare(curr.dataCount.data, data) == 0) {
//					curr.dataCount.count++;
//					return index;
//				}
//				curr = curr.next;
//			}
//			
//			NodeObj temp = new NodeObj(data);
//			temp.next = arr[index];
//			arr[index] = temp;
//			numOfUnique++;
//		}
//		return index;
//	}

	@Override
	public int getSize() {
		return numOfUnique;
	}

	@Override
	public int getCount(String data) {
		int index = stringHash.hash(data) % stringTable.length;
		
		if (stringTable[index] == null) {
			return 0;
		} else {
			NodeObj curr = stringTable[index];
			while (curr != null) {
				if (stringComp.compare(curr.dataCount.data, data) == 0) {
					return curr.dataCount.count;
				}
				curr = curr.next;
			}
			return 0;
		}
	}

	@Override
	public SimpleIterator getIterator() {
		return new SimpleIterator() {
			int elementsOut = 0;
			int index = 0;
			NodeObj pointer = stringTable[0]; 

			@Override
			public DataCount next() {
				if (!this.hasNext()) {
					throw new NoSuchElementException();
				}
				if(pointer == null) {
					index++;

					while(stringTable[index] == null) {
						index++;
					}
					pointer = stringTable[index];
				}
				NodeObj temp = pointer;
				pointer = pointer.next;
				elementsOut++;

				return temp.dataCount;
			}

			@Override
			public boolean hasNext() {
				return (index < stringTable.length) && (elementsOut < numOfUnique);
			}
			
		};
	}
	
	
	public class NodeObj {
		public NodeObj next;
		public DataCount dataCount;
		
		public NodeObj() {
			this.dataCount = null;
			this.next = null;
		}
		
		public NodeObj(String data) {
			this.dataCount = new DataCount(data, 1);
			this.next = null;
		}
	}
	

}
