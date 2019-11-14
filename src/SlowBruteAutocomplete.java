//The SlowBruteAutocomplete class is the slowest of all the Autocomplete classes, because it requires looping through
//all the terms, and then looping through the terms with prefixes again to find the top k matches.

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SlowBruteAutocomplete extends BruteAutocomplete {
/**
      * Create immutable instance with terms constructed from parameter
      *
      * @param terms   words such that terms[k] is part of a word pair 0 <= k < terms.length
      * @param weights weights such that weights[k] corresponds to terms[k]
      * @throws NullPointerException     if either parameter is null
      * @throws IllegalArgumentException if terms.length != weights.length
      * @throws IllegalArgumentException if any element of weights is negative
      * @throws IllegalArgumentException if any element of terms is duplicate
      */
    public SlowBruteAutocomplete(String[] terms, double[] weights) {
super(terms, weights);
}
@Override
public List<Term> topMatches(String prefix, int k) {
if (k < 0) {
throw new IllegalArgumentException("Illegal value of k:"+k);
}
List<Term> list = new ArrayList<>();
for (Term x: myTerms){
if (x.getWord().startsWith(prefix)){
list.add(x);
}
}
Comparator c = Comparator.comparing(Term :: getWeight).reversed();
Collections.sort(list,c);
List<Term> ret = new ArrayList<>();
for (int i=0; i<k + 1; i++){
ret.add(list.get(i));
}
return ret;
}
}