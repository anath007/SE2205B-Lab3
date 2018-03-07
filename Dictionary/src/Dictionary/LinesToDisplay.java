package Dictionary;

import java.util.Iterator;
import javafx.beans.binding.Bindings;


/**
 * A class that will be used to display the lines of text that are corrected.
 *
 */
public class LinesToDisplay {

    public static final int LINES = 10;     // Display 10 lines
    private AList<Wordlet>[] lines;
    private int currentLine;

    /**
     * Constructor for objects of class LinesToDisplay
     */
    public LinesToDisplay() {
        //ADD CODE FOR THE CONSTRUCTOR
        
        currentLine = 0;
        lines = (AList<Wordlet>[]) new AList[LINES+1];
        for(int i =0;i<=LINES;i++){
            lines[i] = new AList<Wordlet>();
        }
    }

    /**
     * Add a new wordlet to the current line.
     *
     */
    public void addWordlet(Wordlet w) {
        //ADD CODE HERE TO ADD A WORDLET TO THE CURRENT LINE
        lines[currentLine].add(w);
    }

    /**
     * Go to the next line, if the number of lines has exceeded LINES, shift
     * them all up by one
     *
     */
    public void nextLine() {
        //ADD CODE TO HANDLE THE NEXT LINE
        
        if(currentLine == LINES-1){
            for(int i = 0;i<10;i++)
            {
                
                lines[i]= lines[i+1];
            }
            lines[currentLine] = new AList<Wordlet>();
        }
        else currentLine++;}

      
    public int getCurrentLine(){
        return currentLine;
    }
    
    public AList<Wordlet>[] getLines(){
        return lines;
    }
}
