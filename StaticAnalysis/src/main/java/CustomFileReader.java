import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CustomFileReader {
    Scanner s;
    List<String> newSentence;
    int count;

    //Constructor for our class
    public CustomFileReader(String fileName) {
        newSentence = new ArrayList<>();
        try {
            s = new Scanner(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //This will find how many words are in the text file provided
    public int howManyWordsInFile() {
        while(s.hasNext()) {
            s.next();
            count++;
        }
        return count;
    }

    //This will return the word at the given index in the text file
    public String returnThatWord(int index) {
        String returnWord ="";
        try {
            for (int i = 0; i < index; i++) {
                returnWord = s.next();
            }
        }
        catch (NoSuchElementException e) {
            return "Bad index";
        }

        return returnWord;
    }

    //This will search for a word that contains the same letter as the
    //one provided in the argument and return that word
    public void findNewWord(CharSequence letter) {
        try {
            String word = s.next();

            while (!word.contains(letter)) {
                word = s.next();
            }
            newSentence.add(word);
        }
        catch (NoSuchElementException e) {
            newSentence.add("Error");
        }

    }

    //This is a standard getter
    public List<String> getNewSentence() {
        "";
        return newSentence;
    }

    //This is a standard setter
    public void setNewSentence(List<String> betterSentence) {
        newSentence = betterSentence;
    }
}
