package cs321.btree;

import java.util.ArrayList;

public class BTree<E> implements BTreeInterface {
	private int degree;
	private int height;
	private TreeObject root;
	private int sequenceLength;
	
	private final int MAX_KEYS;
	private final int MIN_KEYS;
	private final int MAX_CHILDREN;
	
	public BTree(int degree, int sequenceLength) {
		this.degree = degree;
		this.height = 1;
		this.root = new TreeObject(degree);
		this.sequenceLength = sequenceLength;
		
		this.MAX_KEYS = 2 * degree - 1;
		this.MIN_KEYS = degree - 1;
		this.MAX_CHILDREN = 2 * degree;
	}
	
	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getDegree() {
		return degree;
	}

	@Override
	public TreeObject getRoot() {
		return root;
	}

	@Override
	public TreeObject search(Long key) {
		return search(key, root);
	}
	
	/**
	 * Recursive implementation of search method.
	 * @param key to be searched for
	 * @param node to be searched
	 * @return node containing key, null on failure
	 */
	public TreeObject search(Long key, TreeObject node) {
		TreeObject retVal = null;
		if (node.hasKey(key)) {
			retVal = node;
		} else {
			if (!node.isLeaf()) {
				retVal = search(key, node.getChild(key));
			}
		}
		
		return retVal;
	}

	@Override
	public int insert(Long key) {
		return insert(key, root);
	}
	
	/**
	 * Recursive implementation of insert method. 
	 * @param key to be inserted
	 * @param current node
	 * @return 0 on successful insertion, -1 on failure
	 */
	public int insert(Long key, TreeObject node) {
		if (key < 0 || node == null) {
			return -1;
		}
		
		if (node.getNumKeys() >= MAX_KEYS) {
			insert(key, splitNode(node));
		} else {
			if (node.isLeaf()) {
				if (!node.hasKey(key)) {
					node.addKey(key);
				} 
			} else {
				return insert(key, node.getChild(key));
			}
		}
		
		return 0;
	}

	@Override
	public int delete(Long key) {
		return delete(key, root);
	}
	
	/**
	 * Recursive implementation of delete method
	 * @param key to be deleted
	 * @param node to be searched
	 * @return 0 on successful deletion, -1 on failure
	 */
	public int delete(Long key, TreeObject node) {
		if (node == null || key < 0) {
			return -1;
		}
		
		if (node.getNumKeys() <= 0 && node == root) {
			this.root = node.getChildAtIndex(0);
			node.setParent(null);
			height--;
			return delete(key, root);
		} else if (!node.hasKey(key)) {
			if (node.isLeaf()) {
				return -1;
			} 
			
			int index = 0;
			for (; index < node.getNumKeys(); index++) {
				Long thisKey = node.getKeyAtIndex(index);
				if (key <= thisKey) {
					break;
				}
			}
			
			TreeObject targetChild = node.getChildAtIndex(index);
			if (targetChild.getNumKeys() <= MIN_KEYS) {
				TreeObject parent = node;
				TreeObject leftChild = null;
				TreeObject rightChild = null;
				
				index = parent.indexOfChild(targetChild);
				if (index > 0) {
					leftChild = parent.getChildAtIndex(index - 1);
					if (leftChild.getNumKeys() >= MIN_KEYS + 1) {
						targetChild.addKey(parent.removeKeyAtIndex(index - 1));
						parent.addKey(leftChild.removeKeyAtIndex(leftChild.getNumKeys() - 1));
						targetChild.addChild(leftChild.removeChildAtIndex(leftChild.getNumKeys() + 1));
						return delete(key, targetChild);
					} 
				}
				
				if (index < parent.getNumKeys()) {
					rightChild = parent.getChildAtIndex(index + 1);
					if (rightChild.getNumKeys() >= MIN_KEYS + 1) {
						targetChild.addKey(parent.removeKeyAtIndex(index));
						parent.addKey(rightChild.removeKeyAtIndex(0));
						targetChild.addChild(rightChild.removeChildAtIndex(0));
						rightChild.reorderChildren();
						return delete(key, targetChild);
					}
				}
				
				if (leftChild != null) {
					int nodeNumKeys = targetChild.getNumKeys();
					for (int i = 0; i < nodeNumKeys; i++) {
						leftChild.addKey(targetChild.removeKeyAtIndex(0));
					}
					
					if (index >= parent.getNumKeys()) {
						leftChild.addKey(parent.removeKeyAtIndex(index - 1));
					} else {
						leftChild.addKey(parent.removeKeyAtIndex(index));
					}
					
					if (!targetChild.isLeaf()) {
						for (int i = 0; i < nodeNumKeys + 1; i++) {
							leftChild.addChild(targetChild.removeChildAtIndex(i));
						}
					}
					
					parent.removeChild(targetChild);
					parent.reorderChildren();
					return delete(key, leftChild);
				} else if (rightChild != null) {
					int rightChildNumKeys = rightChild.getNumKeys();
					for (int i = 0; i < rightChildNumKeys; i++) {
						targetChild.addKey(rightChild.removeKeyAtIndex(0));
					}
					
					targetChild.addKey(parent.removeKeyAtIndex(index));
					
					if (!rightChild.isLeaf()) {
						for (int i = 0; i < rightChildNumKeys + 1; i++) {
							targetChild.addChild(rightChild.removeChildAtIndex(i));
						}
					}
					
					parent.removeChild(rightChild);
					parent.reorderChildren();
					return delete(key, targetChild);
				}
			} else {
				return delete(key, targetChild);
			}
			
		} else {
			if (node.isLeaf()) {
				node.removeKey(key);
			} else {
				int index = node.indexOfKey(key);
				TreeObject leftChild = node.getChildAtIndex(index);
				TreeObject rightChild = node.getChildAtIndex(index + 1);
				
				if (leftChild.getNumKeys() >= degree) {
					Long predecessorKey = leftChild.getKeyAtIndex(leftChild.getNumKeys() - 1);
					delete(predecessorKey, node);
					node.addKey(predecessorKey);
					node.removeKey(key);
				} else if (rightChild.getNumKeys() >= degree) {
					Long successorKey = rightChild.getKeyAtIndex(0);
					delete(successorKey, node);
					node.addKey(successorKey);
					node.removeKey(key);
				} else {
					int rightKeys = rightChild.getNumKeys();
					for (int i = 0; i < rightKeys; i++) {
						leftChild.addKey(rightChild.getKeyAtIndex(0));
						rightChild.removeKeyAtIndex(0);
					}
					
					if (!rightChild.isLeaf()) {
						for (int i = 0; i < rightKeys + 1; i++) {
							leftChild.addChild(rightChild.getChildAtIndex(i));
							rightChild.removeChildAtIndex(i);
						}
					}
					
					node.removeChild(rightChild);
					node.removeKey(key);
					node.reorderChildren();
				}
			}
		} 
		
		return 0;
	}

