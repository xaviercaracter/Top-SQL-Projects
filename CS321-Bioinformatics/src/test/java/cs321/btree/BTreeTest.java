package cs321.btree;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.ArrayList;
/**
 * Unit tests for BTree and TreeObject classes. 
 * @author Gabe Miles
 * Date: 12/7/21
 *
 */
public class BTreeTest
{
    
    @Test
    public void testTreeNodeAddKey() {
    	TreeObject to = new TreeObject(2);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long neg = (long) -2;
    	
    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	//Safe
    	assertEquals(NO_ERROR, to.addKey(A));
    	ArrayList<Long> actualKeys = to.getKeys();
    	assertEquals(A, actualKeys.get(0));
    	
    	assertEquals(NO_ERROR, to.addKey(C));
    	actualKeys = to.getKeys();
    	assertEquals(A, actualKeys.get(0));
    	assertEquals(C, actualKeys.get(1));

    	
    	assertEquals(NO_ERROR, to.addKey(B));
    	actualKeys = to.getKeys();
    	assertEquals(A, actualKeys.get(0));
    	assertEquals(B, actualKeys.get(1));
    	assertEquals(C, actualKeys.get(2));

    	
    	//Unsafe
    	//Add more than maximum amount of keys
    	assertEquals(ERROR, to.addKey(D));
    	
    	//Add a negative valued key to array
    	to = new TreeObject(2);
    	assertEquals(ERROR, to.addKey(neg));
    }
    
    @Test
    public void testTreeNodeRemoveKey() {
    	TreeObject to = new TreeObject(2);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	
    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	to.addKey(A);
    	to.addKey(B);
    	to.addKey(C);
    	
    	//Safe
    	assertEquals(NO_ERROR, to.removeKey(B));
    	ArrayList<Long> actualKeys = to.getKeys();
    	assertEquals(A, actualKeys.get(0));
    	assertEquals(C, actualKeys.get(1));
    	
    	assertEquals(NO_ERROR, to.removeKey(C));
    	actualKeys = to.getKeys();
    	assertEquals(A, actualKeys.get(0));
    	
    	//Unsafe
    	//Remove element when there are minimum amount of keys in node
    	assertEquals(NO_ERROR, to.removeKey(A));
    	
    	//Remove element that is not in the array
    	assertEquals(ERROR, to.removeKey(D));
    }
    
    @Test
    public void testTreeNodeAddChild() {
    	TreeObject parent = new TreeObject(2);
    	TreeObject childA = new TreeObject(2);
    	TreeObject childC = new TreeObject(2);
    	TreeObject childE = new TreeObject(2);
    	TreeObject childF = new TreeObject(2);
    	TreeObject emptyChild = new TreeObject(2);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;

    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	//Instantiate parent
    	parent.addKey(B);
    	parent.addKey(D);
    	
    	//Instantiate children
    	childA.addKey(A);
    	childC.addKey(C);
    	childE.addKey(E);
    	childF.addKey(F);
    	
    	//Safe
    	assertEquals(NO_ERROR, parent.addChild(childA));
    	ArrayList<TreeObject> actualChildren = parent.getChildren();
    	assertEquals(A, actualChildren.get(0).getKeyAtIndex(0));
    	
    	assertEquals(NO_ERROR, parent.addChild(childE));
    	actualChildren = parent.getChildren();
    	assertEquals(A, actualChildren.get(0).getKeyAtIndex(0));
    	assertEquals(null, actualChildren.get(1));
    	assertEquals(E, actualChildren.get(2).getKeyAtIndex(0));
    	
    	assertEquals(NO_ERROR, parent.addChild(childC));
    	actualChildren = parent.getChildren();
    	assertEquals(A, actualChildren.get(0).getKeyAtIndex(0));
    	assertEquals(C, actualChildren.get(1).getKeyAtIndex(0));
    	assertEquals(E, actualChildren.get(2).getKeyAtIndex(0));
    	
    	//Unsafe
    	//Add more than allowable children
    	assertEquals(ERROR, parent.addChild(childF));
    	
    	//Add an empty child
    	parent = new TreeObject(2);
    	parent.addKey(B);
    	parent.addKey(D);
    	
    	assertEquals(ERROR, parent.addChild(emptyChild));
    }
    
