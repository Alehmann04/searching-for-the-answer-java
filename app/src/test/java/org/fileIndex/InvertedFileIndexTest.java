package org.fileIndex;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

class InvertedFileIndexTest {

  @Test
  void itReadsAndMapsTextFiles() {
    InvertedFileIndex inverted_file_index = new InvertedFileIndex(
      "../testing_texts/the_eagle_and_the_fox.txt"
    );
    TreeMap<String, ArrayList<Integer>> map =
      inverted_file_index.extractFileToIndexMap();
    ArrayList<Integer> expected = new ArrayList<>();
    expected.add(0);
    assertEquals(expected, map.get("an"));
  }
}
