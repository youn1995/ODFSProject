package saying;

import javafx.beans.property.SimpleStringProperty;

public class FamousSayProperty {
	public	SimpleStringProperty nameSim;
	public SimpleStringProperty contentSim;
	
	public FamousSayProperty(String nameSim, String contentSim) {
		this.nameSim = new SimpleStringProperty();
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
