package com.company;

import java.io.*;
import java.util.*;

/**
 * This program was created for the HEB Developer - I coding challenge.
 *
 * This class will create a histograph from a text file passed in via command line argument
 *
 */
public class MakeHistograph{
  static final String OUTFILE = "output.txt";

  public static void main(String[] args)
  {
    // verify only one input file
    if (args.length != 1) {
      System.out.println("\nUsage: java MakeHistograph <path_to_input_file>\n");
      System.exit(1);
    }

    // Use path to create a file object
    System.out.println("Opeining File ");
    File inFile = new File(args[0]);

    // generate hashmap of unique words and frequencies
    System.out.println("Generating hashmap ");
    HashMap<String, MutableInt> wordMap = readAndHashFile(inFile);

    // convert hashmap to descending sorted list
    System.out.println("Sorting hashmap into list ");
    List<Map.Entry<String, MutableInt> > sortedCount = sortByValue(wordMap);

    System.out.print("Writing to file ");
    displayHistograph(sortedCount);

    System.out.println("Done.");
  }

  /**
   * Method readFile accepts a File object containing text and will store a key/value pair containing each
   * unique word and its corresponding count
   *
   * @param f File object
   * @return HashMap<String, MutableInt> contining the unique words and frequencies
   */
  public static HashMap<String, MutableInt> readAndHashFile(File f)
  {
    String readline;
    HashMap<String, MutableInt> map = new HashMap<>();

    try (BufferedReader br = new BufferedReader(new FileReader(f))) {
      while ((readline = br.readLine()) != null) {
        String[] words = readline.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");

        // Skip any blank lines
        if ((words.length == 0 || words[0].length() == 0) ) { continue; }

        // Create new MutableInt or retrieve the current value, then increment value
        for (String word : words) {
          map.compute(word, (key, value) -> value == null ? new MutableInt() : value).increment();
        }
      }
    } catch (Exception e) {
      System.out.println("ERROR while reading file");
      e.printStackTrace();
      System.exit(1);
    }
    return map;
  }

  /**
   * Method sortByValue accepts a HashMap<String, MutableInt> and will sort the hashmap by values stored in a list
   * of Map.Entry<String, Mutable>
   *
   * @param map <code>HashMap<String, MutableInt> </code>is a hashmap of the words
   * @return List of Map.Entry<String, Mutable> containing the sorted key/value pairs
   */

  private static List<Map.Entry<String, MutableInt> > sortByValue(HashMap<String, MutableInt> map)
  {
    // new list of elements from the unsorted map
    List<Map.Entry<String, MutableInt> > entryList = new LinkedList<>(map.entrySet());

    //Collections.sort(entryList, (o1, o2) -> (o2.getValue().compareTo(o1.getValue())));
    entryList.sort((o1, o2) -> (o1.getValue().compareTo(o2.getValue())));
    Collections.reverse(entryList);
    return entryList;
  }


  /**
   * Method displayHistograph will display the formatted histograph
   *
   * @param <<code>List<Map.Entry<String, MutableInt> > </code> a list of entries from the hashmap
   */
  public static void displayHistograph(List<Map.Entry<String, MutableInt> > sortedCount)
  {
    StringBuilder outString = new StringBuilder();
    for (Map.Entry<String, MutableInt> count : sortedCount) {
      String word = String.format("%10s", count.getKey());
      int freq = count.getValue().getCount();
      outString.append(String.format("%10s", count.getKey()) + " | " + repeatChar('=', freq) + " (" + freq + ")\n");

      try {
        writeToFile(outString.toString());
      } catch (IOException e) {
        System.out.println("ERROR writing to file");
        e.printStackTrace();
      }
    }
  }

  /**
   * Method writetoFile
   * @param toFile a String representation of the list to be printed
   * @throws IOException
   */
  public static void writeToFile(String toFile) throws IOException
  {
    FileOutputStream outFile = new FileOutputStream(OUTFILE);
    byte[] strBytes = toFile.getBytes();
    outFile.write(strBytes);
    outFile.close();

  }

  /**
   * Method repeatChar will repeat any single character n times as given via arguments
   *
   * @return string of n chars
   */
  public static String repeatChar(char c, int n)
  {
    StringBuilder str = new StringBuilder();
    for (int i = 0; i < n; i++) {
      str.append(c);
    }
    return str.toString();
  }
}
