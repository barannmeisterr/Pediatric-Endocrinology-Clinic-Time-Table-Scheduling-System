
import java.util.*;
/*Title : Doctors class
 * Author:Arda Baran
 * Description: This class represents a Pediatric Endocrinology Clinic Doctor.Each doctor has doctorId in order to
 * be represented on directed graph.Each doctor has nameSurname,the number of polikinlik registered,the statuses 
 * that indicates the doctor registered to poliklinik 1 ,the doctor registered to poliklinik 2 , the doctor registered to
   poliklinik 3.
 */


public class Doctors {
int doctorId;
String nameSurname;
int numOfPolikinlikDoctorHas;
boolean polikinlik1Status,polikinlik2Status,polikinlik3Status;
public Doctors(int doctorId) {
	this.doctorId=doctorId;
	this.nameSurname="";
	this.numOfPolikinlikDoctorHas=0;
	this.polikinlik1Status=false;
	this.polikinlik2Status=false;
	this.polikinlik3Status=false;
}
public boolean isPolikinlik1Status() {
	return polikinlik1Status;
}
public void setPolikinlik1Status(boolean polikinlik1Status) {
	this.polikinlik1Status = polikinlik1Status;
}
public boolean isPolikinlik2Status() {
	return polikinlik2Status;
}
public void setPolikinlik2Status(boolean polikinlik2Status) {
	this.polikinlik2Status = polikinlik2Status;
}
public boolean isPolikinlik3Status() {
	return polikinlik3Status;
}
public void setPolikinlik3Status(boolean polikinlik3Status) {
	this.polikinlik3Status = polikinlik3Status;
}
public String getNameSurname() {
	return nameSurname;
}
public void setNameSurname(String nameSurname) {
	this.nameSurname = nameSurname;
}
public int getDoctorId() {
	return doctorId;
}
public void setDoctorId(int doctorId) {
	this.doctorId = doctorId;
}
public int getNumOfPolikinlikDoctorHas() {
	return numOfPolikinlikDoctorHas;
}
public void setNumOfPolikinlikDoctorHas(int numOfPolikinlikDoctorHas) {
	this.numOfPolikinlikDoctorHas = numOfPolikinlikDoctorHas;
}
}