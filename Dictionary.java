

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

public class Dictionary {
		
		Set <String> words;
		Map <String, List<Integer>> wordsAndLines;	
		List <String> lines;

	    /**
	     * Constructs a Dictionary from words provided by a TokenScanner.
	     * <p>
	     * A word is any sequence of letters (see Character.isLetter) or apostrophe characters. All
	     * non-word tokens provided by the TokenScanner should be ignored.
	     * <p>
	     *
	     * @param ts Sequence of words to store in this Dictionary
	     * @throws IOException 
	     * @throws IllegalArgumentException If the provided token scanner is null
	     */
	    public Dictionary(TokenScanner ts, String filename) throws IOException {
	    	if (ts == null) {
	    		throw new IllegalArgumentException("TokenScanner is null");
	    	}
	    	else {
	    	    int lineIndex = 0;
	    	    String line = "";
	    		words = new TreeSet<String>();
	    		wordsAndLines = new TreeMap<String, List<Integer>>();
//	    		lines = new ArrayList<String>();
	    		while (ts.hasNext()) {
	    			String current = ts.next();
	    			if (current.contains("\n")) {
//	    			    System.out.println("LINE " + lineIndex + " says: " + line);
//	    			    lines.add(line);
	    			    lineIndex++;
//	    			    line = "";
	    			}
//	    			line += " " + current.trim();
//	    			System.out.println(current + " is at line " + lineIndex);
	    			if (current.length() > 2 && TokenScanner.isWord(current)) {
	    			    String dictionaryWord = current.toLowerCase();
	        			words.add(dictionaryWord);
	        			if (wordsAndLines.get(dictionaryWord) == null) {
	        			    List<Integer> currentLines = new ArrayList<Integer>();
	        			    currentLines.add(lineIndex);
	        			    wordsAndLines.put(dictionaryWord, currentLines);
	        			} else {
	        			    wordsAndLines.get(dictionaryWord).add(lineIndex);
	        			}
	    			}
	    		}
	            this.setupLines(filename);
//	    		lines.add(line);
	    	}
	    	for (String line : lines) {
//	    	    System.out.println(line);
	    	}
//	    	System.out.println(lines.size());
	    }
	    
	    
	    public void setupLines(String filename) {
	        try {
	            lines = new ArrayList<String>();
	            BufferedReader reader = new BufferedReader(new FileReader(filename));
                String line = reader.readLine();
                while (line != null) {
                    lines.add(line);
                    line = reader.readLine();
                }
                reader.close();
            } catch (Exception e) {
            }
	        
	    }

	    /**
	     * Returns an instance of a Dictionary constructed from words from a file.
	     *
	     * @param filename Location of file from which to read words
	     * @return A Dictionary instance with words from the argued file
	     * @throws FileNotFoundException If the file does not exist
	     * @throws IOException If error while reading
	     */
	    public static Dictionary make(String filename, String filename2) throws IOException {
	        Reader r = new FileReader(filename);
	        Dictionary d = new Dictionary(new TokenScanner(r), filename2);
	        r.close();
	        return d;
	     }

	    /**
	     * Returns the number of unique words in this Dictionary. This count is case insensitive: 
	     * if
	     * both "DOGS" and "dogs" appeared in the input file, it must only be counted once in the 
	     * sum.
	     * 
	     * @return Number of unique words in the dictionary
	     */
	    public int getNumWords() {
	        return words.size();
	    }

	    /**
	     * Tests whether the argued word is present in this Dictionary. Note that strings containing
	     * nonword characters (such as spaces) will not be in the Dictionary. If the word is not 
	     * in the
	     * Dictionary or if the word is null, this method returns false.
	     * 
	     * <p>
	     * This check should be case insensitive. For example, if the Dictionary contains 
	     * "dog" or "dOg"
	     * then isWord("DOG") should return true.
	     * <p>
	     * Calling this method must not re-open or re-read the source file.
	     *
	     * @param word A String token to check. Assume any leading or trailing whitespace has 
	     * already
	     *             been removed.
	     * @return Whether the word is in the dictionary
	     */
	    public boolean isWord(String word) {
	    	if (word == null || !TokenScanner.isWord(word)) {
	    		return false;
	    	}
	        return words.contains(word.toLowerCase());
	    }
	    
	    public void printWords() {
	        for (String word: words) {
	            System.out.println(word);
	        }
	    }
	    
	       public boolean hasLines(String word) {
	            return wordsAndLines.containsKey(word.toLowerCase());
	        }
	       
           public List<Integer> getLineList(String word) {
               return wordsAndLines.get(word.toLowerCase());
           }
	    
	    
	    public int getLineIndex(String word) {
	        List<Integer> lineList = wordsAndLines.get(word.toLowerCase());
	        Random randomGenerator = new Random();
	        int randomIndex = randomGenerator.nextInt(lineList.size());
	        System.out.println("word is " + word);
	        System.out.println(lineList == null);
	        int randomLine = lineList.get(randomIndex);
	        return randomLine;
	    }
	    
	    public String getFirstLine(int randomLine) {
            if (randomLine % 2 == 0) {
                return lines.get(randomLine);
            } else {
                return lines.get(randomLine - 1);
            }
        }
	    
	       public String getSecondLine(int randomLine) {
	            if (randomLine % 2 == 0) {
	                return lines.get(randomLine + 1);
	            } else {
	                return lines.get(randomLine);
	            }
	        }
	    
	    
	    public void playLine(int randomLine) {
            String soundName = "files/Chaucer - " + randomLine + ".wav";
            if (randomLine % 2 != 0) { 
            soundName = "files/Chaucer - " + (randomLine - 1) + ".wav";
            }
            Clip clip = null;
            try {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (Exception e) {

            }

            clip.start();
	    }

	       public void printWordsAndLines() {
	            for (Map.Entry<String, List<Integer>> entry : wordsAndLines.entrySet()) {
	                System.out.print(entry.getKey() + " at lines: ");
	                for (Integer line : entry.getValue()) {
	                    System.out.print(line + ", ");
	                }
	                System.out.println("");
	            }
	        }
	}

