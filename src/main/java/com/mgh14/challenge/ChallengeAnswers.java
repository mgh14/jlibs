package com.mgh14.challenge;

import java.util.Arrays;
import java.util.TreeSet;

/**
 * Created by mgh14 on 6/12/17.
 */
public class ChallengeAnswers {

    // due on 9 Jun 2017
    public static boolean isPermutation(Character[] original, Character[] possiblePermutation) {
        return (original.length == possiblePermutation.length) &&
                (new TreeSet<>(Arrays.asList(original)).equals(
                        new TreeSet<>(Arrays.asList(possiblePermutation))));

        // solution analysis:

        // two operations for retrieving separate array lengths,
        // one more operation for comparison of the two lengths (all constant operations): 3

        // Two conversions to lists:
        // Assume one constant operation to create an empty list, therefore 2 operations to create
        // two empty lists

        // list.add(element) for each char of original and each char of possiblePermutation:
        // n + n (both have exactly 'n' elements at this point)

        // total list cost: 2 + 2n

        // comparison of sets for equality: n^2

        // BigO (3 + 2 + 2n + n^2) = BigO(n^2)
    }
}
