package com.cowthan.algrithm.algs4;
/*************************************************************************
 *  Compilation:  javac DeDup.java
 *  Execution:    java DeDup < input.txt
 *  Dependencies: SET StdIn.java StdOut.java
 *  Data files:   http://algs4.cs.princeton.edu/35applications/tinyTale.txt
 *
 *  Read in a list of words from standard input and print out
 *  each word, removing any duplicates.
 *
 *  % more tinyTale.txt 
 *  it was the best of times it was the worst of times 
 *  it was the age of wisdom it was the age of foolishness 
 *  it was the epoch of belief it was the epoch of incredulity 
 *  it was the season of light it was the season of darkness 
 *  it was the spring of hope it was the winter of despair
 *
 *  % java DeDup < tinyTale.txt 
 *  it
 *  was
 *  the
 *  best
 *  of
 *  times
 *  worst
 *  age
 *  wisdom
 *  ...
 *  winter
 *  despair
 *
 *************************************************************************/

import com.cowthan.algrithm.std.BinaryStdIn;
import com.cowthan.algrithm.std.In;
import com.cowthan.algrithm.std.Out;
import com.cowthan.algrithm.std.Picture;
import com.cowthan.algrithm.std.StdIn;
import com.cowthan.algrithm.std.StdOut;
import com.cowthan.algrithm.std.StdRandom;
public class DeDup {  
    public static void main(String[] args) {
        SET<String> set = new SET<String>();

        // read in strings and add to set
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (!set.contains(key)) {
                set.add(key);
                StdOut.println(key);
            }
        }
    }
}
