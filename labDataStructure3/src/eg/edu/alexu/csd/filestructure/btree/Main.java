package eg.edu.alexu.csd.filestructure.btree;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IBTree bTree = new BTree(3);
		bTree.insert('J', "22");
		bTree.insert('K', "22");
		bTree.insert('L', "22");
		bTree.insert('M', "22");
		bTree.insert('N', "22");
		bTree.insert('O', "22");
		bTree.insert('P', "22");
		bTree.insert('Q', "22");
		bTree.insert('R', "22");
		bTree.insert('A', "5555");
		bTree.insert('B', "22");
		bTree.insert('C', "22");
		bTree.insert('D', "22");
		bTree.insert('E', "22");
		bTree.insert('F', "22");
		bTree.insert('G', "22");
		bTree.insert('S', "22");
		bTree.insert('T', "22");
		bTree.insert('U', "22");
		bTree.insert('V', "22");
		bTree.insert('X', "22");
		bTree.insert('Y', "22");
		bTree.insert('Z', "22");
		
		System.out.println(bTree.delete('F'));
		System.out.println(bTree.search('F'));
		
		System.out.println(bTree.delete('M'));
		System.out.println(bTree.search('M'));
		
		System.out.println(bTree.delete('G'));
		System.out.println(bTree.search('G'));
		
		System.out.println(bTree.delete('D'));
		System.out.println(bTree.search('D'));
		
		System.out.println(bTree.delete('B'));
		System.out.println(bTree.search('B'));
		
	}

}
