package Dictionary;

import static Dictionary.LinesToDisplay.LINES;
import java.io.*;
import static java.nio.file.Files.lines;
import java.util.*;
import javafx.application.Platform;

/**
 * A Thread that contains the application we are going to animate
 *
 */
public class MisspellActionThread implements Runnable {

    DictionaryController controller;
    private final String textFileName;
    private final String dictionaryFileName;

    private LinesToDisplay myLines;
    private DictionaryInterface<String, String> myDictionary;
    private boolean dictionaryLoaded;

    /**
     * Constructor for objects of class MisspellActionThread
     *
     * @param controller
     */
    public MisspellActionThread(DictionaryController controller) {
        super();

        this.controller = controller;
        textFileName = "check.txt";
        dictionaryFileName = "sampleDictionary.txt";

        myDictionary = new HashedMapAdaptor<String, String>();
        myLines = new LinesToDisplay();
        dictionaryLoaded = false;

    }

    @Override
    public void run() {

        // ADD CODE HERE TO LOAD DICTIONARY
        loadDictionary(dictionaryFileName, myDictionary);

        Platform.runLater(() -> {
            if (dictionaryLoaded) {
               controller.SetMsg("The Dictionary has been loaded"); 
            } else {
               controller.SetMsg("No Dictionary is loaded"); 
            }
        });

 
        // ADD CODE HERE TO CALL checkWords
        checkWords(textFileName, myDictionary);
        

    }

    /**
     * Load the words into the dictionary.
     *
     * @param theFileName The name of the file holding the words to put in the
     * dictionary.
     * @param theDictionary The dictionary to load.
     */
    public void loadDictionary(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        try {
            String inString;
            String correctWord;
            input = new Scanner(new File(theFileName));
            String newSpot = "";
            while(input.hasNext())
            {
                newSpot = input.next();
                theDictionary.add(newSpot,"");
                
            } 
            
           dictionaryLoaded=true;

            // ADD CODE HERE TO READ WORDS INTO THE DICTIONARY     

            
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Get the words to check, check them, then put Wordlets into myLines. When
     * a single line has been read do an animation step to wait for the user.
     *
     */
    public void checkWords(String theFileName, DictionaryInterface<String, String> theDictionary) {
        Scanner input;
        StringTokenizer st;
        try {
            String inString;
            String aWord;
            String lineNow;

            input = new Scanner(new File(theFileName));
            
            while(input.hasNextLine()){
            lineNow = input.nextLine();
            st = new StringTokenizer(lineNow,"\t\r\f,.:;?![]()\'\" ",true);
            
            while (st.hasMoreTokens()) {
                aWord=st.nextToken();
                myLines.addWordlet(new Wordlet(aWord, checkWord(aWord, theDictionary)));
                
     } 
           
        showLines(myLines);
        myLines.nextLine();
            }
            
            
            
        } catch (IOException e) {
            System.out.println("There was an error in reading or opening the file: " + theFileName);
            System.out.println(e.getMessage());
        }

    }

    /**
     * Check the spelling of a single word.
     *
     */
    public boolean checkWord(String word, DictionaryInterface<String, String> theDictionary) {
         boolean result = false;
        String [] delim = {".",",",")","!","?","\"","\'",":",";","("};
       
        if(theDictionary.contains(word))
            result = true;
        for(int i =0;i<delim.length;i++){
            if(word.equals(delim[i]))
                result = true;
        }   
        return result;

    }

    private void showLines(LinesToDisplay lines) {
        try {
            Thread.sleep(500);
            Platform.runLater(() -> {
                if (myLines != null) {
                    controller.UpdateView(lines);
                }
            });
        } catch (InterruptedException ex) {
        }
    }

} // end class MisspellActionThread

