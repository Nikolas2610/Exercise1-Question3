import java.util.ArrayList;
import java.util.HashMap;

public class Year {
    private final int year;
    private ArrayList<TextMessage> textMessageList = new ArrayList<>();
    private double average = 0.0;

    public Year(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public String getYearString() {
        return Integer.toString(this.year);
    }

    public void addTextMessage(TextMessage text) {
        textMessageList.add(text);
    }

    public double averageWordsTextMessagePerYear() {
        int sum = 0;
        for (TextMessage text: textMessageList) {
            sum += text.getTextMessageWordSize();
        }
        average = (double)sum / textMessageList.size();
        return average;
    }

    public Year getYearObject() {
        return this;
    }

    public double getAverage() {
        return average;
    }
}