	@Override
	public TreeObject splitNode(TreeObject node) {
		TreeObject parent = node.getParent();
		TreeObject newNode = new TreeObject(degree);
		
		//Don't split nodes with less than maximum keys
		if (node.getNumKeys() < MAX_KEYS) {
			return node;
		}
		
		if (parent != null && parent.getNumKeys() > MAX_KEYS) {
			splitNode(parent);
		}
		
		for (int i = 0; i < MIN_KEYS; i++) {
			newNode.addKey(node.getKeyAtIndex(0));
			node.removeKeyAtIndex(0);
		}
		
		if (!node.isLeaf()) {
			for (int i = 0; i < degree; i++) {
				newNode.addChild(node.getChildAtIndex(i));
				node.removeChildAtIndex(i);
			}
		}
		
		//If the node is the root
		if (parent == null) {
			TreeObject newRoot = new TreeObject(degree);
			newRoot.addKey(node.getKeyAtIndex(0));
			node.removeKeyAtIndex(0);
			node.reorderChildren();
			
			newRoot.addChild(newNode);
			newRoot.addChild(node);
			
			root = newRoot;
			height++;
			return newRoot;
		} else { //Otherwise
			parent.addKey(node.getKeyAtIndex(0));
			node.removeKeyAtIndex(0);
			
			parent.addChild(newNode);
			return parent;
		}
	}
	
	/**
	 * Convert an inputted gene into a byte sequence, which
	 * will then be further converted into a Long key. Gene input
	 * will be truncated to adhere to specified gene sequence length.
	 * 
	 * @param String gene sequence
	 * @return Long key
	 */
	public long geneToKey(String gene) {
		StringBuilder sb = new StringBuilder();
		//Ensures leading zeroes are saved
		for (int i = 0; i < sequenceLength; i++) {
			switch (gene.charAt(i)) {
				case 'A':
					sb.append("00");
					break;
				case 'T':
					sb.append("11");
					break;
				case 'C':
					sb.append("01");
					break;
				case 'G':
					sb.append("10");
					break;
				default:
					throw new IllegalArgumentException("Gene can only contain char A, T, G, or C\n");
			}
		}
		
		long key = Long.parseLong(sb.toString(), 2);
		
		return key;
	}
	
	/**
	 * Takes in a Long key, converts it into a String byte sequence,
	 * then converts bytes into character sequence of genome. 
	 * @param key
	 * @return String gene sequence
	 */
	public String keyToGene(long key) {
		//Convert long key into byte array
		StringBuilder byteKey = new StringBuilder(Long.toBinaryString(key));
		int numZeroes = (sequenceLength * 2) - byteKey.length();
		for (int i = 0; i < numZeroes; i++) {
			byteKey.insert(0, '0');
		}
		char[] byteKeyArray = byteKey.toString().toCharArray();
		StringBuilder gene = new StringBuilder();
		
		for (int index = 0; index < sequenceLength * 2; index += 2) {
			if (byteKeyArray[index] == '0') {
				if (byteKeyArray[index + 1] == '0') {
					gene.append('A');
				} else if (byteKeyArray[index + 1] == '1'){
					gene.append('C');
				}
			} else if (byteKeyArray[index] == '1') {
				if (byteKeyArray[index + 1] == '0') {
					gene.append('G');
				} else if (byteKeyArray[index + 1] == '1') {
					gene.append('T');
				}
			}
		}
		
		return gene.toString();
	}
	

}
