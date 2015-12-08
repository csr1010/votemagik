package classes;

import java.util.ArrayList;
import java.util.List;

public class Params {
	
	String rempID;
	String rempNam;
	int marks;
	String[] mainevents;
	List<marksId> empMarks = new ArrayList<marksId>();
	
	
	public List<marksId> getEmpMarks() {
		return empMarks;
	}
	public void setEmpMarks(List<marksId> empMarks) {
		this.empMarks = empMarks;
	}
	
	public List<marksId> getParamsList() {
		return empMarks;
	}
	public void setParamsList(List<marksId> empMarks) {
		this.empMarks = empMarks;
	}
	
	
	
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public String[] getMainevents() {
		return mainevents;
	}
	public void setMainevents(String[] mainevents) {
		this.mainevents = mainevents;
	}

	
	public String getRempID() {
		return rempID;
	}
	public void setRempID(String rempID) {
		this.rempID = rempID;
	}
	public String getRempNam() {
		return rempNam;
	}
	public void setRempNam(String rempNam) {
		this.rempNam = rempNam;
	}
	public String[] getId() {
		return mainevents;
	}
	public void setId(String[] mainevents) {
		this.mainevents = mainevents;
	}
	


}
