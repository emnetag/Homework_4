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
 *  Compares two strings to see if they are exactly the same, or if 
 *	one comes before the other alphabetically. 
 *
 */
public class StringComparator implements Comparator<String> {

	// returns a positive number if the second string comes alphabetically before the
	// first string, returns a negative number if the first string comes before the second
	// string, returns 0 if the two strings are exactly the same;
	public int compare(String s1, String s2) {		
		if (s1.length() == s2.length()) {
			for (int i = 0; i < s1.length(); i++) {
				if (s1.charAt(i) > s2.charAt(i)) {
					return 1;
				} else if(s1.charAt(i) < s2.charAt(i)) {
					return -1; 
				}
			}
			return 0;
		} else {
			return s1.length() - s2.length();
		}
	}
}
