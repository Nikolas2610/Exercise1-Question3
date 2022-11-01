import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static ArrayList<Year> yearsList = new ArrayList<>();

    public static void main(String[] args) {
//        Get random GLOBAL numbers for N and K. Min is 1 to have at least one year and I add as max number 30 because there are a lot of APIs calls
        int K = randomNumber(1, 30);
        int N = randomNumber(1, 30);
//        Create GLOBAL array with random year for 20 and 21st century
        randomYear(N);
//        Print random numbers to console
        System.out.println("Years List Size: " + N + " and K: " + K);

//        Call Question for 1, 2, 4 and 8 threads
        Question3 q1 = new Question3(1, true, N, K, yearsList);
        Question3 q2 = new Question3(2, true, N, K, yearsList);
        Question3 q3 = new Question3(4, true, N, K, yearsList);
        Question3 q4 = new Question3(8, true, N, K, yearsList);
    }

//    Create random years arraylist
    private static void randomYear(int N) {
        for (int i = 0; i < N; i++) {
            int year = randomNumber(1900, 2023);    // 20 and 21st century
            yearsList.add(new Year(year));  // Add the year to the list
        }
    }

//    Create random number function with min and max value
    private static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + (min);
    }
}