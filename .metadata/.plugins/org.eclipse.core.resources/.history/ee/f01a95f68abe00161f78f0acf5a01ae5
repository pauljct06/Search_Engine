Search Engine
A quick, efficient, search engine based on multiple websites from the internet

Usage:
  $ javac searchEngineDriver.java
  $ java searchEngineDriver

Project Notes:
  - Input websites are stored inside webpages_input.txt
  - Only websites specified inside, above, input file are used for crawling and ranking

Implementation Notes:
  - Preprocessing is done to optimize actual user searching process
  - Although the assignment was to reproduce the search engine implementation mentioned in the book, under section 23.5, I believed I could write a more optimized solution using multiple hashmaps/sets instead of a trie tree. I understand that my solution uses more auxiliary space, however, I believe the tradeoff of time complexity for auxiliary space was worth it because space is retrievable - time isn't.
  - The speed of my application is due to the preprocesing step, where I crawl the websites, tokenize the body, and make a multi layered map which provides me the search complexity of O(n) - O(1) per tokenized input.