    @Test
    public void testTreeNodeRemoveChild() {
    	TreeObject parent = new TreeObject(2);
    	TreeObject childA = new TreeObject(2);
    	TreeObject childC = new TreeObject(2);
    	TreeObject childE = new TreeObject(2);
    	TreeObject childF = new TreeObject(2);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;

    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	//Instantiate parent
    	parent.addKey(B);
    	parent.addKey(D);
    	
    	//Instantiate children
    	childA.addKey(A);
    	childC.addKey(C);
    	childE.addKey(E);
    	childF.addKey(F);
    	
    	//Add children to parent
    	parent.addChild(childA);
    	parent.addChild(childC);
    	parent.addChild(childE);
    	
    	//Safe
    	assertEquals(NO_ERROR, parent.removeChild(childC));
    	ArrayList<TreeObject> actualChildren = parent.getChildren();
    	assertEquals(A, actualChildren.get(0).getKeyAtIndex(0));
    	assertEquals(null, actualChildren.get(1));
    	assertEquals(E, actualChildren.get(2).getKeyAtIndex(0));
    	
    	assertEquals(NO_ERROR, parent.removeChild(childE));
    	actualChildren = parent.getChildren();
    	assertEquals(A, actualChildren.get(0).getKeyAtIndex(0));
    	assertEquals(null, actualChildren.get(1));
    	assertEquals(null, actualChildren.get(2));
    	
    	assertEquals(NO_ERROR, parent.removeChild(childA));
    	actualChildren = parent.getChildren();
    	assertEquals(null, actualChildren.get(0));
    	assertEquals(null, actualChildren.get(1));
    	assertEquals(null, actualChildren.get(2));
    	
    	//Unsafe
    	//Remove node from leaf
    	assertEquals(ERROR, parent.removeChild(childF));
    	
    	//Remove node that is not a child
    	parent.addChild(childA);
    	parent.addChild(childC);
    	parent.addChild(childE);
    	
    	assertEquals(ERROR, parent.removeChild(childF));
    }
    
    @Test
    public void testBTreeSplitNodeNewRoot() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	TreeObject node = new TreeObject(2);
    	
    	TreeObject newParent;
    	TreeObject leftChild;
    	TreeObject rightChild;
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	
    	node.addKey(A);
    	node.addKey(B);
    	node.addKey(C);
    	
    	newParent = tree.splitNode(node);
    	leftChild = newParent.getChildAtIndex(0);
    	rightChild = newParent.getChildAtIndex(1);
    	
    	
    	assertEquals(B, newParent.getKeyAtIndex(0));
    	assertEquals(1, newParent.getNumKeys());
    	
    	assertEquals(A, leftChild.getKeyAtIndex(0));
    	assertEquals(1, leftChild.getNumKeys());
    	
