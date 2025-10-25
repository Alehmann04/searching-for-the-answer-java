package org.fileIndex;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvertedFileIndex {

  private String path;

  public InvertedFileIndex() {
    this.path = "";
  }

  public InvertedFileIndex(String path_string) {
    this.path = path_string;
  }

  public String toString() {
    return this.toString(this.path);
  }

  public String toString(String path_string) {
    TreeMap<String, ArrayList<Integer>> index_map =
      this.extractFileToIndexMap(path_string);
    return this.formatMapAsString(index_map);
  }

  public void print() {
    this.print(this.path);
  }

  public void print(String path_string) {
    TreeMap<String, ArrayList<Integer>> index_map =
      this.extractFileToIndexMap(path_string);
    System.out.println(this.formatMapAsString(index_map));
  }

  public void writeToFile() {
    this.writeToFile(this.path);
  }

  public void writeToFile(String path_string) {
    TreeMap<String, ArrayList<Integer>> index_map =
      this.extractFileToIndexMap(path_string);
    String indexString = this.formatMapAsString(index_map);
    String indexpath_string = path_string.replaceAll(
      "\\.txt",
      "-inverted_file_index.txt"
    );
    File fileIndexTarget = new File(indexpath_string);
    try (FileWriter writer = new FileWriter(fileIndexTarget)) {
      writer.write(indexString);
      System.out.println(
        "Index written to the file successfully.\nWriten to: " +
        indexpath_string
      );
    } catch (FileNotFoundException e) {
      System.err.println(
        "An error occurred while reading the file: " + e.getMessage()
      );
      return;
    } catch (IOException e) {
      System.err.println(
        "An error occurred while writing" + " to the file: " + e.getMessage()
      );
    }
  }

  public TreeMap<String, ArrayList<Integer>> extractFileToIndexMap() {
    return this.extractFileToIndexMap(this.path);
  }

  public TreeMap<String, ArrayList<Integer>> extractFileToIndexMap(
    String path_string
  ) {
    String raw_text = this.extractTextFromFile(path_string);
    String filtered_text = this.filterText(raw_text);
    TreeMap<String, ArrayList<Integer>> inverted_file_index_map =
      this.mapIndexes(filtered_text);
    return inverted_file_index_map;
  }

  private String extractTextFromFile(String path_string) {
    File file_target = new File(path_string);
    String data = "";
    try (Scanner reader = new Scanner(file_target)) {
      while (reader.hasNextLine()) {
        data += reader.nextLine() + " "; // the " " is to prevent double returns from smashing lines together
      }
    } catch (FileNotFoundException e) {
      System.err.println(
        "An error occurred while reading the file." +
        " the file: " +
        e.getMessage()
      );
      e.printStackTrace();
    }
    return data;
  }

  private String filterText(String raw_text) {
    // all to lowercase
    String lowercase_text = raw_text.toLowerCase();
    // replace interchangable chars
    String prossesing_text = lowercase_text.replaceAll("’", "'");
    prossesing_text = prossesing_text.replace("—", "-");
    // remove unexpected chars
    Pattern unexpected_char_pattern = Pattern.compile(
      "[^a-z '-]",
      Pattern.CASE_INSENSITIVE
    );
    Matcher unexpected_char_matcher = unexpected_char_pattern.matcher(
      prossesing_text
    );
    String alphanumeric_text = unexpected_char_matcher.replaceAll("");
    // ensure only 1 space between words
    Pattern multiwhitespace_pattern = Pattern.compile("[ ]+");
    Matcher multiwhitespace_matcher = multiwhitespace_pattern.matcher(
      alphanumeric_text
    );
    String filtered_text = multiwhitespace_matcher.replaceAll(" ");
    return filtered_text;
  }

  private TreeMap<String, ArrayList<Integer>> mapIndexes(String text) {
    TreeMap<String, ArrayList<Integer>> index_map = new TreeMap<>();
    String[] words = text.split(" ");
    for (int i = 0; i < words.length; i++) {
      String word = words[i];
      Pattern pattern = Pattern.compile(
        "^(a)$|^(and)$|^(as)$|^(at)$|^(but)$|^(by)$|" +
        "^(for)$|^(he)$|^(he)$|^(her)$|^(his)$|" +
        "^(in)$|^(in)$|^(is)$|^(it)$|^(not)$|^(of)$|" +
        "^(on)$|^(our)$|^(she)$|^(that)$|^(the)$|^(the)$|" +
        "^(they)$|^(they)$|^(to)$|^(us)$|^(was)$|^(were)$|^(you)$|^(your)$"
      );
      Matcher matcher = pattern.matcher(word);
      if (!matcher.find()) {
        if (index_map.get(word) != null) {
          ArrayList<Integer> indexes = index_map.get(word);
          indexes.add(i);
          index_map.put(word, indexes);
        } else {
          ArrayList<Integer> indexes = new ArrayList<>();
          indexes.add(i);
          index_map.put(word, indexes);
        }
      }
    }

    return index_map;
  }

  private String formatMapAsString(
    TreeMap<String, ArrayList<Integer>> index_map
  ) {
    String output = "{\n";
    for (Map.Entry<String, ArrayList<Integer>> entry : index_map.entrySet()) {
      output += "   " + entry.getKey();
      output += " : " + entry.getValue() + ",\n";
    }
    output += "}";
    return output;
  }

  private String formatMapAsPrettyString(
    TreeMap<String, ArrayList<Integer>> index_map
  ) {
    String output = "{\n";
    int longest = 0;
    for (String key : index_map.keySet()) {
      if (key.length() > longest) {
        longest = key.length();
      }
    }
    for (Map.Entry<String, ArrayList<Integer>> entry : index_map.entrySet()) {
      output += "   " + entry.getKey();
      for (int i = entry.getKey().length(); i < longest; i++) {
        output += " ";
      }
      output += " : " + entry.getValue() + ",\n";
    }

    output += "}";
    return output;
  }
}
