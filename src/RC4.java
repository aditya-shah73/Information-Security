import java.util.Random;

public class RC4
{
	private final byte[] S = new byte[256];
	private final byte[] T = new byte[256];
	private int keylen = 0;

	public void print()
	{
		System.out.format( "%5s", " " );
		
		for(int j=0;j<16;j++)
		{
			System.out.format( "%5d", j );
		}
		
		for(int i=0;i<S.length;i++)
		{
			if(i%16 == 0)
			{
				System.out.format( "\n%5d", i/16 );
			}
			System.out.format( "%5d", S[i] );
		}
		
		System.out.println();
		System.out.println();
	}

	public RC4(final byte[] key)
	{
		if (key.length < 1 || key.length > 256) 
		{
			throw new IllegalArgumentException("key must be between 1 and 256 bytes");
		} 
		else 
		{
			keylen = key.length;
			for (int i = 0; i < 256; i++) 
			{
				S[i] = (byte) i;
				T[i] = key[i % keylen];
			}
			int j = 0;
			byte tmp;
			for (int i = 0; i < 256; i++) 
			{
				j = (j + S[i] + T[i]) & 0xFF;
				tmp = S[j];
				S[j] = S[i];
				S[i] = tmp;
			}
		}
	}

	public byte[] encrypt(final byte[] plaintext) 
	{
		final byte[] ciphertext = new byte[plaintext.length];
		int i = 0, j = 0, k, t;
		byte tmp;
		for (int counter = 0; counter < plaintext.length; counter++) 
		{
			i = (i + 1) & 0xFF; //oxFF is the HEX value for 255
			j = (j + S[i]) & 0xFF;
			tmp = S[j];
			S[j] = S[i];
			S[i] = tmp;
			t = (S[i] + S[j]) & 0xFF;
			k = S[t];
			ciphertext[counter] = (byte) (plaintext[counter] ^ k);
		}
		return ciphertext;
	}

	public byte[] decrypt(final byte[] ciphertext) 
	{
		return encrypt(ciphertext);
	}

	public static void main(String[] args) throws Exception 
	{
		byte[] key = {(byte)0x1A, (byte)0x2B, (byte)0x3C, (byte)0x4D, (byte)0x5E, (byte)0x6F, (byte)0x77}; //Byte array

		StringBuilder builder = new StringBuilder();
		Random r = new Random(System.currentTimeMillis());

		RC4 rc4 = new RC4(key);// Initialization Stage
		System.out.print("After initialization:\n");
		rc4.print();
		
		for(int i = 0;i<100;i++)
		{
			builder.append((char)(r.nextInt(26) + 'a'));
		}
		byte[] value = builder.toString().getBytes();
		byte[] ct = rc4.encrypt(value);
		System.out.print("After 100 bytes of the keystream:\n");// After 100 byes
		rc4.print();
		
		builder = new StringBuilder();
		
		for(int i = 0;i<1000;i++)
		{
			builder.append( (char)(r.nextInt(26) + 'a') );
		}
		byte[] value1 = builder.toString().getBytes();
		rc4 = new RC4(key);
		byte[] pt2 = rc4.encrypt(value1);
		System.out.print("After 1000 bytes of the keystream:\n");// After 1000 bytes
		rc4.print();
		System.out.println();
	}
}