package maps;

import java.util.HashMap;
import java.util.Map;

/**
 * Practice in using maps.
 * 
 * @author Nate Chenette
 *
 */

public class NGramCounting {

	/**
	 * Given an input text and a length n, the method should produce a Map from
	 * n-grams of the text (i.e., length-n substrings) to counts, where n-gram S is
	 * mapped to count C if S shows up C times among substrings of the text.
	 * 
	 * This method would be useful in frequency-based cryptanalysis of the classic
	 * substitution cipher.
	 * 
	 * @param text
	 * @param n,   the length of the n-grams to count
	 * @return
	 */
	 static Map<String, Integer> nGramCounter(String text, int n) {
		HashMap<String, Integer> map = new HashMap<>();
		for (int a = 0; a <= text.length() - n; a++) {
			String finding = text.substring(a, a + n);
			if (!map.containsKey(finding)) {
				map.put(finding, 1);
			} else {
				map.put(finding, map.get(finding) + 1);
			}
		}
		return map;
	}

}
