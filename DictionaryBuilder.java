import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DictionaryBuilder {
	private GenericLinkedList<WordData>[] arr;
	private int loadSize;
	private static double LOAD_FACTOR;

	public DictionaryBuilder(int estimatedEntries) {
		arr = new GenericLinkedList[estimatedEntries];
		loadSize = estimatedEntries;
	}

	public DictionaryBuilder(String filename) throws FileNotFoundException{
		Path path = Paths.get(filename);
		try {
			int size = (int) Files.size(path); //file size = estimated unique words/0.6
			loadSize = size / 60; //final size
			for (int i = 0; i < loadSize; i++) {
				if ((4 * i + 3) >= loadSize) // include a comment about how you calculated the number of unique words etc.
				{
					loadSize = 4 * i + 3;
					break;
				}
			}
			LOAD_FACTOR = loadSize/size;
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		arr = new GenericLinkedList[loadSize];

		try (java.util.Scanner fileScanner = new java.util.Scanner(new java.io.File(filename))) {
			while (fileScanner.hasNextLine()) {
				String line = fileScanner.nextLine().trim();
				System.out.println("Line: " + line);

				// Skip blank lines
				if (line.isEmpty()) {
					continue;
				}

				// Split line into username and encrypted password
				String[] parts = line.split(" ");
				for (int i = 0; i < parts.length; i++) {
					System.out.println("Word: " + parts[i]);
					addWord(parts[i]);
				} // consider try catch
			}
		} catch (java.io.FileNotFoundException e) {
			throw new FileNotFoundException();
		}
	}

	public void addWord(String word) {
		String newWord = word.toLowerCase();
		String endWord = newWord.replaceAll("\\p{Punct}", "");

		int num = endWord.hashCode() % loadSize;
		if (num < 0)
			num *= -1;
		if (arr[num] == null) {
			arr[num] = new GenericLinkedList<>();
			arr[num].addLast(new WordData(endWord));
			return;
		}
		for (WordData w : arr[num]) {
			if (w.getWord().equals(endWord)) {
				w.incrementCount();
				return;
			}
		}
		arr[num].addLast(new WordData(endWord));

	}

	public int getFrequency(String word) {
		String newWord = word.toLowerCase();
		String endWord = newWord.replaceAll("\\p{Punct}", "");
		int num = endWord.hashCode() % loadSize;
		if (num < 0)
			num *= -1;
		if (arr[num] == null)
			return 0;
		for (WordData w : arr[num]) {
			if (w.getWord().equals(endWord))
				return w.getCount();
		}
		return 0;
	}

	public void removeWord(String word) throws DictionaryEntryNotFoundException {
		String newWord = word.toLowerCase();
		String endWord = newWord.replaceAll("\\p{Punct}", "");
		int num = endWord.hashCode() % loadSize;
		if (num < 0)
			num *= -1;
		if (arr[num] == null || !arr[num].contains(new WordData(endWord)))
			throw new DictionaryEntryNotFoundException("Word is not found.");
		if (arr[num].size() == 1) {
			arr[num] = null;
			return;
		}
		arr[num].remove(new WordData(endWord));

	}

	public ArrayList<String> getAllWords() // should return arraylist
	{
		ArrayList<String> results = new ArrayList<>(); // is this an issue
		for (int i = 0; i < loadSize; i++) {
			if (arr[i] != null) {
				for (int j = 0; j < arr[i].size(); j++) {
					results.add(arr[i].get(j).getWord());
				}
			}
		}
		Collections.sort(results);
		return results;
	}
	public double getLoadFactor()
	{
		return LOAD_FACTOR;
	}
}
