package eg.edu.alexu.csd.filestructure.btree;

import java.io.Serializable;

public class SearchResult implements ISearchResult,Serializable{
	
	String ID;
	int rank;
	public SearchResult(String ID, int rank) {
		// TODO Auto-generated constructor stub
		this.ID = ID;
		this.rank = rank;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return ID;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.ID=id;
	}

	@Override
	public int getRank() {
		// TODO Auto-generated method stub
		return rank;
	}

	@Override
	public void setRank(int rank) {
		// TODO Auto-generated method stub
		this.rank=rank;
	}

}
