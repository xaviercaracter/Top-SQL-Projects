package cs321.btree;

import java.util.ArrayList;
import java.util.Collections;
/**
 * TreeObject class for Gene Bank BTree implementation. TreeObject contains
 * an array of keys, which must contain at least t - 1 keys and cannot exceed
 * 2t+1 keys. Keys can be added and removed, but no enforcement of minimums is
 * done for ease of use in BTree. 
 * The child array is ordered based on the key array, where the child at index i
 * is less than the key at index i, and child at i+1 is greater than keys[i]. This
 * order can be maintained with the reordering method. 
 * 
 * @author Gabe Miles
 * Date: 12/7/21
 */
public class TreeObject {
	private ArrayList<Long> keys;
	private ArrayList<TreeObject> children;
	private int degree;
	private int numKeys;
	private int numChildren;
	private TreeObject parent;
	
	private final int MAX_KEYS;
	private final int MIN_KEYS;
	private final int MAX_CHILDREN;
	
	/* Generic constructor for TreeObject */
	public TreeObject(int degree){
		this.degree = degree;
		MAX_KEYS = 2 * degree - 1;
		MIN_KEYS = degree - 1;
		MAX_CHILDREN = 2 * degree;
		this.numKeys = 0;
		this.numChildren = 0;
		this.parent = null;
		
		keys = new ArrayList<Long>();
		children = new ArrayList<TreeObject>();
		//Fully instantiate array
		for (int i = 0; i < MAX_CHILDREN; i++) {
			children.add(null);
		}
		
	}
	
	/**
	 * Add a new key to a node. A new key can only be added
	 * in two scenarios:
	 * 1) The node is a leaf
	 * or 
	 * 2) The node is a parent of a node that is being split
	 * 
	 * This method does not check these scenarios, so if used
	 * outside these parameters unexpected results may occur.
	 * 
	 * @param newKey
	 * @return 0 if added, -1 if keys exceed maximum or key is negative
	 */
	public int addKey(Long newKey) {
		if (numKeys >= MAX_KEYS) {
			return -1;
		}
		
		if (newKey <= 0) {
			return -1;
		}
		
		keys.add(newKey);
		Collections.sort(keys);
				
		numKeys++;
		
		return 0;
	}
	
	/**
	 * Removes a key from a node. Does not check if keys have
	 * reached a minimum.
	 * 
	 * @param key
	 * @return 0 is removed, -1 if key is not in array
	 */
	public int removeKey(Long key) {
		boolean removeStatus = keys.remove(key);
		if (removeStatus) {
			numKeys--;
			return 0;
		} else {
			return -1;
		}		
	}
	
	/**
	 * Removes a key at a given index. Does not check if keys has
	 * reached a minimum.
	 * @param index of key
	 * @return key at index if removed, -1 if index is out of bounds
	 */
	public Long removeKeyAtIndex(int index) {
		if (index > numKeys || index < 0) {
			return (long) -1;
		}
		
		Long retVal = keys.remove(index);
		numKeys--;
		
		return retVal;
	}
	
	/**
	 * Returns a key at a given index without removing it.
	 * @param index of key
	 * @return key at a given index, -1 if index is out of bounds
	 */
	public Long getKeyAtIndex(int index) {
		if (index > numKeys || index < 0 || numKeys == 0) {
			return (long) -1;
		}
		return keys.get(index);
	}
	
	/**
	 * Add a child node from the node. This method ensures that the
	 * child node fits into the heap property, that is all keys within
	 * the node are greater than the key at keys[n] and less than keys[n + 1].
	 * 
	 * A child should only be added when splitting a node. Since a BTree grows up,
	 * leafs should not have a child added to them unless they are the root and the
	 * height is 1.
	 * 
	 * @param newChild
	 * @return 0 is child is added, -1 on exception
	 */
	public int addChild(TreeObject newChild) {
		if (numChildren >= numKeys + 1) {
			return -1;
		}
		
		//If child is null, pass by
		if (newChild == null) {
			return 0;
		}
		
		//Representative of keys in child
		Long childKey = newChild.getKeyAtIndex(0);
		
		//Check if child is empty
		if (childKey == -1) {
			return -1;
		}
		
		int index = 0;
		for (; index < numKeys; index++) {
			Long thisKey = getKeyAtIndex(index);
			if (childKey < thisKey) {
				break;
			}
		}
		
		if (children.get(index) == null) {
			children.set(index, newChild);
		} else {
			children.add(index, newChild);
			children.remove(children.size() - 1);
		}
		
		newChild.setParent(this);
		numChildren++;
	
		return 0;
	}
	
