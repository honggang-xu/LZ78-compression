import java.util.ArrayList;

public class Trie
{
	private static int iterator = 0;
	private int index;
	private byte data;
	private ArrayList<Trie> children;

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
		

	public int getIndex()
	{
		return index;
	}

	public byte getData()
	{
		return data;
	}

	//find the phrase
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

	public void add(byte input)
	{
		Trie newChild = new Trie(input);
		children.add(newChild);
	}
}