package food.instant.instant;

/**
 * Created by mpauk on 4/10/2018.
 */

public class KeyContract {
    private KeyContract(){}

    public static class KeyEntry
    {
        public static final String TABLE_NAME = "key_table";
        public static final String SESSION_KEY_VALUE = "session_key_value";
        public static final String ENCRYPTION_EXPONENT = "encryption_exponent";
        public static final String VERSION = "version";
        public static final String AES_KEY="aes_key";
    }
}
