import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Scanner;

public class LZdecode
{
	private static ArrayList<String> dict;

	public static void main(String [] args)
	{
		if (args.length != 1)
		{
			System.err.println("Usage: java LZdecode <filename>");
			return;
		}

		try
		{
			dict = new ArrayList<String>();
			dict.add(null);
			Scanner scanner = new Scanner(new File(args[0]));
			FileWriter file = new FileWriter("decoded.txt");
			BufferedWriter writer = new BufferedWriter(file);
			
			while (scanner.hasNext())
			{
				System.out.println("into the while loop");
				int index = -1;
				if ((index = scanner.nextInt()) == 0)
				{
					System.out.println("read an int");
					//skip the space
					//scanner.skip(" ");
					byte data = scanner.nextByte();
					String word = Character.toString((char)data);
					dict.add(word);
					System.out.println(word);
					writer.write(word);
				}
				else
					//scanner.hasNextInt()
				{
					//skip the space
					//scanner.skip(" ");
					// if (scanner.hasNextByte())
					// {
					if (!scanner.hasNextByte())
					{
						String parent = dict.get(index);
						System.out.println(parent);
						writer.write(parent);
					}
					else
					{
						String parent = dict.get(index);
						byte data = scanner.nextByte();
						String word = Character.toString((char)data);
						dict.add(parent + word);
						System.out.println(parent + word);
						writer.write(parent + word);
					}
						
					//}
				
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