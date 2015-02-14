package com.awfa.nounroad;

import java.util.*;

public class WordManager {
	
	private Set<String> usedWords;
	private Set<String> availableWords;
	private Deque<String> recentUsed;
	
	public static final int MAX_RECENT_WORDS = 10;
	
	public WordManager(Set<String> words) {
		availableWords = words;
		usedWords = new HashSet<String>();
		recentUsed = new LinkedList<String>();
	}
	
	public boolean playWord(String givenWord) {
		givenWord = givenWord.toLowerCase().trim();
		
		if(usedWords.contains(givenWord)) {
			return false;
		} else if(!availableWords.contains(givenWord)) {
			return false;
		} else if(recentUsed.isEmpty()){
			usedWords.add(givenWord);
			recentUsed.addFirst(givenWord);
			return true;
		} else {
			String compareWord = recentUsed.peekFirst();
			Character c = compareWord.charAt(compareWord.length() - 1);
			
			if(givenWord.charAt(0) == c) {
				usedWords.add(givenWord);
				recentUsed.addFirst(givenWord);
				if(recentUsed.size() > MAX_RECENT_WORDS) {
					recentUsed.removeLast();
				}
				
				return true;
			} else {
				return false;
			}
		}
	}
	
	public Collection<String> getUsedWords() {
		return recentUsed;
	}
	
	public void resetGame() {
		usedWords.clear();
		recentUsed.clear();
	}
}

