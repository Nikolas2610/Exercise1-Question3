import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ProcessThread extends Thread {
    
    private int start;
    private int batchSize;
    private ArrayList<Year> yearsList = new ArrayList<>();
    private final int K;
//    private final String splitWordRegex = "[\s(.,;)]+";
//    \d+
private final String splitWordRegex = "\\W+";
    HashMap<String, Integer> smallWordsCountMap = new HashMap<>();

    public HashMap<String, Integer> getSmallWordsCountMap() {
        return smallWordsCountMap;
    }

    private String getSplitWordRegex() {
        return splitWordRegex;
    }

    public ArrayList<Year> getYearsList() {
        return yearsList;
    }

    public ProcessThread(int start, int batchSize, ArrayList<Year> yearsList, int K) {
        this.start = start;
        this.batchSize = batchSize;
        this.yearsList = yearsList;
        this.K = K;
    }

    @Override
    public void run() {
        System.out.println(getName() + "START: " + start + ", FINISH: " + (start + this.batchSize));
        for (int i = start; i < (start + this.batchSize); i++) {
            for (int j = 0; j < K; j++) {
                String result = makeApiCall(yearsList.get(i));
//                System.out.println(getName() + " , Result: " + result);
            }
        }
        System.out.println(getName() + " finished.");
    }

    private String makeApiCall(Year year) {
        final String API_URL = "http://numbersapi.com/" + year.getYearString() + "/year";

        StringBuilder result = new StringBuilder();
        try {

            URL url = new URL(API_URL);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                result.append(inputLine);
                result.append(" ");
            }
            in.close();
//           TODO: Gia to prwto zitoumeno
            String[] words = result.toString().toLowerCase().split(getSplitWordRegex());
            for (String word: words) {
                if (word.length() < 4) {
                    int wordCount = 1;
                    if (smallWordsCountMap.containsKey(word)) {
                        wordCount = smallWordsCountMap.get(word) + 1;
                    }
                    smallWordsCountMap.put(word, wordCount);
                }
            }
//            TODO: Gia to deutero zitoumeno
            year.addTextMessage(new TextMessage(result.toString(), words.length));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
