import java.util.Comparator;
import java.util.Objects;

public class WordData{
	private String word;
	private int count;
	
	public WordData(String word)
	{
		this.word = word;
		count = 1;
	}
	public WordData()
	{
		this(null);
	}
	public String getWord()
	{
		return word;
	}
	public void incrementCount()
	{
		count++;
	}
	public int getCount()
	{
		return count;
	}
	@Override
	public String toString() {
		return "WordData [word=" + word + ", count=" + count + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordData other = (WordData) obj;
		return Objects.equals(word, other.word);
	}	
}
