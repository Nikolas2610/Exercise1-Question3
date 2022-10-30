import java.util.ArrayList;
import java.util.Random;

public class Main {
    private static ArrayList<Year> yearsList = new ArrayList<>();
    public static void main(String[] args) {
        int K = randomNumber(3, 30);
        int N = randomNumber(3, 30);
        System.out.println("Years List Size: " + N + " and K: " + K);
        randomYear(N);


        Question3 q1 = new Question3(1, false, N, K, yearsList);
        Question3 q2 = new Question3(2, false, N, K, yearsList);
        Question3 q3 = new Question3(4, false, N, K, yearsList);
        Question3 q4 = new Question3(8, false, N, K, yearsList);
    }

    private static void randomYear(int N) {
        for (int i = 0; i < N; i++) {
            int year = randomNumber(1900, 2023);
            yearsList.add(new Year(year));
        }
    }

    private static int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + (min);
    }


}