package team01;

import java.util.List;



public class minimaxTree {
	
	//Constructors
	minimaxTree() {}
	minimaxTree(MinimaxNode nRoot) {
		root = nRoot;
	}
	
	//Methods
	/*
	public int height() {
		for(int i = 0; i < n.)
	}
	*/
	public static int utilityValue(MinimaxNode n) {
		return (n.data.rem_white() - n.data.rem_black());
	}
	
	public static int minimax(MinimaxNode n, int depth) {
		if(depth <= 0) {
			return utilityValue(n);
		}
		int alpha = -99999;
		for(int i = 0; i < n.children.size(); i++) {
			alpha = Math.max(alpha, -minimax(n.children.get(i), depth-1));
		}
		return alpha;
	}
	
	//Data members
	private MinimaxNode root;
	
	
	public static class MinimaxNode {
		//Constructors
		MinimaxNode(Fanorona nData) {//No parent, No children
			data = nData;
			children = null;
			parent = null;
		} 
		MinimaxNode(Fanorona nData, List<MinimaxNode> nChildren) { //No parent
			data = nData;
			children = nChildren;
			parent = null;
		}
		MinimaxNode(Fanorona nData, List<MinimaxNode> nChildren, MinimaxNode nParent) {
			data = nData;
			children = nChildren;
			parent = nParent;
	
		}
		
		//Methods
		public static int depth(MinimaxNode n) { //Returns the depth of a node
			if (n.parent == null) {
				return 0;
			} else {
				int depth = depth(n.parent) + 1;
				return depth;
			}
		}
		/*
		public int height(MinimaxNode n) {//Return the height of a node where leafs have height 0
		
		}
		*/
		//Data members
		private Fanorona data;
		private MinimaxNode parent;
		private List<MinimaxNode> children;
		
		
	}

}
