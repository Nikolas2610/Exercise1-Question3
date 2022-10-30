import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Question3 {
    private ArrayList<String> yearsList = new ArrayList<>();

    public Question3(int THREAD_COUNT) {
        int K = randomNumber(3,10);
        int N = randomNumber(3,100);
        int maxApiCalls = K * N;
        System.out.println("Years List Size: " + N + " and K: " + K);
        int batchsize = N / THREAD_COUNT;
        randomYear(N);
        ProcessThread[] threads = new ProcessThread[THREAD_COUNT];

        for (int i = 0; i < threads.length; i++) {
            if (i == threads.length - 1) {
                threads[i] = new ProcessThread(i*batchsize, (batchsize + (N % THREAD_COUNT)), yearsList, K);
            } else {
                threads[i] = new ProcessThread(i*batchsize, batchsize, yearsList, K);
            }
            threads[i].start();
        }

        for (ProcessThread thread : threads) {
            try {
                // Wait for the threads to finish.
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("FINISH");
//        randomYear(N);
//        printYearsList();


    }

    private void printYearsList() {
        for (String year: yearsList) {
            System.out.println(year);
        }
    }

    private void randomYear(int N) {
        for (int i = 0; i < N; i++) {
            int year = randomNumber(1900, 2100);
            yearsList.add(Integer.toString(year));
        }
    }

    private int randomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(max-min) + (min);
    }
}
