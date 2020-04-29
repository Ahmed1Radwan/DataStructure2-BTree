package eg.edu.alexu.csd.filestructure.btree;

import javax.management.RuntimeErrorException;

public class BTree implements IBTree{
	
	private BTreeNode root;
	private int t;
	private boolean flg = true;
	
	public BTree(int t) {
		if(t < 2) {
			throw new RuntimeErrorException(null);
		}
		this.root=null;
		this.t=t;
	}

	@Override
	public int getMinimumDegree() {
		// TODO Auto-generated method stub
		return t;
	}

	@Override
	public IBTreeNode getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public void insert(Comparable key, Object value) {
		// TODO Auto-generated method stub
		if(key==null || value == null) {
			throw new RuntimeErrorException(null);
		}
		
		if(root == null) { // BTree is Empty
			root = new BTreeNode();
			root.getKeys().add(key);
			root.getValues().add(value);
			root.setNumOfKeys(root.getNumOfKeys() + 1);
			return;
		}
		
		if(root.getNumOfKeys() == 2*t-1) { // root is full
				
			BTreeNode newNode  = root;
			root = new BTreeNode();
			root.getChildren().add(newNode);
			splitChild((BTreeNode)root,newNode);
			root.setLeaf(false);
			
		}
		searchAndInsert(key,value,root);
	}
	
	// methods for insertion 
	private void splitChild(BTreeNode x,BTreeNode y) {
				
		BTreeNode newNode = new BTreeNode();
			
		Comparable key = (Comparable) y.getKeys().get(t - 1);
		Object value = y.getValues().get(t - 1);
		newNode.setLeaf(y.isLeaf());
		for(int i = t; i < y.getNumOfKeys(); i++) {
			newNode.getKeys().add(y.getKeys().get(i));
			newNode.getValues().add(y.getValues().get(i));
		}
		if(!y.isLeaf()) {
			for(int i = t; i <= y.getNumOfKeys(); i++) {
				newNode.getChildren().add(y.getChildren().get(i));
			}
		}
		while(y.getKeys().size() > t - 1) {
			y.getKeys().remove(y.getKeys().size()-1);
			y.getValues().remove(y.getValues().size()-1);
		}
		while(y.getChildren().size() > y.getKeys().size() + 1) {
			y.getChildren().remove(y.getChildren().size() - 1);
		}
		newNode.setNumOfKeys(newNode.getKeys().size());
		y.setNumOfKeys(t - 1);
			
		int i = 0;
		while(i < x.getNumOfKeys() && ((Comparable) x.getKeys().get(i)).compareTo(key) < 0) {
			i++;
		}
		x.getKeys().add(i, key);
		x.getValues().add(i, value);
		x.setNumOfKeys(x.getNumOfKeys() + 1);
		if(x.getChildren().size() >= 1) {
			x.getChildren().add(i + 1, newNode);
		}else {
			x.getChildren().add(newNode);
		}
				
	}
	private void searchAndInsert(Comparable key, Object value, BTreeNode node) {
		// TODO Auto-generated method stub
		int i=0;
		while(i<node.getNumOfKeys() && ((Comparable) node.getKeys().get(i)).compareTo(key) < 0)
			i++;
		
		if(i<node.getNumOfKeys() && ((Comparable) node.getKeys().get(i)).compareTo(key) == 0) {
			return;
		}
		
		if(node.isLeaf()) {
			node.getKeys().add(i,key);
			node.getValues().add(i,value);
			node.setNumOfKeys(node.getNumOfKeys()+1);
			return;
		}else {
			if(((BTreeNode)node.getChildren().get(i)).getNumOfKeys() == 2*t-1) {
				splitChild(node, (BTreeNode) node.getChildren().get(i));
				searchAndInsert(key,value,node);
			}else {
				searchAndInsert(key,value,(BTreeNode) node.getChildren().get(i));
			}
		}
	}

	@Override
	public Object search(Comparable key) {
		// TODO Auto-generated method stub
		if(key == null) throw new RuntimeErrorException(null);
		if(root == null) return null;
		return findd(key,root);
	}

	private Object findd(Comparable key,BTreeNode root) {
		int i = 0;
		while(i < root.getNumOfKeys() && ((Comparable) root.getKeys().get(i)).compareTo(key) < 0) {
			i++;
		}
		if(i < root.getNumOfKeys() && ((Comparable) root.getKeys().get(i)).compareTo(key) == 0) {
			return root.getValues().get(i);
		}
		if(root.isLeaf()) {
			return null;
		}
		return findd(key, (BTreeNode) root.getChildren().get(i));
	}
	
