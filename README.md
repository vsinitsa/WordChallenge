# WordChallenge

## Problem Description
The premise of this challenge is, when given a file filled with words, find the longest and second longest words that are concatenated of other words in the file and give a count of all concatinated words

Example:  
If we had the words:  
cat  
cats  
catsdogcats  
dog  
dogcatsdog  
hippopotamuses  
rat  
ratcatdogcat  

The longest concatenated word would be 'ratcatdogcat' with 12 characters. While hippopotamuses is a longer word its not made up of other smaller words in the file.

The second longest word would be 'catsdogcats' and the total count of concatenated words would be 3.

## My Solution
1. My first solution was to just try and use a for loop to find all the concatenated words in the file but that solution was very slow and would probably take hours to finish, so I did some research into different algorithms and datastructurs and found that a trie tree could suit my needs, so I wrote up a trie tree in java and pluged my recursive algorithm into it and it worked flawlessly.

2. But after I had my trie tree solution in java I wondered if I could make my original slow algorithm faster by multithreading so in python I wrote up a multithreaded solution that splits up the given list into 500 word chunks and runs the algorithm on those chunks and finally adds the total count from all the chunks together to give you your total count, this brought down the solution from hours to solve to only 16 seconds on my laptop and about 10 seconds on my desktop, so in theory the solution could be spead up using an even faster computer. But in the end you would just want to use a Trie tree to get the best results.
