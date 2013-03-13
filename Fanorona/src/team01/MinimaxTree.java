package team01;

import java.util.List;



public class MinimaxTree {

	//Constructors
	MinimaxTree() {}
	MinimaxTree(MinimaxNode nRoot) {
		root = nRoot;
	}

	//Methods
	/*
	public int height() {
		for(int i = 0; i < n.)
	}
	*/
	public static int utilityValue(MinimaxNode n) {
		return (n.data.numWhite() - n.data.numBlack());
	}

	public static int minimax(MinimaxNode n, int depth) {
		if(depth <= 0 || n.children.size() == 0) {
			return utilityValue(n);
		}
		int alpha = -99999;
		for(int i = 0; i < n.children.size(); i++) {
			alpha = Math.max(alpha, -minimax(n.children.get(i), depth-1));
		}
		return alpha;
	}
	/*
	 function integer play_minimax(node, depth)
    if node is a terminal node or depth == 0:
        return the heuristic value of node
    α = -∞
    LOOP: # try all possible movements for this node/game state
        player = depth mod 2
        move = make_game_move(node, player)
        break if not any move
        α = max(α, -play_minimax(move, depth-1))
    return α
	 */
	public int depth(){ //Returns the depth of the tree
		int max_depth=0;
		for(int i = 0; i < root.children.size(); i++) {
			int curr_depth = MinimaxNode.depth(root.children.get(i));
			if (curr_depth > max_depth) {
				max_depth = curr_depth;
			}
		}
		return max_depth;
	}

	//Data members
	private MinimaxNode root;


	public static class MinimaxNode {
		//Constructors
		MinimaxNode(Board nData) {//No parent, No children
			data = new Board(nData);
			children = null;
			parent = null;
		} 
		MinimaxNode(Board nData, List<MinimaxNode> nChildren) { //No parent
			data = new Board(nData);
			children = nChildren;
			parent = null;
		}
		MinimaxNode(Board nData, List<MinimaxNode> nChildren, MinimaxNode nParent) {
			data = new Board(nData);
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
			if (n == NULL)
				return -1;
			else {
				int max_height;
				for(int i = 0; i < children.size(); i++)
					
			}
		}
		*/
		//Data members
		private Board data;
		private MinimaxNode parent;
		private List<MinimaxNode> children;


	}

}