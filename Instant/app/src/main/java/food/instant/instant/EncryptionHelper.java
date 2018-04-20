package food.instant.instant;

import android.content.Context;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by mpauk on 4/10/2018.
 */

public class EncryptionHelper {
    static String IV = "AAAAAAAAAAAAAAAA";
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
    public static byte[] AES_Encrypt(byte[] encryptionKey, String Message)
    {
        Cipher cipher;
        try
        {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(encryptionKey, "AES");

            cipher.init(Cipher.ENCRYPT_MODE, key,new IvParameterSpec(IV.getBytes("UTF-8")));

            return cipher.doFinal(Message.getBytes("UTF-8"));
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }

    public static String AES_Decrypt(byte[] encryptionKey, byte[] message)
    {
        Cipher cipher;
        try
        {
            cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            SecretKeySpec key = new SecretKeySpec(encryptionKey, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));

            byte[] plainByte = cipher.doFinal(message);

            String plainText = new String(plainByte);

            return plainText;
        }
        catch (NoSuchAlgorithmException | NoSuchPaddingException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (BadPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

    public static BigInteger generateAESKEY(){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        BigInteger n = new BigInteger(key);
        return n;
    }

}
