package cs321.btree;

/**
 * File: BTreeInterface.java
 * @author Gabe Miles
 * Date: 11/21/21
 * Description: Implementation of a B-Tree for Gene Bank. A B-Tree is a rooted tree
 * of degree t and height h that has the five following properties:
 * (1) Each node contains t-1 <= n <= 2t-1 keys, with keys stored in non-descending order.
 * 		A node will also contain a boolean flag to signify whether it is a leaf or 
 * 		parent node.
 * (2) For each internal node will contain n+1 pointers to children nodes, and a leaf 
 * 		node will have no children.
 * (3) The keys stored in a node will subdivide the ranges of keys in children nodes. For
 * 		example, if two adjacent keys in a parent node are 2 < 5, then the key 4 would located
 * 		the subtree between these keys.
 * (4) All leaves have the same depth, of height h
 * (5) As stated above, nodes have a maximum and minimum number of keys they can contain. 
 * 		More specifically, a node has to have a minimum of t-1 keys, unless it is the root, which
 * 		much have a non-zero amount of keys if the tree is non-empty. A node can have a maximum of 
 * 		2t-1 keys, and once that number is surpassed the node must be split.
 * 
 * _Adapted from Introduction to Algorithms, Ch 18_
 */

public interface BTreeInterface {
	
	/* Calculate and return the height of the tree */
	public int getHeight();
	
	/* Return the degree of the tree set upon instantiation */
	public int getDegree();
		
	/* Return the root node for unimplemented searching */
	public TreeObject getRoot();
	
	/*
	 * Search through tree, comparing given key to keys within a node.
	 * If the key is between two keys contained within a node, use the pointer designated
	 * by this location to descend to a child node. 
	 * 
	 * If the key is found, return the TreeObject that the key is within
	 * If the key is not found, return NULL
	 */
	public TreeObject search(Long key);
	
	/*
	 * Inserts a new key into the B-Tree. Navigate to the node where key needs 
	 * to be inserted, and attempt to insert key in the proper location, with respect
	 * to current key sequence. If the number of keys will exceed 2t-1, the node will need
	 * to be split. Note: a key can only be added to a leaf node
	 * 
	 * If the key is already in the tree, method will quietly fail.
	 * Upon successful insertion, return void.
	 * If an error occurs, raise an appropriate exception.
	 */
	public int insert(Long key);
	
	/*
	 * Deletes a specified key from the B-Tree. In order to maintain the B-Tree properties, 
	 * removal must follow the following parameters, which depend on each situation.
	 * (1) If the key is in a leaf, delete the key from the node
	 * (2) If the key in in an internal node x, do the following
	 * 		(A) If the child y that preceded node x at the specified k has at least t keys, then find the
	 * 			predecessor key and add it to the node x (remove it from node y in the process)
	 * 		(B) If y has fewer keys, then if child z that succeeds at the specified key has at least
	 * 			t keys, add the successor key to the parent node (remove if from node z in the process)
	 * 		(C) Otherwise, combine y and z and remove the key
	 * (3) If a node does not contain the key, find the child c that forms the subtree that must contain
	 * the key. If the child has t-1 keys, perform the following until the child has at least t keys
	 * 		(A) If a child has an adjacent sibling that has at least t keys, remove the a key from the node
	 * 			add it to the parent, remove a key from the parent, add it to the target child, and then move
	 * 			any necessary child nodes.
	 * 		(B) If no siblings have at lest t keys, combine the child with one of the siblings.
	 */
	public int delete(Long key);
	
	/*
	 * To split a node, simply subdivide the 2t keys into two groups, split by a median key
	 * m. Promote m to the parent node, and use it to split two new nodes of size t. If the parent
	 * node become full, repeat the process until all nodes meet B-Tree requirements.
	 * 
	 */
	public TreeObject splitNode(TreeObject node);
}