	@Override
	public boolean delete(Comparable key) {
		// TODO Auto-generated method stub
		if(key == null) throw new RuntimeErrorException(null);
		
		if(root == null) {
			return false;
		}
		remove(root,key);
		if(root.getNumOfKeys() == 0) {
			
			if(root.isLeaf())
				root = null;
			else
				root = (BTreeNode)root.getChildren().get(0);
			
			
		}
		
		if (flg)
			return true;
		else
			return false;
	}
	
	// all methods for deletion
	
		private void remove(BTreeNode root,Comparable key) {
			// TODO Auto-generated method stub
			int idx = findKey(root, key); 
		    // The key to be removed is present in this node 
		    if (idx < root.getNumOfKeys() && ((Comparable) root.getKeys().get(idx)).compareTo(key) == 0) 
		    {  
		        if (root.isLeaf()) 
		            removeFromLeaf(root, idx); 
		        else
		            removeFromNonLeaf(root, idx); 
		    }  
		    else
		    {  
		        if (root.isLeaf()) 
		        { 
		        	flg = false;
		            return ; 
		        } 
		        boolean flag = ( (idx== root.getNumOfKeys())? true : false ); 
		        if (   ((BTreeNode) root.getChildren().get(idx)).getNumOfKeys() < t) 
		            fill(root, idx); 
		        if (flag && idx > root.getNumOfKeys()) 
		            remove((BTreeNode) root.getChildren().get(idx - 1), key); 
		        else
		        	remove((BTreeNode) root.getChildren().get(idx), key); 
		    } 
		    return;
		}
		
		private void fill(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			// If the previous child(C[idx-1]) has more than t-1 keys, borrow a key 
		    // from that child 
		    if (idx != 0 && ((BTreeNode) root2.getChildren().get(idx -1)).getNumOfKeys() >= t) 
		        borrowFromPrev(root2, idx); 
		  
		    // If the next child(C[idx+1]) has more than t-1 keys, borrow a key 
		    // from that child 
		    else if (idx!= root2.getNumOfKeys() && ((BTreeNode) root2.getChildren().get(idx+1)).getNumOfKeys() >= t) 
		        borrowFromNext(root2, idx); 
		  
		    // Merge C[idx] with its sibling 
		    // If C[idx] is the last child, merge it with with its previous sibling 
		    // Otherwise merge it with its next sibling 
		    else
		    { 
		        if (idx != root2.getNumOfKeys()) 
		            merge(root2, idx); 
		        else
		            merge(root2, idx-1); 
		    } 
		    return;
		}

		private void borrowFromNext(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			BTreeNode child = (BTreeNode) root2.getChildren().get(idx); 
			BTreeNode sibling = (BTreeNode) root2.getChildren().get(idx+1); 
			    // Setting child's first key equal to keys[idx-1] from the current node 
			child.getKeys().add(root2.getKeys().get(idx));   
			child.getValues().add(root2.getValues().get(idx)); 
			root2.getKeys().remove(idx);
			root2.getValues().remove(idx);
			    // Moving sibling's last child as C[idx]'s first child 
			if(!child.isLeaf()) {
			   child.getChildren().add(sibling.getChildren().get(0));
			   sibling.getChildren().remove(0);
			}
			  
			    // Moving the key from the sibling to the parent 
			    // This reduces the number of keys in the sibling 
			
			root2.getKeys().add(idx, sibling.getKeys().get(0));
			root2.getValues().add(idx, sibling.getValues().get(0));
			
		    sibling.getKeys().remove(0);
		    sibling.getValues().remove(0);
		    sibling.setNumOfKeys(sibling.getKeys().size());
		    child.setNumOfKeys(child.getKeys().size());
			return;
			
		}

