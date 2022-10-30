import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Question3 {
    private ArrayList<Year> yearsList = new ArrayList<>();
    HashMap<String, Integer> smallWordsCountMap = new HashMap<>();

    public Question3(int THREAD_COUNT, boolean printResults,int N,int K,ArrayList<Year> yearsList) {
        System.out.println("============" + THREAD_COUNT + " THREAD============");
        this.yearsList = yearsList;

        int batchsize = N / THREAD_COUNT;

        ProcessThread[] threads = new ProcessThread[THREAD_COUNT];

//      Get msec before program start calculate the hamming
        long start = System.currentTimeMillis();

        for (int i = 0; i < threads.length; i++) {
            if (i == threads.length - 1) {
                threads[i] = new ProcessThread(i * batchsize, (batchsize + (N % THREAD_COUNT)), yearsList, K);
            } else {
                threads[i] = new ProcessThread(i * batchsize, batchsize, yearsList, K);
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

//        Year with more words
        Year maxYear = yearsList.get(0);
        for (ProcessThread thread : threads) {
            mergeWordsMap(thread.getSmallWordsCountMap());
            ArrayList<Year> tempYearList = thread.getYearsList();
            for (Year year: tempYearList) {
                if (year.averageWordsTextMessagePerYear() > maxYear.getAverage()) {
                    maxYear = year;
                }
            }
        }

        //      Print the end time to calculation the process time
        long end = System.currentTimeMillis();
        System.out.println("Duration for " + THREAD_COUNT + " threads: " + (end - start) + "msec");
        System.out.println("============END " + THREAD_COUNT + " THREAD============");


        System.out.println("FINISH");
//        printYearsList();
        if (printResults) {
            printData(maxYear);
        }


    }

    private void printData(Year maxYear) {
//        Print Words list
        System.out.println("================SMALL WORDS=================");
        for (String word : smallWordsCountMap.keySet()) {
            System.out.println("Word: \"" + word + "\", has " + smallWordsCountMap.get(word) + " apperances");
        }
        System.out.println("================END WORDS===================");

//        Print year with more words
        System.out.println("================YEAR=================");
        System.out.println("Year " + maxYear.getYear() + ": has average " + maxYear.getAverage() + " words.");
    }

    private void mergeWordsMap(HashMap<String, Integer> tempWordMap) {
        //        Loop the new hashmap from thread
        for (String tempWord : tempWordMap.keySet()) {
            int wordCount = tempWordMap.get(tempWord);
            //            Check if the key exists to the final hashmap
            if (smallWordsCountMap.containsKey(tempWord)) {
//                If exists add the value from the final map and the thread map
                wordCount = tempWordMap.get(tempWord) + smallWordsCountMap.get(tempWord);
            }
//            Update or add the record
            smallWordsCountMap.put(tempWord, wordCount);
        }
    }

    private void printYearsList() {
        for (Year year : yearsList) {
            System.out.println(year);
        }
    }
}
