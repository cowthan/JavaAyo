package com.cowthan.algrithm.algs4;
/*************************************************************************
 *  Compilation:  javac PictureDump.java
 *  Execution:    java PictureDump width height < file
 *  Dependencies: BinaryStdIn.java Picture.java
 *  Data file:    http://introcs.cs.princeton.edu/stdlib/abra.txt
 *  
 *  Reads in a binary file and writes out the bits as w-by-h picture,
 *  with the 1 bits in black and the 0 bits in white.
 *
 *  % more abra.txt 
 *  ABRACADABRA!
 *
 *  % java PictureDump 16 6 < abra.txt
 *
 *************************************************************************/
import java.awt.Color;

import com.cowthan.algrithm.std.BinaryStdIn;
import com.cowthan.algrithm.std.In;
import com.cowthan.algrithm.std.Picture;
import com.cowthan.algrithm.std.StdIn;
import com.cowthan.algrithm.std.StdOut;
import com.cowthan.algrithm.std.StdRandom;
public class PictureDump {

    public static void main(String[] args) {
        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        Picture pic = new Picture(width, height);
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pic.set(j, i, Color.RED);
                if (!BinaryStdIn.isEmpty()) {
                    count++;
                    boolean bit = BinaryStdIn.readBoolean();
                    if (bit) pic.set(j, i, Color.BLACK);
                    else     pic.set(j, i, Color.WHITE);
                }
            }
        }
        pic.show();
        StdOut.println(count + " bits");
    }
}
