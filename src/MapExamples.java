import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class MapExamples {
  final static int HIGH = 1000;
  final static int LOWRANGE = 90;
  final static int HIGHRANGE = 100;
  
  public static void main(String[] args) {
    // birthDayExample();
    wordCountExample();
  }

  private static void birthDayExample() {
    Map<String, Integer> m = new HashMap<String, Integer>();
    m.put("Newton", 1642);
    m.put("Darwin", 1809);
    System.out.println(m);

    Set<String> keys = m.keySet();
    Iterator<String> itr = keys.iterator();
    while (itr.hasNext()) {
      String key = itr.next();
      System.out.println(key + " => " + m.get(key));
    }
  }

  private static void wordCountExample() {
    // Read form file
    Scanner in = readFile("kingarthur.txt");
    Map<String, Integer> countMap = getCountMap(in);
    reportOccurrences(countMap);
    reportReverseCounts(countMap);
  }

  private static void reportOccurrences(Map<String, Integer> countMap) {

    System.out.printf("\n***nWords that occur more than %d times.%n", HIGH);
    for (String word : countMap.keySet()) {
      int count = countMap.get(word);
      if (count > HIGH) {
        System.out.println(word + " occurs " + count + " times.");
      }
    }
    System.out.printf("\n****Words that occur %d to %d times%n", LOWRANGE, HIGHRANGE);
    for (String word : countMap.keySet()) {
      int count = countMap.get(word);
      if (count >= LOWRANGE && count <= HIGHRANGE) {
        System.out.println(word + " occurs " + count + " times.");
      }
    }

    System.out.println("\n***Other Stats:\n");
    System.out.printf("Camelot occurs %d times", countMap.get("camelot"));
    System.out.printf("%nWord that occurs most often: %s%n", findMaxOccurence(countMap));
  }

  private static Scanner readFile(String name) {
    Scanner in = null;
    try {
      in = new Scanner(new File(name));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return (in);
  }

  private static String findMaxOccurence(Map<String, Integer> map) {
    Map.Entry<String, Integer> maxEntry = null;
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
        maxEntry = entry;
      }
    }
    return (maxEntry != null) ? maxEntry.getKey() : "none";
  }

  // Store each word we encounter
  public static Map<String, Integer> getCountMap(Scanner in) {
    Map<String, Integer> wCountMap = new TreeMap<String, Integer>();
    while (in.hasNext()) {
      String word = in.next().toLowerCase();
      if (!wCountMap.containsKey(word)) {
        // never seen this word before
        wCountMap.put(word, 1);
      } else {
        // seen this word before; increment count
        int count = wCountMap.get(word);
        wCountMap.put(word, count + 1);
      }
    }
    return wCountMap;
  }
  
  // What words occur in ranges of frequencies?
  private static void reportReverseCounts(Map<String, Integer> map) {
    Map<Integer, ArrayList<String>> reverseMap = new HashMap<Integer, ArrayList<String>>();
    for (int i=1; i<30; i++) {
      int lowRange = i * 100 + 500;;
      int highRange = (i+1)*100 + 500;
      
      for (Map.Entry<String, Integer> entry : map.entrySet()) {
        if (entry.getValue() > lowRange && entry.getValue() <= highRange) {
          String word = entry.getKey();
          if (!reverseMap.containsKey(lowRange)) {
            ArrayList<String> firstList = new ArrayList<String>();
            firstList.add(word);
            reverseMap.put(lowRange, firstList);
          } else {
            reverseMap.get(lowRange).add(word);
          }
        }
      }
    }
    System.out.println(reverseMap);
  }
}
