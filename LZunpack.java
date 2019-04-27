import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class LZunpack
{
	public static void main(String [] args)
	{
		try
		{
			InputStream inputs = new BufferedInputStream(System.in);
			FileWriter file = new FileWriter("unpacked.txt");
			BufferedWriter writer = new BufferedWriter(file);

			int datum = 0;
			int mask = 0;
			int phraseNumValue = -1;
			int availableBits = 32;
			int index = 0;
			int phrase = 0;

			int i;
			boolean flag = true;
			//int entryCount = 0;
			
			while (availableBits != 0)
			{
				//System.out.println("availableBits" + availableBits);
				
				if ((i = inputs.read()) != -1)
				{
					//System.out.println("has next");
					int nextByte = i;
					//System.out.println("next byte " + nextByte);
					nextByte <<= (availableBits - 8);
					datum |= nextByte;
					availableBits -= 8;
					//System.out.println(entryCount);
				}
				else
				{
					while ((32 - availableBits) > 8)
					{
						phraseNumValue++;
						int phraseNumBits = Integer.toBinaryString(phraseNumValue).length();
						mask = -1;
						mask <<= (32 - phraseNumBits);
						index = datum & mask;
						datum <<= phraseNumBits;

						index >>>= 32 - phraseNumBits;
						availableBits += phraseNumBits;

						mask = 255;
						mask <<= 24;
						phrase = datum & mask;
						datum <<= 8;

						phrase >>>= 24;
						availableBits += 8;

						writer.write(index + " " + (byte)phrase);
						writer.newLine();
						
					}
					availableBits = 0;
				}
			}
			
			
			
			while (flag)
			{
				
				phraseNumValue++;
				int phraseNumBits = Integer.toBinaryString(phraseNumValue).length();
				
				mask = -1;
				mask <<= (32 - phraseNumBits);
				index = datum & mask;
				datum <<= phraseNumBits;

				index >>>= 32 - phraseNumBits;
				availableBits += phraseNumBits;
				

				mask = 255;
				mask <<= 24;
				phrase = datum & mask;
				datum <<= 8;

				phrase >>>= 24;
				availableBits += 8;
				

				writer.write(index + " " + (byte)phrase);
				
				writer.newLine();

				//while there is next input and has 8 bits available
				while (availableBits >= 8)
				{
					if ((i = inputs.read()) != -1)
					{
						int nextByte = i;
						nextByte <<= (availableBits - 8);
						datum |= nextByte;
						availableBits -= 8;
					}
					//else there is no input
					else
					{
						flag = false;
						while ((32 - availableBits) > 8)
						{
							
							phraseNumValue++;
							phraseNumBits = Integer.toBinaryString(phraseNumValue).length();
							mask = -1;
							mask <<= (32 - phraseNumBits);
							index = datum & mask;
							datum <<= phraseNumBits;

							index >>>= 32 - phraseNumBits;
							availableBits += phraseNumBits;

							mask = 255;
							mask <<= 24;
							phrase = datum & mask;
							datum <<= 8;

							phrase >>>= 24;
							availableBits += 8;

							if (phrase != 0)
							{
								writer.write(index + " " + (byte)phrase);
								writer.newLine();
							}
							else
							{
								writer.write(index + "");
							}
						}
						availableBits = 0;
					}
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