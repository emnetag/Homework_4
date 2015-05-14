package shake_n_bacon;

import providedCode.Hasher;

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
 * StringHasher.java is a hash function that takes in a string 
 * and returns a hash value. 
 *
 */
public class StringHasher implements Hasher {

	public static final int INITAL_HASH_VAL = 7;
	public static final int PRIME_HASH_NUM = 31;

	// takes in a string and returns a hash value by 
	// keeping a running total of PRIME_HASH_NUM multiplied
	// by the hash value and adding the ASCII value of each
	// character in the string
	public int hash(String str) {
		int hash = INITAL_HASH_VAL;	
		for(int i = 0; i < str.length(); i++) {
			hash += hash * PRIME_HASH_NUM + str.charAt(i);
		}
		if(hash < 0) {
			hash = Math.abs(hash);
		}
		return hash;
	}
}
