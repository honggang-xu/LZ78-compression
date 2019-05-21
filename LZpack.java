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

			//int as the buffer to store the byte
			int datum = 0;
			//int used for masking
			int mask = 0;
			//int for counting phrase entries
			int phraseNumValue = -1;
			//int indicating the available bits
			int availableBits = 32;
			//int used for output
			int output = 0;

			//while there is input
			while (scanner.hasNextLine())
			{
				//if there is an int to read
				if (scanner.hasNextInt())
				{
					//read the int and store it as phrase number
					int phraseNum = scanner.nextInt();
					//byte for the mismatched byte
					byte value;
					//increment counter for number of phrase read
					phraseNumValue++;
					//bits for the phrase number
					int phraseNumBits = Integer.toBinaryString(phraseNumValue).length();
					//store it into mask
					mask = phraseNum;
					//shift its bits to the very left next the previous bits
					mask <<= (availableBits - phraseNumBits);
					//mask into the buffer
					datum |= mask;
					//decrement the available bits
					availableBits -= phraseNumBits;

					//if there is next byte
					if (scanner.hasNextByte())
					{
						//read the byte
						value = scanner.nextByte();
						//store the byte into int and padded with leading zero
						mask = value & 0xFF;
						//shift its bits to the very left next to the previous bits
						mask <<= (availableBits - 8);
						//mask into the buffer
						datum |= mask;
						//decrement the available bits
						availableBits -= 8;
					}

					//while there is 8 bits to out put 
					while ((32 - availableBits >= 8))
					{
						//mask the leading 8 bits
						mask = 255;
						mask <<= 24;
						output = (datum & mask);
						//8 bits padded with leading zero in int
						output >>>= 24;
						//write to the output
						writer.write(output);
						//left shift 8 bits in the buffer
						datum <<= 8;
						//increment available bit
						availableBits += 8;
					}
				}
				//else there is nothing to read next
				else
				{
					//while there is bits in the buffer, output them as 8 bits at a time 
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
			//while there is bits in the buffer, output them as 8 bits at a time
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