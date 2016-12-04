package com.cowthan.math;

import java.util.ArrayList;
import java.util.List;

/**
 * 二叉树的层次遍历
 * @author Administrator
 *
 */
public class BinaryTreeTraverse_ByLevel {
	
	public static void tranverse(BinaryTreeNode root, BinaryNodeProcessor processor){
		
		if(root == null) {
			System.out.println("二叉树是空");
			return;
		}
		
		List<BinaryTreeNode> nodes = new ArrayList<BinaryTreeNode>();
		nodes.add(root);

		int level = 1;
		
		while(true){
			System.out.println("level---" + level);
			if(nodes == null || nodes.size() == 0) break;
			List<BinaryTreeNode> nodesForNextLevel = new ArrayList<BinaryTreeNode>();
			for(BinaryTreeNode node: nodes){
				processor.process(node);
				
				if(node.left != null) nodesForNextLevel.add(node.left);
				if(node.right != null) nodesForNextLevel.add(node.right);
			}
			nodes = nodesForNextLevel;
			level++;
		}
		
		
	}
	
	public static void main(String[] args) {
		BinaryTreeNode tree = BinaryTreeRepo.createSampleBinaryTree();
		tranverse(tree, new BinaryNodeProcessor() {
			
			@Override
			public void process(BinaryTreeNode node) {
				System.out.println(node.data);
			}
		});
	}
}
