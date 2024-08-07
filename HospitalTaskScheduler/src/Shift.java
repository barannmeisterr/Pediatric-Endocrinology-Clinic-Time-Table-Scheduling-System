/*Title : Shift class
 * Author:Arda Baran
 * Description: This class represents a shift in Pediatric Endocrinology Clinic.Each shift has shift id in order to be represented
 * in directed graph.Each shift has name.

 */
public class Shift {
	int shiftId;//shift id
	String shiftName;	
	public Shift(int shiftId) {
		this.shiftName="";
	    this.shiftId=shiftId;	    		 
	}
	public int getShiftId() {
		return shiftId;
	}
	public void setShiftId(int shiftId) {
		this.shiftId = shiftId;
	}
	public String getShiftName() {
		return shiftName;
	}
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}
}