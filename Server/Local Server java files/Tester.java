package hello;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class Tester {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
		
		//data adding (String name, int ID, String cuisine_main, String cuisine_secondary, String[] keywords) 
//		String[] keywords_a = {"Chain", "Always Fresh Never Frozen"};
//		Restaurant r1 = new Restaurant("Wendy's", 1 , "American" , "Fast Food", keywords_a);
//		restaurants.add(r1);
//		
//		String[] keywords_b = {"Chain", "Chain", "Have it your way"};
//		Restaurant r2 = new Restaurant("Burger King", 2 , "American" , "Fast Food", keywords_b);
//		restaurants.add(r2);
//		
//		String[] keywords_c = {"Student Dining", "Never know what you will find"};
//		Restaurant r3 = new Restaurant("UDCC", 3 , "Mixed" , "Buffet", keywords_c );
//		restaurants.add(r3);
//		
//		
//		DATABASE_GET.searchRestaurant_KeyWords("Amerkan");
//		
//		System.out.println();
//		System.out.println("NEXT DEMO STUFF ");
//		System.out.println();
//		
//		//Simuating a typical encounter
//		//User Logs in 
//		MainController mc = new MainController();
//		String JSONreturned = mc.getRSAPublicKey();
//		
//		String  newESAKey = DATABASE_UTILS.StringToInt("test");
//		System.out.println("Chosing ESA Key to be " + newESAKey);
//		BigInteger bigRep = new BigInteger(newESAKey);
//		System.out.println("BIG INT REP "  + bigRep );
//		
//		BigInteger EncyrptedData = mc.RSA.EncryptMessage_Big_Integer(bigRep);
//		System.out.println("Encrypted Version is " + EncyrptedData);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData));
//
//		System.out.println();
//		System.out.println("TESTING");
//		System.out.println();
//		
//		String  newESAKey1 = "2";
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		BigInteger EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//		
//		newESAKey1 = "13";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//		
//		newESAKey1 = "123";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//		
//		newESAKey1 = "1251";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//		
//		newESAKey1 = "13622";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//		
//		newESAKey1 = "136232";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//	
//		newESAKey1 = "1362432";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//	
//		newESAKey1 = "12592757";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
//	
//		newESAKey1 = "395837621";
//		System.out.println();
//		System.out.println("Chosing ESA Key to be " + newESAKey1);
//		
//		EncyrptedData1 = mc.RSA.EncryptMessage_Big_Integer(new BigInteger(newESAKey1));
//		System.out.println("Encrypted Version is " + EncyrptedData1);
//		System.out.println("Decyrpted Version is " + mc.RSA.DecryptMessage_BigInteger(EncyrptedData1));
	//
	//
	//			
	//		String text = "&";
	//		System.out.println("Text: "+text);
	//
	//		String integer = new BigInteger(text.getBytes()).toString();
	//		System.out.println("As number: "+integer);
	//		
	//
	//		String text2 = new String(new BigInteger(integer).toByteArray());
	//		System.out.println("As text: "+text2);
	//	
		
		
//		String message = "Hello World! My name is Adam and I enjoy Marijuiana. Twice Daily I smoke weed";
//		
//		SecureRandom random = new SecureRandom();
//		byte[] key = new byte[16];
//		random.nextBytes(key);
//		BigInteger n = new BigInteger(key);
//		
//		System.out.println(Arrays.toString(key));
//		
//
//		System.out.println(n);
//		System.out.println(Arrays.toString(n.toByteArray()));
//		
//		
//		BigInteger encypted	= new BigInteger(AES_Encryption.AES_Encrypt(key, message));
//		System.out.println(encypted);
//		String decMessage = AES_Encryption.AES_Decrypt(key, encypted.toByteArray());
//		System.out.println(decMessage);
	
	
	String exampleURL = "?hello=adam&goodbye=peter&euler=project";
	String cutout = replace(exampleURL);
	System.out.println(cutout);

	}
	public static String replace(String example)
	{
		for(int i=0; i<example.length(); i++)
		{
			int question = example.indexOf("=", i);
			int and = example.indexOf("&", i);
			
			if(and != -1)
			{
				System.out.println(question + " " + and + " : " + example.substring(question+1,and));
				i = and;
			}
			else
			{
				System.out.println(question + " " + and + " : " + example.substring(question+1));
				i = example.length();
			}
			
		}
		return example;
	}
	


}
