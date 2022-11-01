import java.util.ArrayList;
import java.util.HashMap;

public class Question3 {
    private ArrayList<Year> yearsListApi = new ArrayList<>();
    HashMap<String, Integer> smallWordsCountMap = new HashMap<>();

    public Question3(int THREAD_COUNT, boolean printResults, int N, int K, ArrayList<Year> yearsList) {
        System.out.println("============" + THREAD_COUNT + " THREAD============");

//      Get millisecond before program start calculate the hamming
        long start = System.currentTimeMillis();
        ProcessThread[] threads = new ProcessThread[THREAD_COUNT];

//        Divide the APIs calls to the threads with custom function
        int mul = N * K;    // Get the number with all APIs calls
        int batchSize = mul / THREAD_COUNT;  //Get the size of the batch from all APIs calls divide by threads
        int counter = K;    // Max APIs calls per year as counter
        int selectedYear = 0;   // Variable to control the current year from arraylist
        boolean oneTime = false;    // Boolean variable to return true only one time
        Year tempYear = new Year(yearsList.get(selectedYear).getYear());    //Temp year variable to add it to the yearsListApi
        for (int i = 0; i < THREAD_COUNT; i++) {    //Loop the threads
            for (int j = 0; j < batchSize; j++) {   // Loop bt batch size (Size of the thread)
//                This if statement will return true if the modulo of mul and THREAD_COUNT is not zero (For the last thread)
                if (mul % THREAD_COUNT != 0 && i == THREAD_COUNT - 1 && (selectedYear == yearsList.size() - 1) && !oneTime) {
                    //Add the modulo to the counter and batchSize
                    counter += mul % THREAD_COUNT;
                    batchSize += mul % THREAD_COUNT;
                    oneTime = true; //To not access  again this if statement
                }
                if (counter != 0) {
                    tempYear.increaseCallsApi();    //Add the APIs calls to the Year object
                    counter--;  //Decrease the counter
                } else {
                    yearsListApi.add(tempYear); //Add the year to the temp arraylist
                    counter = K; // Add the K values to the counter
                    selectedYear++; // Choose next year
                    j--;    //Skip a round from the for loop
                    tempYear = new Year(yearsList.get(selectedYear).getYear()); //Get the new temp year
                }
            }
            // When thread is full add the current year and send the yearsListApi to the Thread class
            yearsListApi.add(tempYear);
            threads[i] = new ProcessThread(yearsListApi);
            threads[i].start();
            tempYear = new Year(yearsList.get(selectedYear).getYear()); //Get the exists temp year
            yearsListApi.clear();   //Clear the list to add the new Years
        }

        for (ProcessThread thread : threads) {
            try {
                // Wait for the threads to finish.
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//      Find the year with more words by average
        Year maxYear = yearsList.get(0);    //At the first year as max to compare with the others
        for (ProcessThread thread : threads) {
            mergeWordsMap(thread.getSmallWordsCountMap());  //Merge the years list from all threads
            ArrayList<Year> tempYearList = thread.getYearsList();   //Add temp year list to check for max
            for (Year year : tempYearList) {
                if (year.averageWordsTextMessagePerYear() > maxYear.getAverage()) { //If the tempYear is bigger save as max or continue to check
                    maxYear = year;
                }
            }
        }

        //  Print the end time to calculation the process time
        long end = System.currentTimeMillis();
        System.out.println("Duration for " + THREAD_COUNT + " threads: " + (end - start) + "msec");
        System.out.println("============END " + THREAD_COUNT + " THREAD============");

//        Print the result if the value is true from main class
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
//           Loop the new hashmap from thread
        for (String tempWord : tempWordMap.keySet()) {
            int wordCount = tempWordMap.get(tempWord);
//           Check if the key exists to the final hashmap
            if (smallWordsCountMap.containsKey(tempWord)) {
//           If exists add the value from the final map and the thread map
                wordCount = tempWordMap.get(tempWord) + smallWordsCountMap.get(tempWord);
            }
//            Update or add the record
            smallWordsCountMap.put(tempWord, wordCount);
        }
    }

}
