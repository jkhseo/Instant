package hello;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;

public class RSA_Encyption 
{
	public static final int SIZE = 5000; //backup version
	public static final int SPREAD = 1000; //Max difference maximum prime 
	boolean pullLargePrime = true;
	
	KeySet keys = null;	
	static ArrayList<Integer> primes = new ArrayList<Integer>();

	String[] largePrime = {
			"671998030559713968361666935769",
			"282174488599599500573849980909",
			"521419622856657689423872613771",
			"362736035870515331128527330659",
			"115756986668303657898962467957",
			"590872612825179551336102196593",
			"564819669946735512444543556507",
			"513821217024129243948411056803",
			"416064700201658306196320137931",
			"280829369862134719390036617067"
	};
	
	
	
	/**
	 *  Generates new RSA Encryption keys and posts them to the database
	 */
	public RSA_Encyption()
	{
		GenerateKeys();
		DATABASE_POST.Add_New_RSA_Key(keys.n, keys.EncryptionExponet);
	}
	
	/**
	 * 
	 * @param message Encrypted Message
	 * @return The decrypted message
	 */
	public BigInteger DecryptMessage(int message)
	{
		String strMessage = ""+message;
		String strN = ""+keys.n;
		String strDecryptionExponet = "" + keys.DecryptionExponet;
		
		BigInteger nBIG = new BigInteger(strN);
		BigInteger decryptionExponetBIG = new BigInteger(strDecryptionExponet);

		BigInteger ans = new BigInteger(strMessage);
		ans = ans.modPow(decryptionExponetBIG, nBIG);
	
		return ans;	
	}
	
	
	/**
	 * 
	 * @param message Encrypted Message
	 * @return The decrypted message
	 */
	public BigInteger DecryptMessage_BigInteger(BigInteger message)
	{
		message = message.modPow(keys.DecryptionExponet, keys.n);
		return message;	
	}
	
	
	
	
	/**
	 * 
	 * @param The decrypted message
	 * @return Encrypted Message
	 */
	public BigInteger EncryptMessage(int message)
	{
		String strMessage = ""+message;
		String strN = ""+ keys.n;
		String strEncryptionExponet = "" + keys.EncryptionExponet;
		
		BigInteger nBIG = new BigInteger(strN);
		BigInteger encryptionExponetBIG = new BigInteger(strEncryptionExponet);
		
		BigInteger ans = new BigInteger(strMessage);
		System.out.println(ans + " ^ " + encryptionExponetBIG + " % " + nBIG);
		ans = ans.modPow(encryptionExponetBIG, nBIG);
		
		return ans;
		
	}
	
	/**
	 * 
	 * @param The decrypted message
	 * @return Encrypted Message
	 */
	public BigInteger EncryptMessage_Big_Integer(BigInteger message)
	{
		String strN = ""+ keys.n;
		String strEncryptionExponet = "" + keys.EncryptionExponet;
		

		message = message.modPow(keys.EncryptionExponet, keys.n);
		
		return message;
		
	}
	
	
	
	
	
	
	/**
	 * Generate Keys
	 */
	public void GenerateKeys()
	{
		System.out.println("Generating RSA KEYS ... Size = " + SIZE);
		long StartTime = System.currentTimeMillis();
		

		
		int random1;
		int random2;

		BigInteger p;
		BigInteger q;
		
		
		if(pullLargePrime)
		{
			 random1 = (int) (Math.random() * 10);
			 random2 = (int) (Math.random() * 10);
			
			while(random1 == random2)
				random2 = (int) (Math.random() * 10);
			
			p = new BigInteger(largePrime[random1]);
			q = new BigInteger(largePrime[random2]);
		}
		else
		{
			

			 random1 = (int) (Math.random() * SPREAD);
			 random2 = (int) (Math.random() * SPREAD);
			
			while(random1 == random2)
				random2 = (int) (Math.random() * SPREAD);
			
			String prime1 = DATABASE_GET.getPrime(random1);
			String prime2 = DATABASE_GET.getPrime(random2);
			
			//if the database is down, do it the old way.
			if(prime1.equalsIgnoreCase("Null") || prime2.equalsIgnoreCase("Null"))
			{
				//Old Way
				addPrimes(SIZE);
				primes.remove(0);
				removePrimes(.95); //Remove the first 95% of primes. 
				
				int firstPrime = (int) (Math.random() * primes.size());
				p = new BigInteger("" + primes.get(firstPrime));
				primes.remove(firstPrime);
				
				q = new BigInteger("" + primes.get((int) (Math.random() * primes.size())));
			
			}
			else
			{
				p = new BigInteger(prime1);
				q = new BigInteger(prime2);
			}
		}
		
		BigInteger n = p.multiply(q);	
		BigInteger m = p.subtract(new BigInteger("1")).multiply(q.subtract(new BigInteger("1")));
		
		//int intN = Integer.parseInt(n.toString());
		
		//S is the encryption exponet 
		BigInteger s = new BigInteger("2");
		while(!gcd_Big_Integer(s,m).equals(new BigInteger("1")))
		{
			//System.out.println(gcd_Big_Integer(s,m));
			
			BigInteger log = n.subtract(new BigInteger("4"));
			
			Random rand = new Random();
		    BigInteger result = new BigInteger(n.bitLength(), rand);
		    while( result.compareTo(n) >= 0 ) 
		    {
		        result = new BigInteger(n.bitLength(), rand);
		    }
		    
		    s = result.add(new BigInteger("3"));
		}
		//T is the decryption exponent
		//T*S - 1 % M == 0 //Equation for T. 
		//In other words, T is the Modular inverse of S. 
		BigInteger t = modularInverse_Big_Integer(s, m); 
		
		
		long EndTime = System.currentTimeMillis();
		System.out.println("Finished RSA Keys. " + (EndTime - StartTime) + "ms.");
		
				System.out.println("Public KEYS");
				System.out.println("N = " + n + " Encryption Exponet = " + s);
				System.out.println("Private KEYS");
				System.out.println("Decryption Exponet = " + t);
				
				System.out.println("Other Stuff");
				System.out.println("P = " + p + " Q = " + q + " M = " + m );
				
		
		//int[] vals = gcdExtended(p, q);
		//System.out.println(vals[1] + "(" + p + ") + " + vals[2] + "(" + q + ") = " + vals[0]);
		
				
		
		keys = new KeySet();
		keys.EncryptionExponet = s;
		keys.DecryptionExponet = t;
		keys.n = n;
	}
	
	
	
