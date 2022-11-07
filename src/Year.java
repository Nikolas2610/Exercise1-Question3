import java.util.ArrayList;

public class Year {
    private final int year;
    private ArrayList<TextMessage> textMessageList = new ArrayList<>();
    private double average = 0.0;
    private int callsApi = 0;

    public Year(int year) {
        this.year = year;
    }

    public ArrayList<TextMessage> getTextMessageList() {
        return textMessageList;
    }

    //    Return year
    public int getYear() {
        return year;
    }

//    Increase the APIs calls
    public void increaseCallsApi() {
        callsApi++;
    }

//   Decrease the APIs calls
    public void decreaseCallsApi() {
        callsApi--;
    }

//    Check if the year has available APIs calls | True for yes and False for no
    public boolean availableApiCall(){
        return callsApi > 0;
    }

//    Return the year name as string for the url
    public String getYearString() {
        return Integer.toString(this.year);
    }

//    Add the text message for the exists year
    public void addTextMessage(TextMessage text) {
        textMessageList.add(text);
    }

//    Calculate the average words for the exists words. Second sub question
    public double averageWordsTextMessagePerYear() {
        int sum = 0;
//        Count the words
        for (TextMessage text: textMessageList) {
            sum += text.getTextMessageWordSize();
        }
//        Calc the average
        average = (double)sum / textMessageList.size();
        return average; //return the average value
    }

//    return average value
    public double getAverage() {
        return average;
    }
}
