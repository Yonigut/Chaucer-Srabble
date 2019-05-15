

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class models the bag of letter tiles that exists in a real Scrabble game. Here,
 * the object stores an ArrayList of Strings, where each string represents a distinct
 * tile in the bag. There are methods provided to take a letter and two exchange for use during
 * the gameplay.
 */
public class Bag {
	
	private List<String> letters;

    /**
     * Constructs a Bag by reading the file containing all the letters.
     *
     * @param none
     * @return none
     */
	public Bag() {
		try {
			letters = new ArrayList<String>();
			Reader in = new FileReader("files/Scrabble_Letters.txt"); 
			BufferedReader br = new BufferedReader(in);
			boolean hasNext = true;
			while (hasNext) {
				String line = br.readLine();
        		if (line == null) {
        			hasNext = false;
        		} else {
        		letters.add(line);
        		}
			}
			br.close();
		} catch (IOException e) {
			// do nothing
		}
		letters.remove("k");
		letters.remove("n");
		letters.remove("y");
		letters.remove("g");
		letters.remove("h");
		letters.remove("t");
	    letters.remove("p");
	    
	    letters.remove("s");
        letters.remove("e");
        letters.remove("n");
        letters.remove("d");
        letters.remove("e");
        letters.remove("t");
        letters.remove("w");	    
	}
	
    /**
     * Removes a letter from the Arraylist of letters, allowing for a tile to be "taken."
     *
     * @return a String that is the taken letter if the Bag has letters and an empty string 
     * otherwise.
     */
	public String take() { 
		if (noMoreTiles()) {
			return " ";
		}
		int randomIndex = getRandom();
		String randomLetter = letters.get(randomIndex);
		letters.remove(randomIndex);
		return randomLetter;
	}
	
    /**
     * This is a private helper function that is used get a random index used for taking.
     *
     * @return an int, that is a random index within the ArrayList
     */
	private int getRandom() {
		return ((int)(Math.random() * (letters.size())));
	}
	
    /**
     * This method allows for a letter to be exchanged for a different one from the bag. It adds
     * the old letter to the ArrayList and removes the other to be returned.
     *
     * @param oldWord, a string that is the old letter being returned to the bag
     * @return a String, the new letter from the bag
     */
	public String exchange(String oldWord) {
		letters.add(oldWord);
		return take();
	}

    /**
     * This method checks if there are any letters left in the bag by checking if the ArrayList
     * is empty.
     *
     * @return a boolean that represents whethere or not the ArraList is empty
     */
	public boolean noMoreTiles() {
		return letters.isEmpty();
	}
}
