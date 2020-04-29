package eg.edu.alexu.csd.filestructure.btree;

public interface IBTree<K extends Comparable<K>,V> {
	
	//Return the minimum d eg r e e of the gi v e n B t ree .
	//The minimum d eg r e e of the B t ree i s s e n t a s a parameter t
	//the c o n s t r u c t o r .

	public int getMinimumDegree ( ) ;
	
	// Return the roo t of the gi v e n B t ree .
	
	public IBTreeNode<K, V> getRoot ( ) ;
	
	// I n s e r t the gi v e n key i n the B t ree . I f the key i s al r e a d y
	//in the Btree , i g n o r e the c a l l of t h i s method .
	
	
	public void insert (K key , V value ) ;
	
	// Sea rch f o r the gi v e n key i n the BTree .

	public V search (K key ) ;
	
	// D el e t e the node with the gi v e n key from the B t ree .
	// Return t r u e i n ca s e of s u c c e s s and f a l s e o t h e r wi s e .
	
	public boolean delete (K key ) ;
}
