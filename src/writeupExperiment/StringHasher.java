package writeupExperiment;

import providedCode.Hasher;

/**
 * @author <name>
 * @UWNetID <uw net id>
 * @studentID <id number>
 * @email <email address>
 */
public class StringHasher implements Hasher {

	public static final int INITAL_HASH_VAL = 7;
	public static final int PRIME_HASH_NUM = 31;

	/**
	 * TODO Replace this comment with your own as appropriate.
	 */
	@Override
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
