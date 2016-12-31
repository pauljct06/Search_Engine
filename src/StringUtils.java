/**
 * Exposes custom String utility functions
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class StringUtils {
	
	public static String[] getTokens(String str) {
		return str.replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+");
	}
	
	
	public static Set<String> getStopwordSet() throws FileNotFoundException {
		Scanner s = new Scanner(new File("StopWords.txt"));
		Set<String> StopWordSet = new HashSet<String>();
		while (s.hasNext()){
		    StopWordSet.add(s.next());
		}
		s.close();
		
		return StopWordSet;
	}
	
	public static String[] removeStopwords(String[] tokens) {
		try {
			Set<String> stopWords = StringUtils.getStopwordSet();
			List<String> cleanWordList = new ArrayList<String>();
			
			for (String word: tokens) {
				if(!stopWords.contains(word)) {
					cleanWordList.add(word);
				}
			}
			return cleanWordList.toArray(new String[100]);		
		} catch (FileNotFoundException e) {
			System.out.println("Error Processing StopWords.txt. Including stop words now");
			e.printStackTrace();
		}
		
		return tokens;	
	}
	
	public static Map<String, Integer> buildFreqMap(String[] tokens) {
		Map<String, Integer> freqMap = new HashMap<String, Integer>();
		
		for (String token: tokens) {
			if (!freqMap.containsKey(token)) {
				freqMap.put(token, 1);
			}
			else {
				freqMap.put(token, (freqMap.get(token) + 1));
			}
		}
		
		return freqMap;
	}
	
}