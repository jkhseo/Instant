package food.instant.instant;

/**
 * Created by mpauk on 3/28/2018.
 */

public class MessageContract {
    private MessageContract(){}

    public static class MessageEntry
    {
        public static final String TABLE_NAME = "message_table";
        public static final String MESSAGE = "message";
        public static final String RECIEVER_TYPE = "rec_type";
        public static final String RECIEVER_ID = "rec_id";
        public static final String SENDER_TYPE = "send_type";
        public static final String SENDER_ID = "send_id";


    }
}
