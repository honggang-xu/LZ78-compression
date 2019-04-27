import java.util.ArrayList;
import java.io.File;
import java.io.FileOutputStream;
import java.io.BufferedOutputStream;
import java.util.Scanner;

public class LZdecode
{
	//a dictionary to store all the phrases
	private static ArrayList<ArrayList<Byte>> dict;

	public static void main(String [] args)
	{
		if (args.length != 2)
		{
			System.err.println("Usage: java LZdecode <encoded file> <file name for decoded output>");
			return;
		}

		try
		{
			dict = new ArrayList<ArrayList<Byte>>();
			dict.add(null);
			Scanner scanner = new Scanner(new File(args[0]));
			String filename = args[1];
			BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream(new File(filename)));
			//while there is input
			while (scanner.hasNext())
			{
				int index = -1;
				//read the index value and if the index is 0
				if ((index = scanner.nextInt()) == 0)
				{
					//read the mismatched byte
					byte data = scanner.nextByte();
					//create a list representing the phrase to store byte
					ArrayList<Byte> node = new ArrayList<Byte>();
					//add the mismatched byte to the list
					node.add(data);
					//add the list to the dictionary
					dict.add(node);
					//write the phrase to output
					writer.write(data);
				}
				//if the index is not 0
				else
				{
					//if there is no byte to read next
					if (!scanner.hasNextByte())
					{
						//use index to find the matching phrase
						ArrayList<Byte> parent = dict.get(index);
						//write current phrase to output
						for (byte value : parent)
						{
							writer.write(value);
						}
					}
					//there is a mismatched byte
					else
					{
						//use index to find the matching phrase
						ArrayList<Byte> parent = dict.get(index);
						//read the mismatched byte
						byte data = scanner.nextByte();
						//create a list representing the phrase to store byte
						ArrayList<Byte> childNode = new ArrayList<Byte>();
						//add the matching phrase to the list
						for (byte previous : parent)
						{
							childNode.add(previous);
						}
						//add the mismatched byte to the list
						childNode.add(data);
						//add the list to the dictionary
						dict.add(childNode);
						//write current phrase to output
						for (byte value : childNode)
						{
							writer.write(value);
						}
					}
				}
			}
			//flush the output stream
			writer.flush();
			System.out.println("process finished");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}