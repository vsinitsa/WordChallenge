# Written by Vlad Sinitsa
# In this solution I used just a basic dictionary with a slower function to find the concatenated words but
# I multi-threaded the function to create a semi quick solution

import collections
import threading
import time

totalwords = 0
words = collections.OrderedDict()
longestword = ""
secondlongestword = ""

timestart = int(round(time.time() * 1000))

fin = open("wordsforproblem.txt","r")

# Read all the words from the file and map them into a ordered dictionary by {word: word length}
readword = fin.readline().strip()
while readword:
    words[readword] = len(readword)
    readword = fin.readline().strip()

# Sort the dictionary by word length from longest to shortest
words = collections.OrderedDict(sorted(words.iteritems(), key=lambda x: x[1], reverse=True))

# This is my thread that works through a portion of the words
class CountThread(threading.Thread):
    def __init__(self, wordstocheck):
        threading.Thread.__init__(self)
        self.wordstocheck = wordstocheck

    def run(self):
        tcount = 0
        for i in range(0, len(self.wordstocheck)):
           #print self.wordstocheck[i]
            if findword(self.wordstocheck[i], True):
                #print "im here"
                tcount += 1
        lock.acquire()  # acquire a lock on the global counter so that we can synchronize the threads
        global totalwords
        totalwords += tcount
        lock.release()


# Recursive algorithm for checking if the word is made up of other words in the file
def findword(word, first):
    for i in range(1, len(word)+1):
        if i == len(word) - 1 and first:
            return False
        else:
            if word[0:i] in words:
                tword = word[words[word[0:i]]:]
                if len(tword) == 0:
                    return True
                if findword(tword, False):
                    return True
                continue
    return False


lock = threading.Lock()  # Lock that the threads use to synchronize
threads = []
# Create the threads and give each thread 500 words then start the thread
for i in range(0, len(words)/500):
    tempThread = CountThread(list(words.keys())[i*500:(i+1)*500])
    tempThread.start()
    threads.append(tempThread)

# This is to take care of any left over words
if (len(words) % 500) > 0:
    lentemp = len(words)/500
    threadExtra = CountThread(list(words.keys())[lentemp*500:(lentemp*500)+len(words) % 500])
    threadExtra.start()
    threads.append(threadExtra)

# Finds the first and second longest words in the main thread
count = 0
while len(longestword) == 0 or len(secondlongestword) == 0:
    word = list(words.keys())[count]
    if findword(word, True):
        if len(longestword) == 0:
            longestword = word
        else:
            secondlongestword = word
    count += 1

for t in threads:
    t.join()

print "The longest word is " + longestword + " with length: " + str(len(longestword))
print "The seconds longest word is " + secondlongestword + " with length: " + str(len(secondlongestword))
print "The total count of concatenated words is: " + str(totalwords)

timeend = int(round(time.time() * 1000))

print str((timeend-timestart)/1000) + " sec"
