package maps;

import java.util.Scanner;

public class UseUnsortedTableMap {
    public static void main(String[] args) {
        /**
         * Test the implementation of UnsortedTableMap by using it
         */

        // Test by performing wordCount using UnsortedTableMap
        wordCount(new UnsortedTableMap<>());

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

        System.out.println("The most frequent word is " + maxWord);
        System.out.println("Max count is " + maxCount);
    }
}
