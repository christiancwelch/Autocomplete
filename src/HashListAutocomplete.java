
import java.util.*;

public class HashListAutocomplete implements Autocompletor {
    private static final int MAX_PREFIX = 10;
    private Map<String, List<Term>> myMap;
    private int mySize;

    public HashListAutocomplete(String[] terms, double[] weights) {
    if (terms == null || weights == null){
throw new NullPointerException("One or more arguments null");
    }
initialize(terms, weights);
}




    /**
     * Implements the Autocompletor interface, with quicker implementation of
     * topMatches by utilizing a hashlist. Maintains a hashlist containing
     * every possible prefix up to 10 characters, the key being the prefix and
     * the value being a weight sorted list of Term objects with that prefix.
     * Returns the k words in myTerms with the largest weight which match the given prefix,
     * in descending weight order. If less than k words exist matching the given
     * prefix (including if no words exist), then the array instead contains all
     * those words.
     */
    @Override
    public List<Term> topMatches(String prefix, int k) {
        if (myMap.containsKey(prefix)) {
            List<Term> all = myMap.get(prefix);
            List<Term> list = all.subList(0, Math.min(k, all.size()));
            return list;
}
    return new ArrayList<>();
}
    @Override
    public void initialize(String[] terms, double[] weights) {
        myMap = new HashMap<>();
        for (int i = 0; i < terms.length; i++) {
            String word = terms[i];
            for (int q = 0; q < Math.min(word.length(), MAX_PREFIX) + 1; q++) {
                String x = word.substring(0, q);
                Term add = new Term(terms[i], weights[i]);
                myMap.putIfAbsent(x, new ArrayList<>());
                myMap.get(x).add(add);
}
}
for (String s : myMap.keySet()) {
Collections.sort(myMap.get(s), Comparator.comparing(Term::getWeight).reversed());
}
}
@Override
public int sizeInBytes() {
if (mySize == 0) {
for (String s : myMap.keySet()) {
mySize += BYTES_PER_CHAR * s.length();
List<Term> term = myMap.get(s);
for (int j = 0; j < term.size(); j++) {
mySize += BYTES_PER_CHAR * term.get(j).getWord().length();
mySize += BYTES_PER_DOUBLE;
}
}
}
return mySize;
        }
}