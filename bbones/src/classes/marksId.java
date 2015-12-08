package classes;

import java.util.ArrayList;
import java.util.List;

public class marksId {
	
	String rempids;
	String rempNam;
	int marks;
	String event;
	List<marksId> oldMarks = new ArrayList<marksId>();
	public List<marksId> getOldempMarks() {
		return oldMarks;
	}
	public void setOldempMarks(List<marksId> oldempMarks) {
		this.oldMarks = oldempMarks;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	
	public String getRempIDs() {
		return rempids;
	}
	public void setRempIDs(String rempIDs) {
		this.rempids = rempIDs;
	}
	
	public String getRempNam() {
		return rempNam;
	}
	public void setRempNam(String rempNam) {
		this.rempNam = rempNam;
	}


}
