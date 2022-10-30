import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

public class ProcessThread extends Thread {
    
    private int start;
    private int batchSize;
    private ArrayList<String> yearsList = new ArrayList<>();
    private final int K;

    public ProcessThread(int start, int batchSize, ArrayList<String> yearsList, int K) {
        this.start = start;
        this.batchSize = batchSize;
        this.yearsList = yearsList;
        this.K = K;
    }

    @Override
    public void run() {
        System.err.println(getName() + "START: " + start + ", FINISH: " + (start + this.batchSize));
        for (int i = start; i < (start + this.batchSize); i++) {
            for (int j = 0; j < K; j++) {
                String result = makeApiCall(yearsList.get(i));
                System.out.println(getName() + " , Result: " + result);
            }
        }
        System.out.println(getName() + " finished.");
    }

    private static String makeApiCall(String year) {
        final String API_URL = "http://numbersapi.com/" + year + "/year";
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
