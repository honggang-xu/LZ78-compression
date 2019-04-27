import java.util.ArrayList;

public class Trie
{
	//iterator used to set index
	private static int iterator = 0;
	//the index value of this trie node
	private int index;
	//the byte value of this trie node
	private byte data;
	//the children of this trie node
	private ArrayList<Trie> children;

	//constructor
	public Trie(byte value)
	{
		index = iterator;
		data = value;
		children = new ArrayList<Trie>();
		iterator++;
	}
	public Trie()
	{
		index = 0;
		data = -1;
		iterator++;
		children = new ArrayList<Trie>();
	}
	
	//return the index of this trie node
	public int getIndex()
	{
		return index;
	}

	//return the byte value of this trie node
	public byte getData()
	{
		return data;
	}

	//find the byte in the children of this trie node
	public Trie find(byte input)
	{
		for (Trie childTrie : children)
		{
			if (childTrie.getData() == input)
			{
				return childTrie;
			}
		}
		return null;
	}

	//add the byte to the children of this trie node
	public void add(byte input)
	{
		Trie newChild = new Trie(input);
		children.add(newChild);
	}
}