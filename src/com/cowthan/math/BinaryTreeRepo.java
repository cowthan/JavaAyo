package com.cowthan.math;

public class BinaryTreeRepo {
	
	public static BinaryTreeNode createSampleBinaryTree(){
		
		BinaryTreeNode a = new BinaryTreeNode();
		a.data = 1;
		
		BinaryTreeNode b = new BinaryTreeNode();
		b.data = 2;
		
		BinaryTreeNode c = new BinaryTreeNode();
		c.data = 3;
		
		BinaryTreeNode d = new BinaryTreeNode();
		d.data = 4;
		
		BinaryTreeNode e = new BinaryTreeNode();
		e.data = 5;
		
		BinaryTreeNode f = new BinaryTreeNode();
		f.data = 6;

		BinaryTreeNode g = new BinaryTreeNode();
		g.data = 7;
		
		BinaryTreeNode h = new BinaryTreeNode();
		h.data = 8;
		
		BinaryTreeNode i = new BinaryTreeNode();
		i.data = 9;
		
		BinaryTreeNode j = new BinaryTreeNode();
		j.data = 10;
		
		
		a.left = b;
		a.right = c;
		
		b.left = d;
		
		c.left = e;
		
		d.left = f;
		d.right = g;
		
		e.left = h;
		e.right = i;
		
		
		return a;
		
	}
	
}