	private static void addPrimes(int n)
	{
		while(primes.size() < n)
		{
			if(primes.size() == 0)
				primes.add(2);
			int potential = primes.get(primes.size()-1)+1;
			
			boolean prime = false;
			while(prime == false)
			{
				prime = true;
				for(Integer e : primes)
				{
					if(potential%e ==0 )
					{
						prime = false;
						break;
					}
				}
				
				if(prime)
					primes.add(potential);
				else
					potential++;
			}
			
		}
	}
	
	private static void removePrimes(double amount)
	{
		int limit = (int) (amount * primes.size());
		for(int i=0; i<limit; i++)
			primes.remove(0);
		
	}
	
	  //Find GCD's two coefficents by Euclid's extened algorithm 
 	  //Example for extended algortihm found on princton CS website. 
	   static int[] gcdExtended(int p, int q)
	   {
		      if (q == 0)
		         return new int[] { p, 1, 0 };

		      int[] vals = gcdExtended(q, p % q);
		      int d = vals[0];
		      int a = vals[2];
		      int b = vals[1] - (p / q) * vals[2];
		      return new int[] { d, a, b };
	   }
	   
	   
	 //Finds Modular Inverse for BigInteger
	private static BigInteger modularInverse_Big_Integer(BigInteger s , BigInteger m)
	{
		return s.modInverse(m);
		
	}  

	//Finds Modular Inverse
	//Need to find a faster way to do this. 
	private static int modularInverse(int s, int m)
	{
		for(int i=0; i<=m-1; i++)
		{
			//System.out.println(s + "*" + i + "%" + m + " == " + (s*i % m));
			if(s*i % m == 1)
				return i; 
		}
		return -1;
		
	}
	
	   
	   
	//Find LCM Efficently by finding GCD and then 
	// LCM = num1*num2 / GCD
	private static int lcm(int num1, int num2)
	{
		return (num1*num2)/gcd(num1,num2);
	}
	
	
	private static BigInteger gcd_Big_Integer(BigInteger num1, BigInteger num2)
	{
		BigInteger gcd = new BigInteger("0");
		
	
		while(num1.compareTo(gcd) > 0   && num2.compareTo(gcd) > 0)
		{
			if(num1.compareTo(num2) == 1)
				num1 = num1.mod(num2);
			else
				num2 = num2.mod(num1);
			//System.out.println("Num1 " + num1 + " Num2 " + num2);
		}
		
		if(num1.compareTo(gcd) != 0 )
			gcd = num1;
		else
			gcd = num2;
		//System.out.println("GCD = " + gcd);
		return gcd;
				
	}
	//Find GCD by Euclid's algorithm 
	private static int gcd(int num1, int num2)
	{
		int gcd = 0;
		while(num1 > 0 && num2 > 0)
		{
			if(num1 >= num2)
				num1 = num1 % num2;
			else
				num2 = num2 % num1;
			//System.out.println("Num1 " + num1 + " Num2 " + num2);
		}
		
		if(num1 != 0 )
			gcd = num1;
		else
			gcd = num2;
		//System.out.println("GCD = " + gcd);
		return gcd;
	}
}