    	assertEquals(C, rightChild.getKeyAtIndex(0));
    	assertEquals(1, leftChild.getNumKeys());
   
    }
    
    @Test
    public void testBTreeSplitNodeExistingParent() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	
    	TreeObject parent = new TreeObject(2);
    	TreeObject child = new TreeObject(2);
    	
    	TreeObject newParent;
    	TreeObject leftChild;
    	TreeObject rightChild;
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;

    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	parent.addKey(A);
    	parent.addKey(E);
    	
    	child.addKey(B);
    	child.addKey(C);
    	child.addKey(D);
    	
    	parent.addChild(child);
    	
    	newParent = tree.splitNode(child);
    	leftChild = newParent.getChildAtIndex(1);
    	rightChild = newParent.getChildAtIndex(2);
    	
    	assertEquals(A, newParent.getKeyAtIndex(0));
    	assertEquals(C, newParent.getKeyAtIndex(1));
    	assertEquals(E, newParent.getKeyAtIndex(2));
    	
    	assertEquals(B, leftChild.getKeyAtIndex(0));
    	
    	assertEquals(D, rightChild.getKeyAtIndex(0));
    	
    }
    
    @Test
    public void testBTreeInsert() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;
    	Long neg = (long) -2;
    	
    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	//Safe
    	assertEquals(NO_ERROR, tree.insert(A));
    	TreeObject root = tree.getRoot();
    	assertEquals(A, root.getKeyAtIndex(0));
    	
    	assertEquals(NO_ERROR, tree.insert(E));
    	root = tree.getRoot();
    	assertEquals(A, root.getKeyAtIndex(0));
    	assertEquals(E, root.getKeyAtIndex(1));
    	
    	assertEquals(NO_ERROR, tree.insert(C));
    	root = tree.getRoot();
    	assertEquals(A, root.getKeyAtIndex(0));
    	assertEquals(C, root.getKeyAtIndex(1));
    	assertEquals(E, root.getKeyAtIndex(2));
    	
    	//Node should split
    	assertEquals(NO_ERROR, tree.insert(B));
    	root = tree.getRoot();
    	assertEquals(C, root.getKeyAtIndex(0));
    	TreeObject leftChild = root.getChildAtIndex(0);
    	assertEquals(A, leftChild.getKeyAtIndex(0));
    	assertEquals(B, leftChild.getKeyAtIndex(1));
    	TreeObject rightChild = root.getChildAtIndex(1);
    	assertEquals(E, rightChild.getKeyAtIndex(0));
    	
    	assertEquals(NO_ERROR, tree.insert(F));
    	root = tree.getRoot();
    	assertEquals(C, root.getKeyAtIndex(0));
    	leftChild = root.getChildAtIndex(0);
    	assertEquals(A, leftChild.getKeyAtIndex(0));
    	assertEquals(B, leftChild.getKeyAtIndex(1));
    	rightChild = root.getChildAtIndex(1);
    	assertEquals(E, rightChild.getKeyAtIndex(0));
    	assertEquals(F, rightChild.getKeyAtIndex(1));
    	
    	//Unsafe
    	assertEquals(ERROR, tree.insert(neg));
    	
    	assertEquals(NO_ERROR, tree.insert(A));
    	
    }
    
    @Test
    public void testBTreeSearch() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;
    	
    	//Instantiate tree
    	tree.insert(A);
    	tree.insert(B);
    	tree.insert(C);
    	tree.insert(D);
    	tree.insert(E);
    	
    	//Safe
    	TreeObject result = tree.search(A);
    	assertEquals(A, result.getKeyAtIndex(0));
    	
    	result = tree.search(B);
    	assertEquals(B, result.getKeyAtIndex(0));
    	
    	result = tree.search(C);
    	assertEquals(C, result.getKeyAtIndex(0));
    	
    	result = tree.search(D);
    	assertEquals(D, result.getKeyAtIndex(1));
    	
    	result = tree.search(E);
    	assertEquals(E, result.getKeyAtIndex(2));
    	
    	//Unsafe
    	result = tree.search(F);
    	assertEquals(null, result);
    }
    
    @Test
    public void testBTreeDeleteKeyLeft() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;
    	
    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	//Instantiate tree
    	tree.insert(F);
    	tree.insert(E);
    	tree.insert(D);
    	tree.insert(C);
    	tree.insert(B);
    	tree.insert(A);

    	//Remove from leaf that has at least t keys
    	assertEquals(NO_ERROR, tree.delete(A));
    	TreeObject root = tree.getRoot();
    	assertEquals(C, root.getKeyAtIndex(0));
    	assertEquals(E, root.getKeyAtIndex(1));
    	TreeObject child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	TreeObject child1 = root.getChildAtIndex(1);
    	assertEquals(D, child1.getKeyAtIndex(0));
    	TreeObject child2 = root.getChildAtIndex(2);
    	assertEquals(F, child2.getKeyAtIndex(0));
    	
    	//Remove from leaf that has less than t keys
    	assertEquals(NO_ERROR, tree.delete(B));
    	root = tree.getRoot();
    	assertEquals(E, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(C, child0.getKeyAtIndex(0));
    	assertEquals(D, child0.getKeyAtIndex(1));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(F, child1.getKeyAtIndex(0));
    	
    	//Remove key from root, with extra key in child
    	assertEquals(NO_ERROR, tree.delete(E));
    	root = tree.getRoot();
    	assertEquals(D, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(C, child0.getKeyAtIndex(0));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(F, child1.getKeyAtIndex(0));
    	
    	//Remove key from root, no extra keys in children
    	assertEquals(NO_ERROR, tree.delete(D));
    	root = tree.getRoot();
    	assertEquals(0, root.getNumKeys());
    	child0 = root.getChildAtIndex(0);
    	assertEquals(C, child0.getKeyAtIndex(0));
    	assertEquals(F, child0.getKeyAtIndex(1));
    	
    	//Unsafe
    	assertEquals(ERROR, tree.delete(A));
    }
    
    @Test
    public void testBTreeDeleteKeyRight() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;
    	
    	final int NO_ERROR = 0;
    	final int ERROR = -1;
    	
    	//Instantiate tree
    	tree.insert(A);
    	tree.insert(B);
    	tree.insert(C);
    	tree.insert(D);
    	tree.insert(E);
    	tree.insert(F);
    	
    	//Remove from leaf that has at least t keys
    	assertEquals(NO_ERROR, tree.delete(F));
    	TreeObject root = tree.getRoot();
    	assertEquals(B, root.getKeyAtIndex(0));
    	assertEquals(D, root.getKeyAtIndex(1));
    	TreeObject child0 = root.getChildAtIndex(0);
    	assertEquals(A, child0.getKeyAtIndex(0));
    	TreeObject child1 = root.getChildAtIndex(1);
    	assertEquals(C, child1.getKeyAtIndex(0));
    	TreeObject child2 = root.getChildAtIndex(2);
    	assertEquals(E, child2.getKeyAtIndex(0));
    	
    	//Remove from leaf that has less than t keys
    	assertEquals(NO_ERROR, tree.delete(E));
    	root = tree.getRoot();
    	assertEquals(B, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(A, child0.getKeyAtIndex(0));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(C, child1.getKeyAtIndex(0));
    	assertEquals(D, child1.getKeyAtIndex(1));
    	
    	//Remove from root that has less t keys, child with t keys
    	assertEquals(NO_ERROR, tree.delete(B));
    	root = tree.getRoot();
    	assertEquals(C, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(A, child0.getKeyAtIndex(0));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(D, child1.getKeyAtIndex(0));
    	
    	//Remove from root that has less than t keys, no child with t keys
    	assertEquals(NO_ERROR, tree.delete(C));
    	root = tree.getRoot();
    	assertEquals(0, root.getNumKeys());
    	child0 = root.getChildAtIndex(0);
    	assertEquals(A, child0.getKeyAtIndex(0));
    	assertEquals(D, child0.getKeyAtIndex(1));
    	
    	//Unsafe
    	assertEquals(ERROR, tree.delete(F));
    	
    }
    
    @Test 
    public void testBTreeDegree3() {
    	BTree<Long> tree = new BTree<Long>(3, 0);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;
    	Long G = (long) 7;
    	Long H = (long) 8;
    	Long I = (long) 9;
    	Long J = (long) 10;
    	
    	//Instantiate tree
    	tree.insert(J);
    	tree.insert(I);
    	tree.insert(H);
    	tree.insert(G);
    	tree.insert(F);
    	tree.insert(E);
    	tree.insert(D);
    	tree.insert(C);
    	tree.insert(B);
    	tree.insert(A);
    	
    	//Check Tree structure
    	TreeObject root = tree.getRoot();
    	assertEquals(E, root.getKeyAtIndex(0));
    	assertEquals(H, root.getKeyAtIndex(1));
    	TreeObject child0 = root.getChildAtIndex(0);
    	assertEquals(A, child0.getKeyAtIndex(0));
    	assertEquals(B, child0.getKeyAtIndex(1));
    	assertEquals(C, child0.getKeyAtIndex(2));
    	assertEquals(D, child0.getKeyAtIndex(3));
    	TreeObject child1 = root.getChildAtIndex(1);
    	assertEquals(F, child1.getKeyAtIndex(0));
    	assertEquals(G, child1.getKeyAtIndex(1));
    	TreeObject child2 = root.getChildAtIndex(2);
    	assertEquals(I, child2.getKeyAtIndex(0));
    	assertEquals(J, child2.getKeyAtIndex(1));
    	
    	//Check deletion
    	tree.delete(A);
    	root = tree.getRoot();
    	assertEquals(E, root.getKeyAtIndex(0));
    	assertEquals(H, root.getKeyAtIndex(1));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	assertEquals(C, child0.getKeyAtIndex(1));
    	assertEquals(D, child0.getKeyAtIndex(2));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(F, child1.getKeyAtIndex(0));
    	assertEquals(G, child1.getKeyAtIndex(1));
    	child2 = root.getChildAtIndex(2);
    	assertEquals(I, child2.getKeyAtIndex(0));
    	assertEquals(J, child2.getKeyAtIndex(1));
    	
    	tree.delete(F);
    	root = tree.getRoot();
    	assertEquals(D, root.getKeyAtIndex(0));
    	assertEquals(H, root.getKeyAtIndex(1));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	assertEquals(C, child0.getKeyAtIndex(1));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(E, child1.getKeyAtIndex(0));
    	assertEquals(G, child1.getKeyAtIndex(1));
    	child2 = root.getChildAtIndex(2);
    	assertEquals(I, child2.getKeyAtIndex(0));
    	assertEquals(J, child2.getKeyAtIndex(1));
    	
    	tree.delete(D);
    	root = tree.getRoot();
    	assertEquals(H, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	assertEquals(C, child0.getKeyAtIndex(1));
    	assertEquals(E, child0.getKeyAtIndex(2));
    	assertEquals(G, child0.getKeyAtIndex(3));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(I, child1.getKeyAtIndex(0));
    	assertEquals(J, child1.getKeyAtIndex(1));
    	
    	tree.delete(H);
    	root = tree.getRoot();
    	assertEquals(G, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	assertEquals(C, child0.getKeyAtIndex(1));
    	assertEquals(E, child0.getKeyAtIndex(2));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(I, child1.getKeyAtIndex(0));
    	assertEquals(J, child1.getKeyAtIndex(1));
    	
    	tree.delete(J);
    	root = tree.getRoot();
    	assertEquals(E, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	assertEquals(C, child0.getKeyAtIndex(1));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(G, child1.getKeyAtIndex(0));
    	assertEquals(I, child1.getKeyAtIndex(1));
    	
    	tree.delete(B);
    	root = tree.getRoot();
    	assertEquals(0, root.getNumKeys());
    	child0 = root.getChildAtIndex(0);
    	assertEquals(C, child0.getKeyAtIndex(0));
    	assertEquals(E, child0.getKeyAtIndex(1));
    	assertEquals(G, child0.getKeyAtIndex(2));
    	assertEquals(I, child0.getKeyAtIndex(3));
    }
    
    @Test
    public void testBTreeDeleteHeight3() {
    	BTree<Long> tree = new BTree<Long>(2, 0);
    	
    	Long A = (long) 1;
    	Long B = (long) 2;
    	Long C = (long) 3;
    	Long D = (long) 4;
    	Long E = (long) 5;
    	Long F = (long) 6;
    	Long G = (long) 7;
    	Long H = (long) 8;
    	Long I = (long) 9;
    	Long J = (long) 10;
    	
    	//Instantiate tree
    	tree.insert(J);
    	tree.insert(I);
    	tree.insert(H);
    	tree.insert(G);
    	tree.insert(F);
    	tree.insert(E);
    	tree.insert(D);
    	tree.insert(C);
    	tree.insert(B);
    	tree.insert(A);
    	
    	//Check tree structure
    	TreeObject root = tree.getRoot();
    	assertEquals(G, root.getKeyAtIndex(0));
    	TreeObject child0 = root.getChildAtIndex(0);
    	assertEquals(C, child0.getKeyAtIndex(0));
    	assertEquals(E, child0.getKeyAtIndex(1));
    	TreeObject child00 = child0.getChildAtIndex(0);
    	assertEquals(A, child00.getKeyAtIndex(0));
    	assertEquals(B, child00.getKeyAtIndex(1));
    	TreeObject child01 = child0.getChildAtIndex(1);
    	assertEquals(D, child01.getKeyAtIndex(0));
    	TreeObject child02 = child0.getChildAtIndex(2);
    	assertEquals(F, child02.getKeyAtIndex(0));
    	TreeObject child1 = root.getChildAtIndex(1);
    	assertEquals(I, child1.getKeyAtIndex(0));
    	TreeObject child10 = child1.getChildAtIndex(0);
    	assertEquals(H, child10.getKeyAtIndex(0));
    	TreeObject child11 = child1.getChildAtIndex(1);
    	assertEquals(J, child11.getKeyAtIndex(0));
    	
    	//Delete keys
    	tree.delete(C);
    	root = tree.getRoot();
    	assertEquals(G, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	assertEquals(E, child0.getKeyAtIndex(1));
    	child00 = child0.getChildAtIndex(0);
    	assertEquals(A, child00.getKeyAtIndex(0));
    	child01 = child0.getChildAtIndex(1);
    	assertEquals(D, child01.getKeyAtIndex(0));
    	child02 = child0.getChildAtIndex(2);
    	assertEquals(F, child02.getKeyAtIndex(0));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(I, child1.getKeyAtIndex(0));
    	child10 = child1.getChildAtIndex(0);
    	assertEquals(H, child10.getKeyAtIndex(0));
    	child11 = child1.getChildAtIndex(1);
    	assertEquals(J, child11.getKeyAtIndex(0));
    	
    	tree.delete(I);
    	root = tree.getRoot();
    	assertEquals(E, root.getKeyAtIndex(0));
    	child0 = root.getChildAtIndex(0);
    	assertEquals(B, child0.getKeyAtIndex(0));
    	child00 = child0.getChildAtIndex(0);
    	assertEquals(A, child00.getKeyAtIndex(0));
    	child01 = child0.getChildAtIndex(1);
    	assertEquals(D, child01.getKeyAtIndex(0));
    	child1 = root.getChildAtIndex(1);
    	assertEquals(G, child1.getKeyAtIndex(0));
    	child10 = child1.getChildAtIndex(0);
    	assertEquals(F, child10.getKeyAtIndex(0));
    	child11 = child1.getChildAtIndex(1);
    	assertEquals(H, child11.getKeyAtIndex(0));
    	assertEquals(J, child11.getKeyAtIndex(1));
    	
    }
   
}
