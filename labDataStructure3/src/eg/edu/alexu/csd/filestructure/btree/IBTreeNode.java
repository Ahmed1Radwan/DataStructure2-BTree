package eg.edu.alexu.csd.filestructure.btree;

import java.util.List;
public interface IBTreeNode<K extends Comparable<K>,V> {
	
	//return the numOfKeys return number of keys in this node .
	
	public int getNumOfKeys( );
	
	
	//@param numOfKeys
	
	public void setNumOfKeys( int numOfKeys ) ;
	
	// @return i s L e af i f the node i s l e a f o r not .
	
	public boolean isLeaf ( ) ;
	
	// @param i s L e af
	
	public void setLeaf(boolean isLeaf) ;
	
	// return the key s r e t u r n the l i s t of key s of the gi v e n node
	
	
	public List<K> getKeys ( ) ;
	
	// @param key s the key s to s e t
	
	public void setKeys(List<K> keys);
	
	// @return the v al u e s r e t u r n the l i s t of v al u e s f o r theg i v e n node .

	public List<V> getValues ( ) ;
	
	// @param v al u e s the v al u e s to s e t
	
	public void setValues ( List<V> values ) ;
	
	// @return the c h i l d r e n r e t u r n the l i s t of c h i l d r e n f o r the gi v e n node .
	
	public List<IBTreeNode<K, V>> getChildren ( ) ;
	
	// @param c h i l d r e n the c h i l d r e n to s e t
	
	public void setChildren( List<IBTreeNode<K, V>> children ) ;
}
