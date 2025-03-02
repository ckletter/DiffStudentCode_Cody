import org.testng.annotations.Test;
import org.junit.jupiter.api.Timeout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PlagiarismCheckerTest {

    private String doc1;
    private String doc2;

    @Test
    public void testCorrectSmall() {
        setTestData(0);
    }

    @Test
    public void testCorrectMed() {
        setTestData(1);
    }

    @Test
    @Timeout(value = 50, unit = TimeUnit.MILLISECONDS)
    public void testEfficientLarge() {
        setTestData(2);
    }

    @Test
    @Timeout(value = 250, unit = TimeUnit.MILLISECONDS)
    public void testEfficientLargest() {
        setTestData(3);
    }

    @Test
    @Timeout(value = 70, unit = TimeUnit.MILLISECONDS)
    public void testEdgeCase() {
        setTestData(4);
    }

    private void setTestData(int testNumber) {
        // Open files
        try {
            BufferedReader testReader = new BufferedReader(new FileReader("test_files/" + testNumber + ".txt"));
            BufferedReader answerReader = new BufferedReader(new FileReader("test_files/" + testNumber + "_answers.txt"));

            // Read in the data, then run.
            int answer = Integer.parseInt(answerReader.readLine());
            loadTest(testReader);
            assertEquals(answer, PlagiarismChecker.longestSharedSubstring(doc1, doc2),
                    "Test " + testNumber + " failed: should return " + answer);

        } catch (IOException e) {
            System.out.println("Error opening test file #" + testNumber);
            e.printStackTrace();
        }
    }

    private void loadTest(BufferedReader br) {

        try {
            doc1 = br.readLine();
            doc2 = br.readLine();

        } catch (IOException e) {
            System.out.println("Error opening test file");
            e.printStackTrace();
        }
    }
}
