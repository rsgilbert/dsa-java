package priorityqueue;


import java.util.Comparator;

class StringLengthComparator implements Comparator<String> {
    public int compare(String a, String b) {
        if(a.length() < b.length()) return -1;
        if(a.length() == b.length()) return 0;
        else return 1;
    }
}


public class Comparing {
}
