import java.io.*;
import java.util.*;



class Pair<String> {
	private String first;
	private String second;
	
	public Pair(String first, String second) {
		this.first = first;
		this.second = second;
	}
	public String first() {
		return first;
	}
	public String second() {
		return second;
	}
}

class Trie {

	private class TrieNode {
	
		private char val;			                    
		private HashMap<Character, TrieNode> children;	
		private boolean isEndWord;		                   
		
		public TrieNode(char val) {
			this.val = val;
			children = new HashMap<Character, TrieNode>();
			isEndWord = false;
		}
		
		
		public void addChild(char child) {
			children.put(child, new TrieNode(child));
		}
		
	
		public TrieNode getChild(char child) {
			if (!children.keySet().contains(child))
				return null;
			
			return children.get(child);
		}
		
		
		public boolean containsChild(char child) {
			return children.keySet().contains(child);
		}
	}
	
	private TrieNode root;
	private TrieNode current;
	
	public Trie() {
		root = new TrieNode(' ');	
		current = root;
	}
	

	public void insert(String str) {
		char letter;
		current = root;
		
		
		for (int i = 0; i < str.length(); i++) {
			letter = str.charAt(i);
			
			if (!current.containsChild(letter))
				current.addChild(letter);
			
			current = current.getChild(letter);
		}
		
		
		current.isEndWord = true;
	}
	

	public List<Integer> getIndices(String str) {
		List<Integer> indices = new LinkedList<Integer>();	
		char letter;
		current = root;	
		
		for (int i = 0; i < str.length(); i++) {
			letter = str.charAt(i);
			
	
			if (!current.containsChild(letter))
				return indices;
			
		
			current = current.getChild(letter);
			
		
			if (current.isEndWord)
				indices.add(i + 1);
		}
		
		return indices;
	}
	
}

class LongestCompoundWord {
	public static void main(String[] args) throws FileNotFoundException {
		
		File file = new File("Input_02.txt");
		
		Trie trie = new Trie();
		

		HashSet<String> compoundWords = new HashSet<String>();
		LinkedList<Pair<String>> queue = new LinkedList<Pair<String>>();
		
		Scanner str = new Scanner(file);

		String word;			
		List<Integer> suffixIndices;	
		
		while (str.hasNext()) {
			word = str.next();		
			suffixIndices = trie.getIndices(word);
		
			for (int idx : suffixIndices) {
				if (idx >= word.length())		
					break;					
											
				queue.add(new Pair<String>(word, word.substring(idx)));
			}
			trie.insert(word);
		}
		
		Pair<String> pairedWord;				
		int maxLen = 0;			
					
		String longestCompundWord = "";		
			

		while (!queue.isEmpty()) {
			pairedWord = queue.removeFirst();
			word = pairedWord.second();
			
			suffixIndices = trie.getIndices(word);
			
	
			if (suffixIndices.isEmpty())
				continue;
			
			
			for (int idx : suffixIndices) {
				if (idx > word.length()) { 
					break;
				}
				
				if (idx == word.length()) { 
					
					if (pairedWord.first().length() > maxLen) {
						
						
						maxLen = pairedWord.first().length();
						longestCompundWord = pairedWord.first();
					}
			
					compoundWords.add(pairedWord.first());	
					
				} else {
					queue.add(new Pair<String>(pairedWord.first(), word.substring(idx)));
				}
			}
		}
		
        str.close();

		System.out.println("Longest Compound Word: " + longestCompundWord);
	}
}
