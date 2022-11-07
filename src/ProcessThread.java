import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessThread extends Thread {

    private ArrayList<Year> yearsList = new ArrayList<>();
    private final String splitWordRegex = "\\W+";   // Regex for split the words
    HashMap<String, Integer> smallWordsCountMap = new HashMap<>();  //Use Hashmap for better performance

//    Return hashmap
    public HashMap<String, Integer> getSmallWordsCountMap() {
        return smallWordsCountMap;
    }

//    Return Regex value
    private String getSplitWordRegex() {
        return splitWordRegex;
    }

//    Return YearsList
    public ArrayList<Year> getYearsList() {
        return yearsList;
    }

    public ProcessThread(ArrayList<Year> yearsList) {
        this.yearsList = (ArrayList<Year>)yearsList.clone();    //Clone the arraylist because when we clear the arraylist from Question3 class we lost the years
    }

    @Override
    public void run() {
        for (Year year : yearsList) {   //Loop the years
            while (year.availableApiCall()) {   // Check if the year have available APIs calls
                String result = makeApiCall(year);  // Make the API call to get the data
//                System.out.println(getName() + " , Result: " + result);   // Print the result from API call for DEBUG
                year.decreaseCallsApi(); // Decrease the APIs call variable from year
            }
        }
        System.out.println(getName() + " finished.");   // Thread Finish
    }

    private String makeApiCall(Year year) {
//        URL with custom year param
        final String API_URL = "http://numbersapi.com/" + year.getYearString() + "/year";

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(API_URL);
//            Add the return data to a bufferReader
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
                result.append(" ");
            }
            in.close(); //Close connection

            // Split the words of the sentence for the first sub question
            String[] words = result.toString().toLowerCase().split(getSplitWordRegex());
            for (String word : words) { // Loop the words array
                if (word.length() < 4) {    // Save the words with max length 3
                    int wordCount = 1;
//                    If the word exists increase the count word variable
                    if (smallWordsCountMap.containsKey(word)) {
                        wordCount = smallWordsCountMap.get(word) + 1;
                    }
//                    Create or update word
                    smallWordsCountMap.put(word, wordCount);
                }
            }
//          Save the sentence to the TextMessage Class with the length of the sentence for the second sub question
            year.addTextMessage(new TextMessage(result.toString(), words.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();   // Return the string value if we want to print it for DEBUG
    }
}
