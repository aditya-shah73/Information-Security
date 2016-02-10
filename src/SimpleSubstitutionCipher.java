import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SimpleSubstitutionCipher 
{
	public static String compressed(String s)
	{
		char ch;
		String compress = "";
		HashMap<Character, Integer> h = new HashMap<Character, Integer>();

		for(int i=0;i<s.length();i++)
		{
			ch = s.charAt(i);
			if(h.containsKey(ch))
			{
				h.put(ch,h.get(ch)+1);
			}
			else
			{
				h.put(ch, 1);
			}
		}

		for(Map.Entry<Character, Integer> entry : h.entrySet())
		{
			char key = entry.getKey();
			int val = entry.getValue();
			compress = compress + key + ' ' + val + '\n';
		}
		return compress;
	}
	
	public static String key(String s, int key)
	{
		char ch;
		String r = "";
		for(int i=0;i<s.length();i++)
		{
			ch = s.charAt(i);
			int a = 55 + Character.getNumericValue(ch);
			a -= key;
			if(a < 64)
			{
				a = 90 - 64 % a;
			}
			r += Character.toString((char) a);
		}
		return r;
	}
		
	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		System.out.println("Enter a string: ");
		String s = in.nextLine();
		String result1 = "";
		String result2 ="";
		result1 = compressed(s);
		System.out.println(result1);
		System.out.println("Enter a putative key: ");
		int a = in.nextInt();
		result2 = key(s,a);
		System.out.println(result2);	
	}
}