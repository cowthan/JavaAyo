package com.cowthan.collection.hash;

//: containers/Groundhog.java
// Looks plausible, but doesn't work as a HashMap key.

public class Groundhog {
	protected int number;

	public Groundhog(int n) {
		number = n;
	}

	public String toString() {
		return "Groundhog #" + number;
	}

	public int hashCode() {
		return number;
	}

	public boolean equals(Object o) {
		return o instanceof Groundhog && (number == ((Groundhog) o).number);
	}
} // /:~
