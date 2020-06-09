package saying;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class FamousSayProperty {
	private	SimpleStringProperty nameSim;
	private SimpleStringProperty contentSim;
	private SimpleStringProperty useDateSim;
	private SimpleIntegerProperty listIdSim;
	private SimpleIntegerProperty likedSim;
	private SimpleIntegerProperty noLikedSim;
	
	public FamousSayProperty(int listIdSim, String nameSim, String contentSim, String useDateSim, int likedSim, int noLikedSim) {
		this.listIdSim = new SimpleIntegerProperty(listIdSim);
		this.nameSim = new SimpleStringProperty(nameSim);
		this.contentSim = new SimpleStringProperty(contentSim);
		this.useDateSim = new SimpleStringProperty(useDateSim);
		this.likedSim = new SimpleIntegerProperty(likedSim);
		this.noLikedSim = new SimpleIntegerProperty(noLikedSim);
	}
	
	public FamousSayProperty(String nameSim, String contentSim) {
		this.nameSim = new SimpleStringProperty(nameSim);
		this.contentSim = new SimpleStringProperty(contentSim);
	}
	public SimpleStringProperty useDateSimProperty() {
		return this.useDateSim;
	}

	public String getUseDateSim() {
		return this.useDateSim.get();
	}

	public void setUseDateSim(String useDateSim) {
		this.useDateSim.set(useDateSim);
	}
	
	
	public int getListIdSim() {
		return this.listIdSim.get();
	}

	public void setListIdSim(int listIdSim) {
		this.listIdSim.set(listIdSim);
	}

	public SimpleIntegerProperty listIdSimProperty() {
		return this.listIdSim;
	}
	public int getLikedSim() {
		return this.likedSim.get();
	}

	public void setLikedSim(int likedSim) {
		this.likedSim.set(likedSim);
	}

	public SimpleIntegerProperty likedSimProperty() {
		return this.likedSim;
	}
	
	public int getNoLikedSim() {
		return this.noLikedSim.get();
	}

	public void setNoLikedSim(int noLikedSim) {
		this.noLikedSim.set(noLikedSim);
	}

	public SimpleIntegerProperty noLikedSimProperty() {
		return this.noLikedSim;
	}
	
	public String getNameSim() {
		return this.nameSim.get();
	}

	public void setNameSim(String nameSim) {
		this.nameSim.set(nameSim);
	}

	public SimpleStringProperty nameSimProperty() {
		return this.nameSim;
	}

	public SimpleStringProperty contentSimProperty() {
		return this.contentSim;
	}

	public String getContentSim() {
		return this.contentSim.get();
	}

	public void setContentSim(String contentSim) {
		this.contentSim.set(contentSim);
	}
}
