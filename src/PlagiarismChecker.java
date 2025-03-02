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
        ArrayList<String> longestSubstrings = new ArrayList<String>();
        boolean[][] visited = new boolean[lenOne + 1][lenTwo + 1];
        findLongest(longestSubstrings, shared, lenOne, lenTwo, "", doc1, doc2, shared[lenOne][lenTwo], visited);
        for (String longest : longestSubstrings) {
            System.out.println(longest);
        }
        // Return bottom right corner
        return shared[lenOne][lenTwo];
    }
    public static void findLongest(ArrayList<String> longestSubstrings, int[][] shared, int i, int j, String current, String doc1, String doc2, int longestLength, boolean[][] visited) {
        int difference = longestLength - current.length();
        if (visited[i][j]) {
            return;
        }
        if (i - difference < 0 || j - difference < 0) {
            return;
        }
        if (i == 0 || j == 0) {
            if (current.length() == longestLength) {
                longestSubstrings.add(current);
            }
            return;
        }
        // Mark square as visited
        visited[i][j] = true;
        if (shared[i - 1][j - 1] == shared[i][j] - 1 && doc1.charAt(i - 1) == doc2.charAt(j - 1)) {
            current = doc1.charAt(i - 1) + current;
            findLongest(longestSubstrings, shared, i - 1, j - 1, current, doc1, doc2, longestLength, visited);
        }
        else {
            if (shared[i - 1][j] == shared[i][j - 1]) {
                findLongest(longestSubstrings, shared, i - 1, j, current, doc1, doc2, longestLength, visited);
                findLongest(longestSubstrings, shared, i, j - 1, current, doc1, doc2, longestLength, visited);
            }
            else if (shared[i - 1][j] > shared[i][j - 1]) {
                findLongest(longestSubstrings, shared, i - 1, j, current, doc1, doc2, longestLength, visited);
            }
            else {
                findLongest(longestSubstrings, shared, i, j - 1, current, doc1, doc2, longestLength, visited);
            }
        }
    }
}
