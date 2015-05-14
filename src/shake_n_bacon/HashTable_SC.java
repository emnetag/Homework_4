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
 *  A hash table that stores words and their counts. This implementation
 *  handles collisions using separate chaining.
 * 
 */
public class HashTable_SC extends DataCounter {
	// compares two strings
	private Comparator<String> stringComp;
	
	// hash function that returns the hash value of the given string
	private Hasher stringHash;
	
	// array to store each word and its count
	private NodeObj[] stringTable; 
	
	// size multiplier for increasing hash table size
	private int sizeMult;
	
	// number of unique words in the hash table
	private int numOfUnique;
	
	// constructs a new table of node objects with an initial size of 86311
	// and a size multiplier of 0;
	public HashTable_SC(Comparator<String> c, Hasher h) {
		stringComp = c;
		stringHash = h;
		stringTable = new NodeObj[86311]; 
		numOfUnique = 0;
		sizeMult = 0;
	}

	// if the word already exists in the table, increment the count. Otherwise
	// creates a new node object and inserts it into the table. If the load factor
	// is greater than 1.5, the new table size will be the value of the primeNum array
	// at index of sizeMult. all node objects in the old array will be rehashed into the new
	// table.
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
	
	// inserts a node object into the hash table. If the word associated with the object
	// already exists the count is incremented. if two words hash to the same index and that word doesn't
	// exist a new node is added at the end of the list at that index.
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
	
	// returns the number of unique words in the hash table
	public int getSize() {
		return numOfUnique;
	}

	// returns the count of the given word in the table. if the word is not
	// found, 0 is returned. 
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

	// returns a SimpleIterator that can iterate over the hash table
	public SimpleIterator getIterator() {
		return new SimpleIterator() {
			// number of elements iterated over by the iterator
			int elementsOut = 0;
			
			// the current index of the table that the iterator is on
			int index = 0;
			NodeObj pointer = stringTable[0]; 

			// returns the next dataCount object in the hash table
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

			// returns true if there are more DataCount objects left to be iterated over
			public boolean hasNext() {
				return (index < stringTable.length) && (elementsOut < numOfUnique);
			}
		};
	}
	
	// inner class for node object to handle collisions by using separate chaining
	private class NodeObj {
		public NodeObj next; // reference to the next node in the chain
		public DataCount dataCount; // DataCount object 
		
		// constructs an empty node 
		public NodeObj() {
			this.dataCount = null;
			this.next = null;
		}
		
		// constructs a node object with a given word and a count of 1
		public NodeObj(String data) {
			this.dataCount = new DataCount(data, 1);
			this.next = null;
		}
	}
	

}
