package hello;

import java.util.ArrayList;
import java.util.Scanner;

import org.hibernate.boot.model.relational.Database;

import java.math.BigInteger;

public class RSA_Encyption 
{
	public static final int SIZE = 1000;
	KeySet keys = null;	
	static ArrayList<Integer> primes = new ArrayList<Integer>();

	public RSA_Encyption()
	{
		GenerateKeys();
		DATABASE_POST.Add_New_RSA_Key(keys.n, keys.EncryptionExponet);
	}
	
	
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
	
	public void GenerateKeys()
	{
		System.out.println("Generating RSA KEYS ... ");
		long StartTime = System.currentTimeMillis();
		
		addPrimes(SIZE);
		primes.remove(0);
		removePrimes(.95); //Remove the first 95% of primes. 
		
		int firstPrime = (int) (Math.random() * primes.size());
		int p = primes.get(firstPrime);
		primes.remove(firstPrime);
		
		int q = primes.get((int) (Math.random() * primes.size()));
		int n = p*q;
		int m = (p-1)*(q-1); //Euler-Toient Function for realtively prime numbers;
		
		//S is the encryption exponet 
		int s = 2;
		while(gcd(s,m) > 1)
			s = ((int) (Math.random() * (n-4))) + 3;

		//T is the decryption exponent
		//T*S - 1 % M == 0 //Equation for T. 
		//In other words, T is the Modular inverse of S. 
		int t = modularInverse(s, m); 
		
		
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
	
	public static void removePrimes(double amount)
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

	//Finds Modular Inverse
	//Need to find a faster way to do this. 
	private static int modularInverse(int s, int m)
	{
		for(int i=0; i<m-1; i++)
		{
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