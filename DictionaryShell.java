import java.util.ArrayList;
import java.util.Scanner;

public class DictionaryShell {

	public static void main(String args[]) {
		// commands to allow search add delete list stats and exit
		int length = 1000; // we'll set to filename.length() when times comes
		// filename.length();// whatever filename is
		int size = (int) ((length / 100) / 0.6);
		for (int i = 0; i < size; i++) {
			if ((4 * i + 3) >= size) // include a comment about how you calculated the number of unique words etc.
			{
				size = 4 * i + 3;
				break;
			}
		}
		String filename = null;
		if(args.length == 1)
			filename = args[0];

		DictionaryBuilder dictionary;
		if (filename == null)
			dictionary = new DictionaryBuilder(size); //default size
		else
			dictionary = new DictionaryBuilder(filename);

		System.out.println("Welcome to the Dictionary Builder CLI.");
		System.out.println("Available comman ds: add <word>, delete <word>, search <word>, list, stats, exit");

		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				String prompt = " ";
				prompt = sc.nextLine();
				String keyword;
				String arg = "";
				if (prompt.contains(" ")) {
					keyword = prompt.substring(0, prompt.indexOf(' '));
					arg = prompt.substring(prompt.indexOf(' ') + 1);
				} else
					keyword = prompt;
				if (keyword.equals("add")) {
					dictionary.addWord(arg);
					System.out.println("\"" + arg + "\" added.");
				} else if (keyword.equals("delete")) {
					try {
						dictionary.removeWord(arg);
					} catch (DictionaryEntryNotFoundException e) {
						System.out.println(e.getMessage());
					}
					System.out.println("\"" + arg + "\" deleted.");
				} else if (keyword.equals("search")) {
					if (dictionary.getFrequency(arg) > 0)
						System.out.println(dictionary.getFrequency(arg) + " instance(s) of \"" + arg + "\" found."); // check
					else
						System.out.println("\"" + arg + "\" not found.");

				} else if (keyword.equals("list")) {
					//System.out.println("Going through list");
					ArrayList<String> results = dictionary.getAllWords();
					for (String r : results) {
						System.out.println(r);
					}
				} else if (keyword.equals("stats")) {
					int totalWords = 0;
					ArrayList<String> results = dictionary.getAllWords();
					for (String r : results) {
						totalWords += dictionary.getFrequency(r);
					}
					System.out.println("Total words: " + totalWords);
					System.out.println("Total unique words: " + dictionary.getAllWords().size());
					System.out.println("Estimated load factor: " + dictionary.getLoadFactor());// what is this

				} else if (keyword.equals("exit")) {
					System.out.println("Quitting...");
					sc.close();
					return;
				} else {
					System.out.println("Not valid");
					return;
				}
			}
		}

	}
}
