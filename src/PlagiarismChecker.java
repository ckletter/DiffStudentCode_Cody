import java.util.ArrayList;
import java.util.Arrays;

/**
 * Plagiarism Checker
 * A tool for finding the longest shared substring between two documents.
 *
 * @author Zach Blick
 * @author YOUR NAME HERE
 */
public class PlagiarismChecker {

    /**
     * This method finds the longest sequence of characters that appear in both texts in the same order,
     * although not necessarily contiguously.
     * @param doc1 the first document
     * @param doc2 the second
     * @return The length of the longest shared substring.
     */
    public static int longestSharedSubstring(String doc1, String doc2) {
        return tabulationLSS(doc1, doc2);
    }

    /**
     * Implements longest shared substring using a tabulation approach
     * @param doc1 first document
     * @param doc2 second document
     * @return
     */
    public static int tabulationLSS(String doc1, String doc2) {
        int lenOne = doc1.length();
        int lenTwo = doc2.length();
        // Create array for tabulation (first row and column already filled with 0s)
        int[][] shared = new int[lenOne + 1][lenTwo + 1];
        // Loop through each index in array
        for (int i = 1; i <= lenOne; i++) {
            for (int j = 1; j <= lenTwo; j++) {
                // If the letters match between letters at the given index,
                // current index becomes index of top left square above it plus one
                if (doc1.charAt(i - 1) == doc2.charAt(j - 1)) {
                    shared[i][j] = shared[i - 1][j - 1] + 1;
                }
                // If no match, current index becomes the max of the left and right squares
                else {
                    shared[i][j] = Math.max(shared[i - 1][j], shared[i][j - 1]);
                }
            }
        }
        // Create an array to store all the longest shared substrings
        ArrayList<String> longestSubstrings = new ArrayList<String>();
        findLongest(longestSubstrings, shared, lenOne, lenTwo, "", doc1, doc2, shared[lenOne][lenTwo]);
        // Print out each of the longest shared substrings
        for (String longest : longestSubstrings) {
            System.out.println(longest);
        }
        // Return bottom right corner
        return shared[lenOne][lenTwo];
    }

    /**
     * DFS backtracking approach to find all the longest shared substrings, saving it in longest substrings
     */
    public static void findLongest(ArrayList<String> longestSubstrings, int[][] shared, int i, int j, String current, String doc1, String doc2, int longestLength) {
        int difference = longestLength - current.length();
        // Not possible to add enough letters to reach target longest substring
        if (i - difference < 0 || j - difference < 0) {
            return;
        }
        // If on first row or column, if our current string matches our target length, add the string to our array
        if (i == 0 || j == 0) {
            if (current.length() == longestLength) {
                longestSubstrings.add(current);
            }
            return;
        }
        // If the two chars are equal at current location, search the top left corner
        if (shared[i - 1][j - 1] == shared[i][j] - 1 && doc1.charAt(i - 1) == doc2.charAt(j - 1)) {
            current = doc1.charAt(i - 1) + current;
            findLongest(longestSubstrings, shared, i - 1, j - 1, current, doc1, doc2, longestLength);
        }
        else {
            // If the squares left and up are equal, add both searches to call stack
            if (shared[i - 1][j] == shared[i][j - 1]) {
                findLongest(longestSubstrings, shared, i - 1, j, current, doc1, doc2, longestLength);
                findLongest(longestSubstrings, shared, i, j - 1, current, doc1, doc2, longestLength);
            }
            // If the square up is greater than square left, search square up
            else if (shared[i - 1][j] > shared[i][j - 1]) {
                findLongest(longestSubstrings, shared, i - 1, j, current, doc1, doc2, longestLength);
            }
            // If the square left is greater than square up, search square left
            else {
                findLongest(longestSubstrings, shared, i, j - 1, current, doc1, doc2, longestLength);
            }
        }
    }
}
