package shake_n_bacon;

import providedCode.Hasher;

/**
 * @author <name>
 * @UWNetID <uw net id>
 * @studentID <id number>
 * @email <email address>
 */
public class StringHasher implements Hasher {

	/**
	 * TODO Replace this comment with your own as appropriate.
	 */
	@Override
	public int hash(String str) {
		int hash = 7;
		int primeNum = 31;
		for(int i = 0; i < str.length(); i++) {
			hash = hash*primeNum + str.charAt(i);
		}
		return hash;
	}
}
