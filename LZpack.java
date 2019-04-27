import java.io.File;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.Scanner;

public class LZpack
{
	public static void main(String [] args)
	{
		if (args.length != 1)
		{
			System.err.println("Usage: java LZpack <filename>");
			return;
		}

		try
		{
			Scanner scanner = new Scanner(new File(args[0]));
			FileOutputStream outputFile = new FileOutputStream("packed.txt");
			ByteArrayOutputStream writer = new ByteArrayOutputStream();

			int datum = 0;
			int mask = 0;
			int phraseNumValue = -1;
			int availableBits = 32;
			int output = 0;

			while (scanner.hasNextLine())
			{
				if (scanner.hasNextInt())
				{
					int phraseNum = scanner.nextInt();
					byte value;

					phraseNumValue++;
					//bits for the phrase number
					int phraseNumBits = Integer.toBinaryString(phraseNumValue).length();

					mask = phraseNum;
					mask <<= (availableBits - phraseNumBits);
					datum |= mask;
					availableBits -= phraseNumBits;

					//if there is next byte
					if (scanner.hasNextByte())
					{
						value = scanner.nextByte();
						mask = value & 0xFF;
						mask <<= (availableBits - 8);
						datum |= mask;
						availableBits -= 8;
					}
					/*
					else
					{
						System.out.println(phraseNum);
					}
					*/

					//while there is 8 bits to out put 
					while ((32 - availableBits >= 8))
					{
						mask = 255;
						mask <<= 24;
						output = (datum & mask);
						output >>>= 24;

						writer.write(output);
						datum <<= 8;
						availableBits += 8;
					}
				}
				//else there is nothing to read next
				else
				{
					while (availableBits < 32)
					{
						int bitsRemained = (32 - availableBits) > 8 ? 8 : (32 - availableBits);
						int bitsToShift = 32 - bitsRemained;

						mask = -1;
						mask <<= 24;
						output = (datum & mask);
						output >>>= 24;

						writer.write(output);
						datum <<= 8;
						availableBits += 8;
					}
					
					scanner.nextLine();
				}
			}

			while (availableBits < 32)
			{
				int bitsRemained = (32 - availableBits) > 8 ? 8 : (32 - availableBits);
				int bitsToShift = 32 - bitsRemained;

				mask = -1;
				mask <<= 24;
				output = (datum & mask);
				output >>>= 24;

				writer.write(output);
				datum <<= 8;
				availableBits += 8;
			}

			writer.writeTo(outputFile);
			writer.close();
			System.out.println("process finished");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}