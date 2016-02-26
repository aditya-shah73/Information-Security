/***
 * Implement a BigInteger modular exponentiation routine 
 * by repeatedly squaring a running total
 * Source: https://cs6490-timing-attack.googlecode.com/hg-history/ec28e613cd7f6089c448725597346f83943f9508/src/timingAttack/ModExp.java
 * 
 */

import java.math.BigInteger;
public class ModExp 
{
	static public final int delayMillis = 0;
	static BigInteger TWO = new BigInteger("2");
	static final boolean verbose = false;

	/***
	 * Modular exponentiation via repeated squaring
	 * 
	 * @param msg the number to square
	 * @param exp the exponent
	 * @param n the modulus
	 * @return msg^e mod n
	 */
	static BigInteger modExp(BigInteger msg, BigInteger exp, BigInteger n)
	{
		int maxBits = exp.bitLength();

		if (verbose) System.out.println("Exponent = " + exp.toString(2));

		BigInteger answer = BigInteger.ONE;
		for( int bit = 0; bit < maxBits; bit++ )
		{
			// explicitly break apart the multiplication and modulus
			answer = answer.multiply(answer);
			if( answer.compareTo(n) > 0 )
				answer = answer.mod(n);

			// do the mult and mod together
			// answer = answer.multiply(answer).mod(n);

			// Consider bits from left to right
			// if the bit is set multiply by msg
			if( exp.testBit(maxBits - bit - 1) )
			{
				if(ModExp.delayMillis > 0)
				try 
				{
						Thread.sleep(delayMillis);
				} 
				catch (InterruptedException e) 
				{
					e.printStackTrace();
					System.exit(-1);
				}
				// explicitly break apart the multiplication and modulus
				answer = answer.multiply(msg);
				if( answer.compareTo(n) > 0 )
					answer = answer.mod(n);
				// do the mult and mod together
				// answer = answer.multiply(msg).mod(n);
				if (verbose) System.out.println("Exponent = 1");
			}
			else
				if (verbose) System.out.println("Exponent = 0");
		}
		return answer;
	}

	/***
	 * This is a wrapper method that uses the BigInteger
	 * modular exponentiation routine on longs
	 * 
	 * @param msg the number to square
	 * @param exp the exponent
	 * @param n the modulus
	 * @return msg^exp mod n
	 */
	static long modExp( long msg, long exp, long n )
	{
		return modExp(new BigInteger(Long.toString(msg)), 
				new BigInteger(Long.toString(exp)), 
				new BigInteger(Long.toString(n))).longValue();
	}

	/***
	 * Test the modular exponentiation routine
	 */
	static void testModExp()
	{
		System.out.println( "s_1 = 10^2851 mod 3233 = " + modExp( 10, 2851, 3233 ) );
		System.out.println( "s_2 = 12^2851 mod 3233 = " + modExp( 12, 2851, 3233 ) );

		// signature for m_1^j mod n is s_1^j mod n
		System.out.println( "1149^2 mod 3233 = " + modExp( 1149, 2, 3233 ) );
		System.out.println( "100^2851 mod 3233 = " + modExp( 100, 2851, 3233 ) );

		// signature for m_1 m_2 mod n is s_1 s_2 mod n
		System.out.println( "(1149*2086)^1 mod 3233 = " + modExp( 1149*2086, 1, 3233 ) );
		System.out.println( "120^2851 mod 3233 = " + modExp( 120, 2851, 3233 ) );

		System.out.println( "970^2851 mod 3233 = " + modExp( 970, 2851, 3233 ) );

		System.out.println( "55^5 mod 551 = " + modExp( 55, 5, 551 ) );

		System.out.println( "189^101 mod 551 = " + modExp( 189, 101, 551 ) );

		System.out.println( "19070^160009 mod 784319 = " + modExp( 19070, 160009, 784319 ) );
	}

	public static void main(String arg[]) 
	{
		for(int i = 1; i < 5000; i++)
		{
			if(i == modExp(i, 17, 3127))
			{
				System.out.println(i); //Prints out all the values till 5000 that satisfy the equation
			}
		}
	}
}