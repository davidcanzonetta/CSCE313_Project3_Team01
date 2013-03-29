package team01;

import java.util.List;
import java.util.ArrayList;


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
		return (n.data.getBoard().getWhite() - n.data.getBoard().getBlack());
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
	
	public static void aiPlayer(Game initialGame) {
		generateTree(initialGame);
		/*
		for(int i = 0; i < root.children.size(); i++) {
			if(utilityValue(root.children.get(i)) > bestUtilityValue) {
				bestUtilityIndex = i;
			}
		}
		*/
		initialGame.board = findBestNode(root, root).data.getBoard();
	}
	
	
	public static MinimaxNode findBestNode(MinimaxNode node, MinimaxNode bestNode) {
		//process node
		if(utilityValue(node) >= utilityValue(bestNode)) {
			bestNode = node;
		}
		if(node.children.size() != 0) {
			for(int i = 0; i < node.children.size(); i++) {
				bestNode = findBestNode(node.children.get(i), bestNode);
			}
			return bestNode;
		} else {
			return bestNode;
		}
	}
	
	public static void generateTree(Game initialGame) {
		root = new MinimaxNode(initialGame);
		generateChildren(root);
	}
	
	private static int generateChildren(MinimaxNode parent){
		//int moreChildren = 0;
		for(int i = 0; i < parent.data.isClickable.size(); i++) {
			//Generate from state
			MinimaxNode tempChild = new MinimaxNode(parent.data);
			tempChild.data.update(root.data.isClickable.get(i));
			//Generate to state which constitutes an entire move, add this node
			for(int j = 0; j < tempChild.data.isClickable.size(); j++){
				MinimaxNode child = new MinimaxNode(tempChild.data);
				child.data.update(tempChild.data.isClickable.get(j));
				parent.addChild(child); //A child after one move 
				//While there are more moves to take, add the move, same 'from' but new 'to's
				while (child.data.isClickable.size() > 0) {
					MinimaxNode grandchild = new MinimaxNode(child.data);
					for(int k = 0; k < child.data.isClickable.size(); k++) {
						grandchild.data.update(child.data.isClickable.get(k));
						child.addChild(grandchild);
					}
					child = grandchild;
				}
			}
			
		}
		return 0;
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
	private static MinimaxNode root;


	public static class MinimaxNode {
		//Constructors
		MinimaxNode(Game nData) {//No parent, No children
			data = new Game(nData);
			children = new ArrayList<MinimaxNode>();
			parent = null;
		} 
		MinimaxNode(Game nData, List<MinimaxNode> nChildren) { //No parent
			data = new Game(nData);
			children = nChildren;
			parent = null;
		}
		MinimaxNode(Game nData, List<MinimaxNode> nChildren, MinimaxNode nParent) {
			data = new Game(nData);
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
		
		public void addChild(MinimaxNode child) {
			children.add(child);
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
		private Game data;
		private MinimaxNode parent;
		private List<MinimaxNode> children;


	}

}