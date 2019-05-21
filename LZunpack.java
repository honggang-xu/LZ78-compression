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

			//int as the buffer to store the byte
			int datum = 0;
			//int used for masking
			int mask = 0;
			//int for counting phrase entries
			int phraseNumValue = -1;
			//int indicating the available bits
			int availableBits = 32;
			//index value
			int index = 0;
			//mismatched byte as int
			int phrase = 0;
			//int for reading input
			int i;
			//flag indicating whether there is input
			boolean flag = true;
			
			//fill the buffer with 32 bits if there is 32 bits in input
			while (availableBits != 0)
			{
				//if there is input, read one byte
				if ((i = inputs.read()) != -1)
				{
					
					int nextByte = i;
					//shift the 8 bits to the very left next to the previous bits
					nextByte <<= (availableBits - 8);
					//or in buffer
					datum |= nextByte;
					//decrement the available bits
					availableBits -= 8;
				}
				//else there is no input
				else
				{
					//while there is 8 bits to output
					while ((32 - availableBits) > 8)
					{
						//increment counter for number of phrase read
						phraseNumValue++;
						//bits for the phrase number
						int phraseNumBits = Integer.toBinaryString(phraseNumValue).length();

						//set up the mask 
						mask = -1;
						mask <<= (32 - phraseNumBits);
						//get the phrase number value
						index = datum & mask;
						//shift the buffer
						datum <<= phraseNumBits;
						//right shift the phrase number padded with leading 0 in an int
						index >>>= 32 - phraseNumBits;
						//increment the available bits
						availableBits += phraseNumBits;

						//set up the mask
						mask = 255;
						mask <<= 24;
						//get the mismatched byte as an int
						phrase = datum & mask;
						//shift the buffer
						datum <<= 8;
						//right shift the mismatched byte padded with leading 0 in an int
						phrase >>>= 24;
						//increment the available bits
						availableBits += 8;
						//write the phrase number and mismatched byte to the output
						writer.write(index + " " + (byte)phrase);
						writer.newLine();
					}
					availableBits = 0;
				}
			}
			//while there is input
			while (flag)
			{
				//increment counter for number of phrase read
				phraseNumValue++;
				//bits for the phrase number
				int phraseNumBits = Integer.toBinaryString(phraseNumValue).length();
				//set up the mask
				mask = -1;
				mask <<= (32 - phraseNumBits);
				//get the phrase number value
				index = datum & mask;
				//shift the buffer
				datum <<= phraseNumBits;
				//right shift the phrase number padded with leading 0 in an int
				index >>>= 32 - phraseNumBits;
				//increment the available bits
				availableBits += phraseNumBits;
				
				//set up the mask
				mask = 255;
				mask <<= 24;
				//get the mismatched byte as an int
				phrase = datum & mask;
				//shift the buffer
				datum <<= 8;
				//right shift the mismatched byte padded with leading 0 in an int
				phrase >>>= 24;
				//increment the available bits
				availableBits += 8;
				//write the phrase number and mismatched byte to the output
				writer.write(index + " " + (byte)phrase);
				writer.newLine();

				//while there is next input and has 8 bits available
				while (availableBits >= 8)
				{
					//if there is input, read one byte and stor it into buffer
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
						//while there is 8 bits to output in the buffer, output them
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

							//if there is mismatched byte
							if (phrase != 0)
							{
								writer.write(index + " " + (byte)phrase);
								writer.newLine();
							}
							//else there is no mismatched byte, just output the phrase number
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