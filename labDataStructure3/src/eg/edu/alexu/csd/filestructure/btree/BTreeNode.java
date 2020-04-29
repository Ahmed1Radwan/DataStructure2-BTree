package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BTreeNode implements IBTreeNode{
	
	private ArrayList<Comparable> keys = new ArrayList<Comparable>(); // keys in node
	private ArrayList<Object> values = new ArrayList<Object>();
	protected int t; // minDegree 
	private ArrayList<BTreeNode> children = new ArrayList<BTreeNode>(); // children'pointers
	protected int n = 0; // current number of keys
	private boolean leaf = true; // is this node a leaf node or not

	
	@Override
	public int getNumOfKeys() {
		// TODO Auto-generated method stub
		return n;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		// TODO Auto-generated method stub
		this.n=numOfKeys;
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return leaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		// TODO Auto-generated method stub
		this.leaf=isLeaf;
	}

	@Override
	public List getKeys() {
		// TODO Auto-generated method stub
		return keys;
	}

	@Override
	public void setKeys(List keys) {
		// TODO Auto-generated method stub
		this.keys=(ArrayList<Comparable>) keys;
	}

	@Override
	public List getValues() {
		// TODO Auto-generated method stub
		return values;
	}

	@Override
	public void setValues(List values) {
		// TODO Auto-generated method stub
		this.values= (ArrayList<Object>) values;
	}

	@Override
	public List getChildren() {
		// TODO Auto-generated method stub
		return children;
	}

	@Override
	public void setChildren(List children) {
		// TODO Auto-generated method stub
		this.children=(ArrayList<BTreeNode>) children;
	}

	

}