	/**
	 * Remove a child node from the node. Child node is removed,
	 * and reordering is unecessary since the BTree properties 
	 * remain unaffected by a removed node. Node is replaced by
	 * NULL to maintain ordering. 
	 * 
	 * Since there is no minimum number of children a node can have
	 * this method does not ensure node has children. It will change
	 * leaf status is numChildren = 0;
	 * 
	 * @param child
	 * @return index of child if added, -1 if child is not in array
	 */
	public int removeChild(TreeObject child) {
		int childIndex = children.indexOf(child);
		
		//Check if child is in children array
		if (childIndex < 0) {
			return -1;
		}
		
		children.set(childIndex, null);
		
		numChildren--;
		if (!isLeaf()) {
			reorderChildren();
		}
		
		return 0;
	}
	
	/**
	 * Removes a child at a given index. If a child at location is
	 * non-null and is set to null. Child order is not maintained,
	 * so reorderChildren must be called as necessary.
	 * 
	 * @param index
	 * @return child at index if removed, null if child is not in array
	 */
	public TreeObject removeChildAtIndex(int index) {
		if (index > numKeys + 1 || index < 0) {
			return null;
		}
		
		TreeObject retVal;
		//If child is null at index, pass by
		if ((retVal = children.get(index)) != null) {
			children.set(index, null);
			
			numChildren--;
		}
		
		return retVal;
	}
	
	/**
	 * Returns the index of a child
	 * @return index of child, -1 if child is not in array
	 */
	public int indexOfChild(TreeObject child) {
		return children.indexOf(child);
	}
	
	/**
	 * Returns the index of a key
	 * @return index of key, -1 if key is not in array
	 */
	public int indexOfKey(Long key) {
		return keys.indexOf(key);
	}
	
	/**
	 * Returns true if node is a leaf, false otherwise
	 * @return true if leaf, false otherwise
	 */
	public boolean isLeaf() {
		return numChildren <= 0;
	}

	/**
	 * Returns an ArrayList of keys in node
	 * @return ArrayList<Long> of keys
	 */
	public ArrayList<Long> getKeys(){
		return keys;
	}
	
	/**
	 * Returns an ArrayList of children in node
	 * @return ArrayList<TreeObject> of children nodes
	 */
	public ArrayList<TreeObject> getChildren(){
		return children;
	}
	
	/** Change node parent 
	 * @param new parent for node
	 */
	public void setParent(TreeObject newParent) {
		this.parent = newParent;
	}
	
	/** Get node parent 
	 * @return parent for node
	 */
	public TreeObject getParent() {
		return parent;
	}
	
	/** Returns the child at a given index 
	 * @return TreeObject child at index
	 */
	public TreeObject getChildAtIndex(int index){
		if (index > numKeys + 1 || index < 0) {
			return null;
		}
		return children.get(index);
	}
	
	/**
	 * Returns the number of keys in node
	 * @return number of keys
	 */
	public int getNumKeys() {
		return numKeys;
	}
	
	/**
	 * Maintains children array order with respect to keys. Compares each
	 * child in turn to keys and places them in the appropriate index (if greater than
	 * keys[i] insert at index i + 1).
	 */
	public void reorderChildren() {
		ArrayList<TreeObject> orderedChildren = new ArrayList<TreeObject>();
		for (int i = 0; i < MAX_CHILDREN; i++) {
			orderedChildren.add(null);
		}
		
		for (int i = 0; i < MAX_CHILDREN; i++) {
			TreeObject child = children.get(i);
			if (child != null) {
				int index = 0;
				Long childKey = child.getKeyAtIndex(0);
				for (; index < numKeys; index++) {
					Long thisKey = getKeyAtIndex(index);
					if (childKey < thisKey) {
						break;
					}
				}
				
				orderedChildren.set(index, child);
			}
		}
		
		this.children = orderedChildren;
	}
	
	/**
	 * Method for searching. Returns child that key belongs to. If
	 * node is a leaf, returns current node.
	 * @param key
	 * @return TreeObject next node, current node if leaf, or null if key
	 * is less than 0
	 */
	public TreeObject getChild(Long key) {
		if (key < 0) {
			return null;
		}
		
		int index = 0; 
		for (; index < numKeys; index++) {
			Long thisKey = getKeyAtIndex(index);
			if (key < thisKey) {
				break;
			}
		}
		
		TreeObject child = children.get(index);
		return child;
	}
	
	/**
	 * Returns if node contains a key
	 * @param key
	 * @return True if key is in node, false otherwise
	 */
	public boolean hasKey(Long key) {
		return keys.contains(key);
	}
	
	/**
	 * Returns the number of children pointed to by node
	 * @return number of children
	 */
	public int getNumChildren() {
		return numChildren;
	}
}
