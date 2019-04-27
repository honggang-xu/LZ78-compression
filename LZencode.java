import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class LZencode
{
	//member variable for trie
	private static Trie dict;

	public static void main(String [] args)
	{
		try
		{
			//initialize the trie
			dict = new Trie();
			//create reader
			InputStream inputs = new BufferedInputStream(System.in);
			FileWriter file = new FileWriter("encoded.txt");
			//create writer
			BufferedWriter writer = new BufferedWriter(file);
			//int for the result of reading input
			int i;
			
			//while there is input
			while ((i = inputs.read()) != -1)
			{
				//read one byte
				
				
				byte b = (byte)i;
				//next used to iterate the trie
				Trie next = dict;
				//try to find the byte in the trie
				//if found, keep reading another byte
				while (next.find(b) != null)
				{
					next = next.find(b);
					i = inputs.read();
					if (i == -1)
						break;
					b = (byte)i;
				}
				//if the byte is not found, add it to the children of the current trie node
				if (i != -1)
				{
					//add the byte to the children of the current trie node
					next.add(b);
					//write the index of the current node followed by the byte
					writer.write(next.getIndex() + " " + b);
					//write a newline
					writer.newLine();
					//System.out.println(next.getIndex() + " " + (char)b + " " + b);
				}
				//if there is no byte to read
				else
				{
					//only output the index of the current trie node
					writer.write(next.getIndex() + "");
					//System.out.println(next.getIndex());
				}
			}
			writer.close();
			System.out.println("process finished");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}