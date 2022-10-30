public class TextMessage {
    private final String textMessage;
    private int textMessageWordSize;

    public TextMessage(String textMessage, int textMessageWordSize) {
        this.textMessage = textMessage;
        this.textMessageWordSize = textMessageWordSize;
    }

    public int getTextMessageWordSize() {
        return textMessageWordSize;
    }
}
