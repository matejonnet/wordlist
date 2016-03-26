WordList Generator
==================

Crunch like wordlist generator with simple pattern definition.

Usage
-----

Compile the program

`mvn clean install`

Generate a list of words using

`java -jar target/wordlist-1.0.jar 1st-letter-charset 2nd-letter-charset ... n-th-letter-charset`

Example bellow will generate 27040 words starting with a0a and ending with Z9Z, where
1st character is one of mixalpha
2dn character is one of numeric
3rd character is one of mixalpha

`java -jar target/wordlist-1.0.jar mixalpha numeric mixalpha`

Optionally you can provide a file containing words that will be excluded from the generated list.

`java -jar target/wordlist-1.0.jar mixalpha numeric mixalpha -e ./file-with-words-to-exclude.txt`


LICENCE
-------
[GNU GENERAL PUBLIC LICENSE Version 3](gpl-3.0.txt)
