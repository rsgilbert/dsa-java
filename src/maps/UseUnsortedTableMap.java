package maps;

import java.util.Scanner;

public class UseUnsortedTableMap {
    public static void main(String[] args) {
        // Test by performing wordCount using UnsortedTableMap
       // wordCount(new UnsortedTableMap<>());

        // Test ChainHashMap using wordCount
//        System.out.println("*** ChainHashMap ***");
//        wordCount(new ChainHashMap<>());

        // Test ProbeHashMap using wordCount
//        System.out.println("*** ProbeHashMap ***");
//        wordCount(new ProbeHashMap<>());

        // Test SortedTableMap using wordCount
        System.out.println("*** SortedTableMap ***");
        wordCount(new SortedTableMap<>());
    }

    public static void wordCount(Map<String, Integer> f) {
        Map<String, Integer> freq = f;
        // Scan input for words, using all non-letters as delimiters
        // Scanner doc = new Scanner(System.in).useDelimiter("[^a-zA-Z]+");
        String input = "My name is my name what's your name welcome to my town.";
        Scanner doc = new Scanner(input).useDelimiter("[^a-zA-Z]+");
        int i = 0;
        String word = "";
        while (doc.hasNext()) {
            word = doc.next().toLowerCase().trim();
            Integer count = freq.get(word);
            if (count == null)
                count = 0;

            freq.put(word, count + 1);
            System.out.println(word + " ** " + freq.get(word));
        }
        int maxCount = 0;
        String maxWord = "no word";
        for (Entry<String, Integer> ent : freq.entrySet())
            if (ent.getValue() > maxCount) {
                maxCount = ent.getValue();
                maxWord = ent.getKey();
            }

        System.out.println("The most frequent word is \"" + maxWord + "\"");
        System.out.println("Max count is: " + maxCount);
    }
}
