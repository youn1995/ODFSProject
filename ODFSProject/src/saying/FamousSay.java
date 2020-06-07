package saying;

import javafx.beans.property.SimpleStringProperty;

public class FamousSay {
	
	private int listId;
//	private SimpleStringProperty nameSim;
//	private SimpleStringProperty contentSim;
	private String name;
	private String content;
	private int liked;
	private int noliked;
	
//	public FamousSay() {
//		// TODO Auto-generated constructor stub
//	}
//	
//	public FamousSay(String nameSim, String contentSim) {
//		this.nameSim = new SimpleStringProperty(nameSim);
//		this.contentSim = new SimpleStringProperty(contentSim);
//	}
//	
//	public String getNameSim() {
//		return this.nameSim.get();
//	}
//	public void setNameSim(String nameSim) {
//		this.nameSim.set(nameSim);
//	}
//	public SimpleStringProperty nameSimProperty() {
//		return this.nameSim;
//	}
//	public SimpleStringProperty contentSimProperty() {
//		return this.contentSim;
//	}
//	public String getContentSim() {
//		return this.contentSim.get();
//	}
//	public void setContentSim(String contentSim) {
//		this.contentSim.set(contentSim);
//	}
	
	public int getLiked() {
		return liked;
	}
	public void setLiked(int liked) {
		this.liked = liked;
	}
	public int getNoliked() {
		return noliked;
	}
	public void setNoliked(int noliked) {
		this.noliked = noliked;
	}
	public int getListId() {
		return listId;
	}
	public void setListId(int listId) {
		this.listId = listId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	

}
