package food.instant.instant;

import android.content.Context;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by mpauk on 4/10/2018.
 */

public class EncryptionHelper {
    /**
     *
     * @param message The decrypted message
     * @return Encrypted Message
     */
    public static BigInteger EncryptMessage_Big_Integer(BigInteger message,BigInteger RSA_KEY,BigInteger Exponent)
    {
        System.out.println("RSA_KEY"+RSA_KEY);
        System.out.println("Exponent"+Exponent);
        String strN = RSA_KEY.toString();
        String strEncryptionExponet = Exponent.toString();
        BigInteger nBIG = new BigInteger(strN);
        BigInteger encryptionExponetBIG = new BigInteger(strEncryptionExponet);
        message = message.modPow(encryptionExponetBIG, nBIG);
        return message;
    }
    public static String AES_EncryptionHelper(String path){
        String[] unencryptedData = path.replaceAll("&","=").split("=");
        BigInteger[] numberRepresentation = new BigInteger[unencryptedData.length];
        for(int i=0;i<unencryptedData.length;i++){
            numberRepresentation[i] = new BigInteger(stringToInt(unencryptedData[i]));
        }
        for(int i=0;i<unencryptedData.length;i++){
            path = path.replace(unencryptedData[i],numberRepresentation[i].toString());
        }
        return path;
    }
    public static BigInteger AES_Encrypt_Message(BigInteger message, int AESKEY){
        return null;
    }

    public static String stringToInt(String text){
        return new BigInteger(text.getBytes()).toString();
    }
    public static String intToString(String integer){
        return new String(new BigInteger(integer).toByteArray());
    }
    public static void main(String[] args){
        System.out.println(stringToInt("password"));
    }
    //string
    //integer
    //encrypted integer

    public static int generateAESKEY(){
        Random random = new Random();
        return random.nextInt(10000)+10000;

    }

}
