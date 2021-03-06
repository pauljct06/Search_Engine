/**
 * Driver class that 
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

public class SearchEngineDriver {
	public static final String INPUTFILE = "WebPagesInput.txt"; 
	
//	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		Scanner kb = new Scanner(System.in);
		
		// addresses listed inside input file
		List<String> webaddresses = getFilesFromInput(INPUTFILE);
		WebsiteProcessor wp = new WebsiteProcessor();
		
		// contains webpage to the (words, count) it contains ( wp1 -> [{word: count}, {word: count}] )
		Map<String, Map<String, Integer>> webpageToWords = new HashMap<String, Map<String, Integer>>();
		
		// global dictionary of words to the webpages they are inside (word -> [wp1, wp3])
		Map<String, List<String>> wordsToWebpage = new HashMap<String, List<String>>();

		// process per each webpage
		for (String webpage: webaddresses) {
			System.out.println("[*] Processing " + webpage);
			
			// get webpage content for current webpage
			wp.setWebaddress(webpage);
			wp.process();
			String bodyContent = wp.getContent();
			
			// all words in the current webpage (includes dupes)
			String[] allTokens = StringUtils.getTokens(bodyContent);
			
			// all words after removing stopwords
			String[] tokens = StringUtils.removeStopwords(allTokens);

			// frequency map of words to the number of times they appear in current webpage
			Map<String, Integer> freqMap = StringUtils.buildFreqMap(tokens);
			
			// map containing the current webpage -> (words -> count)
			webpageToWords.put(webpage, freqMap);
			
			List<String> tmp;
			String currWord;
			
			// creating words to the list of webpages they appear inside (word -> [wp1, wp3])
			for (Map.Entry<String, Integer> wordEntry: freqMap.entrySet()) {
				currWord = wordEntry.getKey();
				
				if (!wordsToWebpage.containsKey(currWord)) {
					tmp = new ArrayList<String>();
					tmp.add(webpage);
					wordsToWebpage.put(currWord, tmp);
				} else {
					tmp = wordsToWebpage.get(currWord);
					tmp.add(webpage);
					wordsToWebpage.put(currWord, tmp);
				}
			}
		}
		
		String input = "-";
		
		// processing the input and return website matching input in order
		while (input != "") { 
			System.out.print("\n\n[+] Enter your search or press Enter key to exit program : ");
			input = kb.nextLine(); // process input
			
			if (input.equals("")) {
				System.out.println("Thanks for searching. Exiting...");
				kb.close();
				return;
			}
			
			String[] tokens = StringUtils.getTokens(input);
			
			List<List<String>> relevantPageLists = new ArrayList<List<String>>();
			List<String> searchedWords =  new ArrayList<String>();

			// constructs relevant page lists ( [ [wp1, wp2], [wp2], [wp1, wp3] ] )
			for (String token: tokens) {
				if (wordsToWebpage.containsKey(token)) {
					relevantPageLists.add(wordsToWebpage.get(token));
					searchedWords.add(token);  //adding search words to a list [word1, word2]
					
				}
			}
			
			// adding the websites and the number of words found to webpageFreq map (webpage -> #wordsfound)
			Map<String, Integer> webpageFreq = new HashMap<String, Integer>();
			for (List<String> webpageList: relevantPageLists) {
				for (String webpage: webpageList) {
					if (!webpageFreq.containsKey(webpage)) {
						webpageFreq.put(webpage, 1 );
					}
					else {
						webpageFreq.put(webpage, webpageFreq.get(webpage) + 1);
					}
				}
			}
			
			List<Map.Entry<String, Integer>> webpageEntries = new ArrayList<Map.Entry<String, Integer>>(webpageFreq.entrySet());
			
			// ranking the websites by the amount of word that were found
			Collections.sort(webpageEntries, new Comparator<Map.Entry<String, Integer>>() {

				@Override
				public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
					return o2.getValue() - o1.getValue();
				}
				
			});
			
			// printing the sorted hashmap in order
			int i = 1;
			for (Map.Entry<String, Integer> webpageEntry: webpageEntries) {
				System.out.printf("%d: %-95s Words found %s \n", i,webpageEntry.getKey(),webpageEntry.getValue());
				i++;
			}
			//technology settings video games
			
			
			// message display when words are not found
			if (webpageEntries.isEmpty()) {
				System.out.println("Sorry, No results for searched words in current pages");
			}
			// find the union between the above webpages
			
			
			// show the results based on the webpage/word freq (ranking)
		}
		kb.close();
	}
	
	@SuppressWarnings("resource")
	public static List<String> getFilesFromInput(String filename) {
		List<String> webAddresses = new ArrayList<String>();
		
		try {
			FileReader in = new FileReader(filename);
			BufferedReader br = new BufferedReader(in);
			String line;
			
			while ((line = br.readLine()) != null) {
				webAddresses.add(line);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return webAddresses;
	}
}
