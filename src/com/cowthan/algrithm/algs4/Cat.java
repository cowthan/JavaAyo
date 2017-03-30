package com.cowthan.algrithm.algs4;
/*************************************************************************
 *  Compilation:  javac Cat.java
 *  Execution:    java Cat input0.txt input1.txt ... output.txt
 *  Dependencies: In.java Out.java
 *
 *  Reads in text files specified as the first command-line 
 *  parameters, concatenates them, and writes the result to
 *  filename specified as the last command line parameter.
 *
 *  % more in1.txt
 *  This is
 *
 *  % more in2.txt 
 *  a tiny
 *  test.
 * 
 *  % java Cat in1.txt in2.txt out.txt
 *
 *  % more out.txt
 *  This is
 *  a tiny
 *  test.
 *
 *************************************************************************/

import com.cowthan.algrithm.std.BinaryStdIn;
import com.cowthan.algrithm.std.In;
import com.cowthan.algrithm.std.Out;
import com.cowthan.algrithm.std.Picture;
import com.cowthan.algrithm.std.StdIn;
import com.cowthan.algrithm.std.StdOut;
import com.cowthan.algrithm.std.StdRandom;
public class Cat { 

    public static void main(String[] args) { 
        Out out = new Out(args[args.length - 1]);
        for (int i = 0; i < args.length - 1; i++) {
            In in = new In(args[i]);
            String s = in.readAll();
            out.println(s);
            in.close();
        }
        out.close();
    }

}
