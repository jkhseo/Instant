package hello;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES_Encryption
{

	static String IV = "AAAAAAAAAAAAAAAA";
	/**
	 * 
	 * @param key       The Key to Encrypt with
	 * @param Message   The message to Encrypt.
	 * @return The Encrypted String
	 */
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
	
	/**
	 * 
	 * @param key       The Key to Decypt with
	 * @param Message   The message to Encrypt.
	 * @return The Decrypted String
	 */
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
}
