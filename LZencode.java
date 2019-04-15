import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class LZencode
{
	private static Trie dict;

	public static void main(String [] args)
	{

		try
		{
			dict = new Trie();
			InputStream inputs = new BufferedInputStream(System.in);
			FileWriter file = new FileWriter("encoded.txt");
			BufferedWriter writer = new BufferedWriter(file);
			int i;
			
			while ((i = inputs.read()) != -1)
			{
				//read one and find 
				//if return null, add
				//if not null keep reading
				byte b = (byte)i;
				Trie next = dict;
				
				while (next.find(b) != null)
				{
					next = next.find(b);
					i = inputs.read();
					if (i == -1)
						break;
					b = (byte)i;
				}
				if (i != -1)
				{
					next.add(b);
					writer.write(next.getIndex() + " " + b);
					writer.newLine();
					System.out.println(next.getIndex() + " " + (char)b + " " + b);
				}
				else
				{
					writer.write(next.getIndex() + "");
					System.out.println(next.getIndex());
				}
			}
			writer.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}