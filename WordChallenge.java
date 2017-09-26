/**
 * Written by Vlad Sinitsa
 * In this solution I used a Trie tree stucture to quickly and efficiently check if each word was made up of other words in the file
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.List;
import java.util.Scanner;
public class WordChallenge {
    public static void main(String[] args)throws IOException{
        String longestword = null;
        String secondlongestword = null;
        Scanner fin = new Scanner(new File("wordsforproblem.txt"));
        Trie tree = new Trie();
        List<String> words = new ArrayList<>();
        while(fin.hasNextLine()){
            String word = fin.nextLine();
            if(word.length() > 0) {//Is used to parse out the blank lines in the bottom of the file
                tree.insert(word);
                words.add(word);
            }
        }
        //sort the arraylist by word length from longest to shortest
        words.sort((s1, s2)-> Math.abs(s2.length()) - Math.abs(s1.length()));

        int counter = 0;
        for(String w: words){
            if(tree.isConcat(w, true)){
                if(longestword == null){//Since we sorted the list from longest to shortest we know that the first word we encounter that is a concatenated word is the longest
                    longestword = w;    //so we set it as the longest word
                }else if(secondlongestword == null){//Same goes for second longest since the first longest is no longer null
                    secondlongestword = w;
                }
                counter++;
            }
        }
        System.out.println("The longest word is: " + longestword + " with length: " + longestword.length());
        System.out.println("The second longest word is: " + secondlongestword + " with length: " + secondlongestword.length());
        System.out.println("The total count of concatenated words is: " + counter);
    }




}

class Trie{

    //Node class for Trie structure
    class Node{
        char letter;
        HashMap<Character, Node> children;
        boolean wordEnd;

        public Node(char l){
            letter = l;
            children = new HashMap<>();
            wordEnd = false;
        }

        public Node(char l, boolean isEnd){
            letter = l;
            children = new HashMap<>();
            wordEnd = isEnd;
        }

        public HashMap<Character, Node> getChildren(){return children;}
        public void setWordEnd(boolean set){wordEnd = set;}
        public boolean getWordEnd(){return wordEnd;}
    }

    //Initialize trie and other methods
    Node root;
    public Trie(){
        root = new Node(' ');
    }

    public void insert(String word){
        Node cur = root;
        for(int i = 0; i < word.length(); i++){
            HashMap<Character, Node> child = cur.getChildren();
            char temp = word.charAt(i);

            if(child.containsKey(temp)){
                cur = child.get(temp);
            } else {
                Node tempNode = new Node(word.charAt(i));
                child.put(temp, tempNode);
                cur = tempNode;
            }
        }
        cur.setWordEnd(true);
    }

    //Recursive function for determining if a word is concatenated of other words in the file
    public boolean isConcat(String word, boolean f){
        boolean first = f; //boolean variable is used to determine if were in the first recursive loop so we dont have words matching to themselves
        Node cur = root;
        for(int i = 0; i < word.length(); i++){
            HashMap<Character, Node> child = cur.getChildren();
            if(!child.containsKey(word.charAt(i))){
                return false;
            }

            cur = child.get(word.charAt(i));
            if(i == word.length()-1 && first){//will only trigger if we get to the end of a word without finding any prefixes
                return false;
            }
            if(cur.getWordEnd()){
                String temp = word.substring(i+1);
                if(temp.length() == 0){
                    return true;
                }
                if(isConcat(temp, false)){//after finding a prefix we chop the prefix off and send the shortened word back into the function
                    return true;
                }
            }
        }//If we get to the end of the for loop we know the word we are looking at is not in the file so we return false
        return false;
    }
}
