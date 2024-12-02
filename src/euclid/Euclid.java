package euclid;

public class Euclid {
	/**
	 * Implementation requirement: must do recursively, as given in the spec.
	 * 
	 * @param a First integer
	 * @param b Second integer
	 * @return The greatest common divisor of a and b using Euclid's recursive
	 *         algorithm.
	 */
	/*
	 * My Way
	 */
	public static long gcd(long a, long b) {
		long holder = 0;
		if (a < b) {
			holder = a;
			a = b;
			b = holder;
		}
		return helper(a, b);
	}

	public static long helper(long a, long b) {
		if (a / b == 0) {
			if (b % a == 0) {
				return a;
			} else {
				return 1;
			}
		}
		return helper(a % b, b);
	}

	/*
	 * Better way learn from the internet after solving this way Comment-> they
	 * solve in a really smart way by switching the position between two number and
	 * get the remainder
	 */
	// public static long gcd(long a, long b) {
	// if (b==0){
	// return a;
	// }
	// return gcd(b, a%b);
	// }

}
