package eg.edu.alexu.csd.filestructure.btree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SearchEngine implements ISearchEngine{
	
	private IBTree<String,InfoOfData> btree;
	private ArrayList<String> FilesNames = new ArrayList<>();
	
	public SearchEngine(int t) {
		btree = new BTree(t);
	}

	@Override
	public void indexWebPage(String filePath) {
		// TODO Auto-generated method stub
		if(filePath == null || filePath == "")
			throw new RuntimeErrorException(null);
		
		filePath = filePath.trim();
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filePath));
			NodeList nodeList = doc.getElementsByTagName("doc");
			
			for(int i = 0;i < nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String id = element.getAttribute("id");
					String text = element.getTextContent();
					text = text.toLowerCase();
					addText(id,text);
				}
			}
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void addText(String id, String text) {
		// TODO Auto-generated method stub
		String[] splited = text.split("\\W+");
		for(String str: splited) {
			InfoOfData data;
			str = str.trim();
			if(str.length() == 0) continue;
			if(btree.search(str) == null) {
				data = new InfoOfData();
				btree.insert(str, data);
			}else {
				data = btree.search(str);
			}
			data.addID(id);
		}
	}

	@Override
	public void indexDirectory(String directoryPath) {
		// TODO Auto-generated method stub
		if(directoryPath==null || directoryPath=="")
			throw new RuntimeErrorException(null);
		
		directoryPath = directoryPath.trim();
		File file = new File(directoryPath);
		if(!file.exists())
			throw new RuntimeErrorException(null);
		
		File[] files = null;
		getFilesNames(directoryPath,files);
		for(String str : FilesNames) {
			indexWebPage(str);
		}
	}

	private void getFilesNames(String directoryPath, File[] files) {
		// TODO Auto-generated method stub
		File folder = new File(directoryPath);
		files = folder.listFiles();
		for(int i=0;i<files.length;i++) {
			if(files[i].isFile()) {
				FilesNames.add(directoryPath+"//"+files[i].getName());
			}else if(files[i].isDirectory()) {
				getFilesNames(directoryPath+"//"+files[i].getName(),files);
			}
		}
	}

	@Override
	public void deleteWebPage(String filePath) {
		// TODO Auto-generated method stub
		if(filePath==null || filePath== "")
			throw new RuntimeErrorException(null);
		
		filePath = filePath.trim();
		File file = new File(filePath);
		if(!file.exists())
			throw new RuntimeErrorException(null);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			Document doc = builder.parse(new File(filePath));
			NodeList nodeList = doc.getElementsByTagName("doc");
			
			for(int i=0;i<nodeList.getLength();i++) {
				Node node = nodeList.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String id = element.getAttribute("id");
					String text = element.getTextContent();
					text = text.trim();
					text=text.toLowerCase();
					deleteText(id,text);
				}
			}
		}catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void deleteText(String id, String text) {
		// TODO Auto-generated method stub
		text = text.toLowerCase();
		text= text.trim();
		String[] splited = text.split("\\W+");
		for(String str:splited) {
			InfoOfData data;
			str = str.trim();
			if(str.length() == 0) continue;
			if(btree.search(str) == null)
				continue;
			else
				data = btree.search(str);
			
			data.deleteID(id);
			if(data.getInfoOfData().size() == 0)
				btree.delete(str);
		}
	}

	@Override
	public List<ISearchResult> searchByWordWithRanking(String word) {
		// TODO Auto-generated method stub
		if(word == null)
			throw new RuntimeErrorException(null);
		
		word =word.trim();
		word=word.toLowerCase();
		InfoOfData data = btree.search(word);
		if(data == null)
			data = new InfoOfData();
			
		return data.getInfoOfData();
	}

	@Override
	public List<ISearchResult> searchByMultipleWordWithRanking(String sentence) {
		// TODO Auto-generated method stub
		if(sentence == null)
			throw new RuntimeErrorException(null);
		
		sentence=sentence.trim();
		List<ISearchResult> ans = new ArrayList<>();
		sentence=sentence.toLowerCase();
		String[] splited = sentence.split("\\W+");
		if(splited.length == 0)
			return ans;
		
		ans = searchByWordWithRanking(splited[0]);
		for(int i=1;i<splited.length;i++) {
			String str = splited[i];
			List<ISearchResult> list = searchByWordWithRanking(str);
			List<ISearchResult> tmp = new ArrayList<>();
			
			for(ISearchResult it_1 : ans) {
				for(ISearchResult it_2 : list) {
					if(it_1.getId().equals(it_2.getId())) {
						tmp.add(new SearchResult(it_1.getId(),Math.min(it_1.getRank(), it_2.getRank())));
					}
				}
			}
			ans = tmp;
		}
		return ans;
	}

}
