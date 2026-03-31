import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DictionaryBuilderStudentTests {

	private DictionaryBuilder db;

	@BeforeEach
	public void setUp() {
		db = new DictionaryBuilder(11); // small estimate
	}

	// === Constructor ===

	@Test
	public void constructor_initializesDictionary() {
		assertNotNull(db);
	}

	// === addWord ===

	@Test
	public void addTwoSimilarWords_shouldBeDistinct() {
		db.addWord("search");
		db.addWord("hcraes");
		assertEquals(1, db.getFrequency("search"));
		assertEquals(1, db.getFrequency("hcraes"));
	}

	public void addTwoVerySimilarWords_shouldBeDistinct() {
		db.addWord("search");
		db.addWord("searchsearch");
		assertEquals(1, db.getFrequency("search"));
		assertEquals(1, db.getFrequency("hcares"));
	}

	// === getFrequency ===

	@Test
	public void getFrequency_wordPresent_shouldBeOne() {
		db.addWord("AaAa");
		db.addWord("AaBB");
		assertEquals(1, db.getFrequency("AaAa"));
	}

	@Test
	public void getFrequency_afterAddingAndRemovingandAdding() throws Exception {
		db.addWord("pear");
		db.addWord("pear");
		db.removeWord("pear");
		db.addWord("pear");
		assertEquals(1, db.getFrequency("pear"));
	}

	// === getAllWords ===

	@Test
	public void test09_getAllWords_shouldReturnSortedList() {
		db.addWord("BBAa");
		db.addWord("AaBB");
		db.addWord("AaAa");
		db.addWord("CC");
		List<String> result = db.getAllWords();
		assertEquals(List.of("aaaa", "aabb", "bbaa", "cc"), result);
	}

	// === removeWord ===

	@Test
	public void removeWord_shouldRemoveCompletely() throws Exception {
		for (int i = 0; i < 10; i++) {
			db.addWord("kiwi");
		}

		db.removeWord("kiwi");
		assertEquals(0, db.getFrequency("kiwi"));
	}

}
