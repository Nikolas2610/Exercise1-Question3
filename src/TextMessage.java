public class TextMessage {
    private final String textMessage;
    private int textMessageWordSize;

    public TextMessage(String textMessage, int textMessageWordSize) {
        this.textMessage = textMessage;
        this.textMessageWordSize = textMessageWordSize;
    }

//    Return the text message word size
    public int getTextMessageWordSize() {
        return textMessageWordSize;
    }
}
