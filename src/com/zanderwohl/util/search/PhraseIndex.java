package com.zanderwohl.util.search;

import java.util.*;

/**
 * An index for use in searching for phrases by abbreviation or initials.
 * Similar to the style of autocomplete in Epic Systems' Hyperspace.
 */
public class PhraseIndex {

    /**
     * The actual index. Can be quite large.
     */
    protected HashMap<String, ArrayList<String>> index;

    /**
     * Standard constructor. Data is added later.
     */
    public PhraseIndex(){
        index = new HashMap<>();
    }

    /**
     * Add a phrase to the index.
     * @param key The phrase or sentence to add.
     */
    public void put(String key){
        createIndex(key);
    }

    /**
     * Create an index for a phrase. Loops a lot. Expensive.
     * O(n*m) where n is number of works and m is avg length of word.
     * @param value
     */
    private void createIndex(String value){
        String[] words = value.split("\\s");
        for(String word: words){
            for(int i = 1; i < word.length() + 1; i++){
                String subKey = word.substring(0, i).toUpperCase(Locale.ROOT);
                if(!index.containsKey(subKey)){
                    index.put(subKey, new ArrayList<>());
                }
                index.get(subKey).add(value);
            }
        }
    }

    /**
     * A set of all the keys... don't know why I coded this or why you'd need it... AJL 2021-09-18
     * @return
     */
    public Set<String> keys(){
        return index.keySet();
    }

    /**
     * Gets the value for an exact key.
     * @param key The key.
     * @return The value.
     */
    public ArrayList<String> values(String key){
        return index.get(key);
    }

    /* Oh God in heaven, hear my plea,
    * and do not place the blame one me.
    * I wanted something short and sweet,
    * a method that was clean and neat.
    * I didn't mean to make this mess,
    * or cause my future-me distress.
    * I blame the MUMPS, its trees and such,
    * that made me sin so very much.
    * Like Icarus, I went too high,
    * I stumbled and it went awry.
    * One last thing that I request,
    * before I lay me down to rest:
    * whoever fix this mess of tree
    * would not track down and murder me.
    * */
    /**
     * Searches the key set for values that may match or be similar to the phrase or abbreviation used as the key words.
     * @param searchTerms The search terms, which may be as many as you want to provide.
     * @return Any values that match as many of the search terms as possible.
     */
    public ArrayList<String> search(String searchTerms){
        String[] searchTermsSplit = searchTerms.split("\\s");
        String[] foundTerms = new String[searchTermsSplit.length];
        int foundTermsIndex = 0;
        for(String term: searchTermsSplit){
            if(index.containsKey(term.toUpperCase(Locale.ROOT))){
                foundTerms[foundTermsIndex] = term.toUpperCase(Locale.ROOT);
                foundTermsIndex++;
            }
        }
        TreeMap<String, Integer> resultsByRelevance = new TreeMap<>();
        for(int i = 0; i < foundTermsIndex; i++){
            String term = foundTerms[i];
            ArrayList<String> resultsForTerm = index.get(term);
            for(String result: resultsForTerm){
                if(resultsByRelevance.containsKey(result)){
                    resultsByRelevance.put(result, resultsByRelevance.get(result) + 1);
                } else {
                    resultsByRelevance.put(result, 1);
                }
            }
        }
        TreeMap<Integer, ArrayList<String>> relevanceRankedResults = new TreeMap<>(Collections.reverseOrder());
        for(Map.Entry<String, Integer> entry: resultsByRelevance.entrySet()){
            if(!relevanceRankedResults.containsKey(entry.getValue())){
                relevanceRankedResults.put(entry.getValue(), new ArrayList<>());
            }
            relevanceRankedResults.get(entry.getValue()).add(entry.getKey());
        }
        ArrayList<String> results = new ArrayList<>();
        Set<Map.Entry<Integer, ArrayList<String>>> set = relevanceRankedResults.entrySet();
        for (Map.Entry<Integer, ArrayList<String>> me : set) {
            ArrayList<String> list = me.getValue();
            results.addAll(list);
        }
        return results;
    }
}
