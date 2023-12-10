/**
 * This file is part of a solution to a Minesweeper server client
 * This file generates the seeds to be used in testing
 */
package main;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GenerateSeed {
    public static void main(String[] args) throws FileNotFoundException {
        File outfile = new File("Games.seed");
        PrintWriter writer = new PrintWriter(outfile);
        int numberOfTestCases = 10000;
        int numberOfBombsPerTestCase = 16;
        StringBuilder unshuffledSeed = new StringBuilder();
        for (int i = 0; i <256; i++){
            if (i < numberOfBombsPerTestCase)
                unshuffledSeed.append("1");
            else
                unshuffledSeed.append("0");
        }


        for (int i = 0; i < numberOfTestCases; i++){

            writer.println(shuffleString(unshuffledSeed.toString()));
        }
        writer.close();

    }
    public static String shuffleString(String s){
        List<String> list = Arrays.asList(s.split(""));
        Collections.shuffle(list);
        String returnString = "";
        for (String ss: list)
            returnString += ss;
       return returnString;
    }
}