		private void borrowFromPrev(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			BTreeNode child = (BTreeNode) root2.getChildren().get(idx); 
			BTreeNode sibling = (BTreeNode) root2.getChildren().get(idx-1); 
			    // Setting child's first key equal to keys[idx-1] from the current node 
			child.getKeys().add(0, root2.getKeys().get(idx - 1));   
			child.getValues().add(0, root2.getValues().get(idx - 1)); 
			root2.getKeys().remove(idx-1);
			root2.getValues().remove(idx-1);
			    // Moving sibling's last child as C[idx]'s first child 
			if(!child.isLeaf()) {
			   child.getChildren().add(0, sibling.getChildren().get(sibling.getKeys().size()));
		       sibling.getChildren().remove(sibling.getKeys().size());
			}
			  
			    // Moving the key from the sibling to the parent 
			    // This reduces the number of keys in the sibling 
			
			root2.getKeys().add(idx-1, sibling.getKeys().get(sibling.getKeys().size() - 1));
			root2.getValues().add(idx-1, sibling.getValues().get(sibling.getKeys().size() - 1));
			
		    sibling.getKeys().remove(sibling.getKeys().size() - 1);
		    sibling.getValues().remove(sibling.getKeys().size() - 1);
		    sibling.setNumOfKeys(sibling.getKeys().size());
		    child.setNumOfKeys(child.getKeys().size());
			    return; 
		}

		private void removeFromNonLeaf(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			
		    Comparable k = (Comparable) root2.getKeys().get(idx); 
		    
		    if (((BTreeNode) root2.getChildren().get(idx)).getNumOfKeys() >= t) 
		    { 
		    	BTreeNode pred = getPred(root2, idx); 
		        root2.getKeys().set(idx, pred.getKeys().get(pred.getNumOfKeys()-1)); 
		        root2.getValues().set(idx, pred.getValues().get(pred.getNumOfKeys()-1)); 
		        remove((BTreeNode)root2.getChildren().get(idx),(Comparable) pred.getKeys().get(pred.getNumOfKeys()-1)); 
		    } 
		    
		    else if  (((BTreeNode) root2.getChildren().get(idx+1)).getNumOfKeys() >= t) 
		    { 
		    	BTreeNode succ = getSucc(root2, idx); 
		    	root2.getKeys().set(idx, succ.getKeys().get(0)); 
		    	root2.getValues().set(idx, succ.getValues().get(0)); 
		    	remove((BTreeNode)root2.getChildren().get(idx+1), (Comparable) succ.getKeys().get(0));  
		    } 
		    
		
		   	else
		    { 
		   			
		            merge(root2, idx); 
		            remove((BTreeNode)root2.getChildren().get(idx),(Comparable) k); 
		    } 
		    return; 
		    
		}

		private void merge(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			BTreeNode child = (BTreeNode) root2.getChildren().get(idx);
			BTreeNode sibling = (BTreeNode) root2.getChildren().get(idx+1); 
		  
		    // Pulling a key from the current node and inserting it into (t-1)th 
		    // position of C[idx]
		    child.getKeys().add(root2.getKeys().get(idx));
		    child.getValues().add(root2.getValues().get(idx));
		    child.getKeys().addAll(sibling.getKeys());
		    child.getValues().addAll(sibling.getValues());
		    
		    if (!child.isLeaf()) {
		    	child.getChildren().addAll(sibling.getChildren());	
		    }
		 
		  
		  root2.getKeys().remove(idx);
		  root2.getValues().remove(idx);
		  root2.getChildren().remove(idx+1);
		   // Updating the key count of child and the current node 
		  root2.setNumOfKeys(root2.getKeys().size());
		  child.setNumOfKeys(child.getKeys().size());
		  
		    return;
		}

		private BTreeNode getSucc(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			// Keep moving to the right most node until we reach a leaf 
			BTreeNode cur = (BTreeNode) root2.getChildren().get(idx+1);
		    while (!cur.isLeaf()) 
		        cur = (BTreeNode) cur.getChildren().get(0); 
		    // Return the last key of the leaf 
		    return  cur;
		}

		private BTreeNode getPred(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			// Keep moving to the right most node until we reach a leaf 
			BTreeNode cur = (BTreeNode) root2.getChildren().get(idx);
		    while (!cur.isLeaf()) 
		        cur = (BTreeNode) cur.getChildren().get(cur.getNumOfKeys()); 
		  
		    // Return the last key of the leaf 
		    return  cur;
		}

		private void removeFromLeaf(BTreeNode root2, int idx) {
			// TODO Auto-generated method stub
			root2.getKeys().remove(idx);
			root2.getValues().remove(idx);
			root2.setNumOfKeys(root2.getKeys().size());
		}

		private int findKey(BTreeNode node, Comparable k) 
		{ 
		    int idx=0; 
		    while (idx< node.getNumOfKeys() && ((Comparable)node.getKeys().get(idx)).compareTo(k) < 0) 
		        ++idx; 
		    return idx; 
		} 
		


}
