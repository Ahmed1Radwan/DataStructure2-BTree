package eg.edu.alexu.csd.filestructure.btree;

import java.io.Serializable;
import java.util.ArrayList;

public class InfoOfData implements Serializable{

	private ArrayList<ISearchResult> infoOfData = new ArrayList<ISearchResult>();
	
	public void addID(String id) {
		boolean found = false;
		for(ISearchResult it : infoOfData) {
			if(it.getId().equals(id)) {
				it.setRank(it.getRank()+1);
				found = true;
				break;
			}
		}
		if(!found) {
			infoOfData.add(new SearchResult(id,1));
		}
	}
	
	public ArrayList<ISearchResult> getInfoOfData(){
		return infoOfData;
	}
	
	public void deleteID(String id) {
		ArrayList<ISearchResult> dataToDelete = new ArrayList<>();
		for(ISearchResult it : infoOfData) {
			if(it.getId().equals(id)) {
				dataToDelete.add(it);
			}
		}
		for(ISearchResult it : dataToDelete) {
			infoOfData.remove(it);
		}
	}
	
}
