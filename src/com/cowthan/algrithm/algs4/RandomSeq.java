package com.cowthan.algrithm.algs4;
/*************************************************************************
 *  Compilation:  javac RandomSeq.java
 *  Execution:    java RandomSeq N lo hi
 *
 *  Prints N numbers between lo and hi.
 *
 *  % java RandomSeq 5 100.0 200.0
 *  123.43
 *  153.13
 *  144.38
 *  155.18
 *  104.02
 *
 *************************************************************************/
import com.cowthan.algrithm.std.In;
import com.cowthan.algrithm.std.StdIn;
import com.cowthan.algrithm.std.StdOut;
import com.cowthan.algrithm.std.StdRandom;
public class RandomSeq { 
    public static void main(String[] args) {

        // command-line arguments
        int N = Integer.parseInt(args[0]);
        double lo = Double.parseDouble(args[1]);
        double hi = Double.parseDouble(args[2]);

        // generate and print N numbers between lo and hi
        for (int i = 0; i < N; i++) {
            double x = StdRandom.uniform(lo, hi);
            StdOut.printf("%.2f\n", x);
        }
    }
}
