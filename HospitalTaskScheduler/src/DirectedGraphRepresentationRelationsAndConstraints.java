import java.util.Random;
import java.util.Scanner;
/*
 * Title: DirectedGraphRepresentationRelationsAndConstraints Class
 * Author: Arda Baran
 * Description: This class represents the complex relationships and constraints between doctors and their shifts using a
 *  Directed Graph data structure.The purpose of this class is to assign doctors to shifts by considering their polikinlik 
 * preferences.
 * The directed graph is a crucial element in modeling the intricate scheduling requirements for doctors. Each node in the graph 
 * represents doctors and shifts,
 * while each directed edge represents the assignment of a doctor to that particular shift, respecting various constraints.
 * 
 * Relationships between doctors and shifts are represented as two dimensional boolean adjancency matrix.
 * ( boolean adjMatrixForConnections [number of doctors][number of shifts] = adjMatrixForConnections [6][8] 
 * for example adjMatrixForConnections[0][0] means that Dr TANER BARAN assigned to poliklinik 1
 *               adjMatrixForConnections[2][3] means that YUSUF CÜREK assigned to Yenidoğan Nöbeti
 * 
 * Each doctor must select at least one polikinlik to work between polikinlik 1 ,polikinlik 2 and polikinlik 3 .
 * After doctors selecting their total polikinlik number updated.for example if TANER BARAN only want to work at
 * polikinlik 1 , his total polikinlik number is 1 ,if SERPİL BAŞ want to polikinlik 1 and polikinlik 2 ,her total polikinlik
 * number is 2.
 * The importance of the Directed Graph data structure for this problem cannot be overstated. It allows for efficient modeling of:
 * - Shift Assignments: Ensuring each doctor is assigned to appropriate shifts while avoiding conflicts.
 * - Constraints: Incorporating various constraints such as non-overlapping shifts, rest periods, and specific shift preferences.
 * - Relationships: Representing the relationships between consecutive shifts and the impact of one shift on another, especially for critical shifts like Yenidoğan (Neonatal).
 * 
 * Constant Doctors:
 * - TANER BARAN --- Doctor ID:0
 * - SERPİL BAŞ  --- Doctor ID:1
 * - YUSUF CÜREK --- Doctor ID:2
 * - GAMZE ÇELMELİ---Doctor ID:3
 * - MÜGE ATAR    --- Doctor ID:4
 * - DOĞA TÜRKKAHRAMAN --- Doctor ID: 5
 * 
 * Constant Shifts:
 * - POLİKLİNİK 1 ---Shift ID:0
 * - POLİKLİNİK 2 ---Shift ID:1 
 * - POLİKLİNİK 3 ---Shift ID:2
 * - YENİDOĞAN NÖBETİ (Neonatal Duty) --- Shift ID:3
 * - İCAP (On-Call) ---Shift ID:4
 * - KONSÜLTASYON (Consultation) ---Shift ID:5
 * - SERVİS (Service) ---Shift ID:6
 * - ENDOKRİN (Endocrine) ---Shift ID:7
 * 
 * Rules:
 * Poliklinik 1 , Poliklinik 2 , Poliklinik 3 , Kons  , Service And Endokrin ici shifts are only assigned on weekdays.
 * (MONDAY , TUESDAY, WEDNESDAY ,THURSDAY and FRIDAY)
 * Icap shift is assigned all days in the month , so there is always icap nöbet in every day
 * On the weekends,only icap shift can be assigned
 * Yenidogan shift can be assigned any day without considering weekend or weekday.The day of yenidogan days and doctors
 * are previously determined by user or hospital management so there is no randomly choice for yenidogan 
 * A doctor cannot be assigned more than one polikinlik at the same time
 * A doctor cannot be assigned to icap if the doctor assigned to yenidogan nöbeti at the same time
 * A doctor cannot be assigned to any shifts if the doctor assigned to yenidoğan on previous day which means the doctor 
 * who assigned to yenidoğan shift cannot work on the next day,he or she takes vacation for next day
 * A doctor can be assigned to kons if the doctor not assigned to pol1 or pol2 or pol 3 at the same day
 * A doctor can be assigned to service if the doctor not assigned to pol1 or pol2 or pol 3 or kons at the same day
 * A doctor can be assigned to endokrin ici if the doctor  assigned to pol1 or pol2 or pol 3 at the same day
 * Constraints:
 * 
 * 
 * 
 * One of the doctor between the doctors that prefers to work at poliklinik 1 randomly chosen and assigned to polikinlik 1 
 * One of the doctor between the doctors that prefers to work at poliklinik 2 randomly chosen and assigned to polikinlik 2 
 * (The constraint is the doctor can not assigned to two polikinlik shifts at the same day. for example if serpil is assigned
 * to poliklinik 1 already,serpil cannot be chosen for poliklinik 3)
 *
 * One of the doctor between the doctors that prefers to work at poliklinik 3 randomly chosen and assigned to polikinlik 3 
  * (The constraint is the doctor can not assigned to two polikinlik shifts at the same day. for example if a doctor who registered
  * to pol3 and at the same same time registered to pol1 or pol2 and assigned to pol1 or pol2 cannot be chosen.)
 * Yenidogan Nöbeti is not randomly choosen and assigned shift.The day and doctors are determined at the beginning of the month   
 * One of the Doctor is choosen for İcap shift . If the doctor on Yenidogan shift duty on yesterday or today cannot be selected  
 * for İcap.
 * For Kons shift , one of the doctor between the doctors that not assigned to polikinlik 1 or polikinlik 2 or polikinlik 3 
 * is selected for kons shift.
 * For example , TANER BARAN is assigned to pol 1 , Yusuf Cürek assigned to pol2 and Doga Türkkahraman assigned to pol3.
 * We can say that the doctors who not assigned to polikinlik shifts are Gamze,Müge and Serpil .One doctor is randomly selected 
 * for kons shift between Gamze ,Müge And Serpil.
 
 * For Service shift , one of the doctor between the doctors that not assigned to polikinlik 1 or polikinlik 2 or polikinlik 3 
 * is selected for Service shift.
 * For example , TANER BARAN is assigned to pol 1 , Yusuf Cürek assigned to pol2 and Doga Türkkahraman assigned to pol3.
 * We can say that the doctors who not assigned to polikinlik shifts are Gamze,Müge and Serpil .One doctor is randomly selected 
 * for kons shift between Gamze ,Müge And Serpil.Suppose that Serpil is choosen for Kons.There are two Doctors left for assigning
 * to service shift whose are müge and gamze because serpil can not be chosen for service shift 
 * .on of them randomly choosen and assigned to Service shift.
 * 
 * For Endokrin ici Shift ,one of the doctor between the doctors that  assigned to polikinlik 1 or polikinlik 2 or polikinlik 3 
 * is selected for Endokrin shift.
 * For example , TANER BARAN is assigned to pol 1 , Yusuf Cürek assigned to pol2 and Doga Türkkahraman assigned to pol3.
 * We can say that the doctors who  assigned to polikinlik shifts are Taner,Yusuf and Doga .One doctor is randomly selected 
 * for endokrin shift between taner ,yusuf And doga.  
 * - Specific doctors have preferences or restrictions for certain shifts.
 * - Neonatal Duty (Yenidoğan Nöbeti) requires special handling as it influences other shift assignments on consecutive days.
 * 
 * The Directed Graph approach ensures that all these aspects are handled efficiently, allowing for optimal and feasible scheduling of doctors.
 */
public class DirectedGraphRepresentationRelationsAndConstraints {
	Doctors[] doctors;
	Shift [] shifts;
	Scanner sc;
	boolean adjMatrixForConnections[][];//connections between doctors and shifts
	
	public DirectedGraphRepresentationRelationsAndConstraints() {
	  
	    this.doctors = new Doctors[6];
	    this.shifts = new Shift[8];
	    this.adjMatrixForConnections = new boolean[6][8];

	    for (int i = 0; i < doctors.length; i++) {
	        doctors[i] = new Doctors(i);
	    }
	    
	    doctors[0].setNameSurname("TANER BARAN");
	    
	    doctors[1].setNameSurname("SERPİL BAŞ");
	    
	    doctors[2].setNameSurname("YUSUF CÜREK");
	   
	    doctors[3].setNameSurname("GAMZE ÇELMELİ");
	    
	    doctors[4].setNameSurname("MÜGE ATAR");
	    
	    doctors[5].setNameSurname("DOĞA TÜRKKAHRAMAN");
	   

	    for (int i = 0; i < shifts.length; i++) {
	        shifts[i] = new Shift(i);
	    }

	    shifts[0].setShiftName("POLİKLİNİK 1");
	 
	    shifts[1].setShiftName("POLİKLİNİK 2");
	   
	    shifts[2].setShiftName("POLİKLİNİK 3");
	   
	    shifts[3].setShiftName("YENİDOĞAN NÖBET");

	    shifts[4].setShiftName("İCAP");
	    
	    shifts[5].setShiftName("KONSÜLTASYON");

	    shifts[6].setShiftName("SERVİS");
	  
	    shifts[7].setShiftName("ENDOKRİN İÇİ");
	    sc=new Scanner(System.in);
	}
	public void addEdge(Doctors doctor,Shift shift) {
		//-----------------------------------------------------------------------
		//Summary:A doctor is assigned to a shift.Adds connection which means edge between doctor and shift	
		//-----------------------------------------------------------------------		
				if(doctor==null || shift==null) {
				return ;
			}	
			adjMatrixForConnections[doctor.getDoctorId()][shift.getShiftId()]=true;	

			}			

	public Shift getSpecificShift(String name) {
//----------------------------------------------------
/*Summary:takes string parameter which is the name of the shift then returns that shift object.
 * 		
 */
//-------------------------------------------------------------------------		
		
		switch(name) {
		case "POLİKLİNİK 1":
			return shifts[0];
		case "POLİKLİNİK 2":
			return shifts[1];
		case "POLİKLİNİK 3":
			return shifts[2];
		case "YENİDOĞAN NÖBET":
			return shifts[3];
		case"İCAP":
			return shifts[4];
		case "KONSÜLTASYON":
			return shifts[5];
		case"SERVİS":
			return shifts[6];
		case "ENDOKRİN İÇİ":
			return shifts[7];
		
		default:
			return null;
		}
	}
	public Doctors getSpecificDoctor(String name) {
		//----------------------------------------------------
		/*Summary:takes string parameter which is the name of the doctor then returns that doctor object.
		 * 		
		 */
		//-------------------------------------------------------------------------
	
		switch(name) {
		case "TANER BARAN":
			return doctors[0];
		case "SERPİL BAŞ":
			return doctors[1];
		case "YUSUF CÜREK":
			return doctors[2];
		case "GAMZE ÇELMELİ":
			return doctors[3];
		case"MÜGE ATAR":
			return doctors[4];
		case "DOĞA TÜRKKAHRAMAN":
			return doctors[5];
		default:
			return null;
		}
	}
	public int getNumOfDoctorsWhoWorkOnPolikinliks() {
		//----------------------------------------------------
		/*Summary:returns the number of total doctors who assigned to pol1 or pol2 or pol3
		 * 		
		 */
		//-------------------------------------------------------------------------	
		
		
		int count = 0 ;
		Doctors[] allDoctors=getDoctors();
		for(Doctors doc : allDoctors) {
			if((adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 1").getShiftId()]
				||adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 2").getShiftId()]
				||adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 3").getShiftId()] )												
					) {
				count++;
			}

		}
		return count;	
	}
	public int getNumOfDoctorsWhoDoesNotWorkOnPolikinliks() {
		//----------------------------------------------------
		/*Summary:returns the number of total doctors who not assigned to pol1 or pol2 or pol3
		 * 		
		 */
		//-------------------------------------------------------------------------		
		
		
		int count = 0 ;
	Doctors[] allDoctors=getDoctors();
	for(Doctors doc : allDoctors) {
		if((!adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 1").getShiftId()]
			&&!adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 2").getShiftId()]
			&&!adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 3").getShiftId()] )												
				) {
			count++;
		}

	}
	return count;
	}

	public Doctors[] getDoctorsWhoDoesWorkAtPolikinliks() {
		//----------------------------------------------------
		/*Summary:returns the array of doctors that  assigned to pol1 or pol2 or pol3
		 * 		
		 */
		//-------------------------------------------------------------------------		
		
		
		
		Doctors[] allDoctors=getDoctors();
		Doctors[] WorkOnPolikinliks = new Doctors[getNumOfDoctorsWhoWorkOnPolikinliks()];
		int i = 0;
		while(i < getNumOfDoctorsWhoWorkOnPolikinliks()) {
		for (Doctors doc : allDoctors) {
			if((adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 1").getShiftId()]
					||adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 2").getShiftId()]
					||adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 3").getShiftId()] )												
						) {
				WorkOnPolikinliks[i]=doc;	
			i++;
			}
		}
	}
	return WorkOnPolikinliks;
	}

	public Doctors[]getDoctorsWhoDoesNotWorkAtPolikinliks(){
		//----------------------------------------------------
		/*Summary:returns the array of doctors that not assigned to pol1 or pol2 or pol3
		 * 		
		 */
		//-------------------------------------------------------------------------		
		
		
		
		Doctors[] allDoctors=getDoctors();
		Doctors[] notWorkOnPolikinliks = new Doctors[getNumOfDoctorsWhoDoesNotWorkOnPolikinliks()];
		int i = 0;
		while(i < getNumOfDoctorsWhoDoesNotWorkOnPolikinliks()) {
		for (Doctors doc : allDoctors) {
			if((!adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 1").getShiftId()]
					&&!adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 2").getShiftId()]
					&&!adjMatrixForConnections[doc.getDoctorId()][getSpecificShift("POLİKLİNİK 3").getShiftId()] )												
						) {
	         notWorkOnPolikinliks[i]=doc;	
			i++;
			}
		}
	}
	return notWorkOnPolikinliks;

	}
	public Doctors[] getTwoDifferentDoctorsNotWorkingAtPolikinliks() {
		//----------------------------------------------------
		/*Summary:returns two different doctors between the doctors that not assigned to pol1 or pol2 or pol3
		 * 		
		 */
		//-------------------------------------------------------------------------			   
		
		
		
	    Doctors[] notWorkOnPolikinliks = getDoctorsWhoDoesNotWorkAtPolikinliks();

	    // Ensure we have at least two doctors who do not work at polikinliks
	    if (notWorkOnPolikinliks.length < 2) {
	        System.out.println("There are not enough doctors who do not work at polikinliks.");
	        return null;
	    }

	    Random random = new Random();
	    Doctors[] selectedDoctors = new Doctors[2];

	    // Select two different doctors randomly
	    int firstIndex = random.nextInt(notWorkOnPolikinliks.length);
	    selectedDoctors[0] = notWorkOnPolikinliks[firstIndex];

	    int secondIndex;
	    do {
	        secondIndex = random.nextInt(notWorkOnPolikinliks.length);
	    } while (secondIndex == firstIndex); // Ensure second doctor is different from the first

	    selectedDoctors[1] = notWorkOnPolikinliks[secondIndex];

	    return selectedDoctors;
	}

	public void assignPolikinliksFromInput(boolean t1,boolean t2,boolean t3,boolean s1,boolean s2 ,boolean s3
	                                      ,boolean y1,boolean y2,boolean y3,boolean g1,boolean g2,boolean g3
	                                      ,boolean m1,boolean m2,boolean m3,boolean d1,boolean d2,boolean d3
			
	//----------------------------------------------------
/*Summary:assigns polikinliks to doctors based on their preferences
 * 		
 */ 
 //-------------------------------------------------------------------------				
		
			
			
			
			
			) {
	  doctors[0].setPolikinlik1Status(t1);
	  doctors[0].setPolikinlik2Status(t2);
	  doctors[0].setPolikinlik3Status(t3);
	
	  doctors[1].setPolikinlik1Status(s1);
	  doctors[1].setPolikinlik2Status(s2);
	  doctors[1].setPolikinlik3Status(s3);
	
	  doctors[2].setPolikinlik1Status(y1);
	  doctors[2].setPolikinlik2Status(y2);
	  doctors[2].setPolikinlik3Status(y3);
	
	  doctors[3].setPolikinlik1Status(g1);
	  doctors[3].setPolikinlik2Status(g2);
	  doctors[3].setPolikinlik3Status(g3);
	
	  doctors[4].setPolikinlik1Status(m1);
	  doctors[4].setPolikinlik2Status(m2);
	  doctors[4].setPolikinlik3Status(m3);
	  
	  
	  doctors[5].setPolikinlik1Status(d1);
	  doctors[5].setPolikinlik2Status(d2);
	  doctors[5].setPolikinlik3Status(d3);
	  
	}
	public int numOfDoctorFromP1() {
//-------------------------------------------
//Summary: Returns the number of doctors that registered to pol 1	
//		
//-------------------------------------------		
		int count = 0;
		Doctors[] all = getDoctors();
	for(Doctors doc:all) {
		if(doc.isPolikinlik1Status()) {
			count++;
		}
	}
	return count;
	}
	public int numOfDoctorFromP2() {
		//-------------------------------------------
		//Summary: Returns the number of doctors that registered to pol 2	
//				
		//-------------------------------------------		
		
		
		int count = 0;
		Doctors[] all = getDoctors();
	for(Doctors doc:all) {
		if(doc.isPolikinlik2Status()) {
			count++;
		}
	}
	return count;
	}
	public int numOfDoctorFromP3() {
		//-------------------------------------------
		//Summary: Returns the number of doctors that registered to pol 3	
//				
		//-------------------------------------------		
		
		
		int count = 0;
		Doctors[] all = getDoctors();
	for(Doctors doc:all) {
		if(doc.isPolikinlik3Status()) {
			count++;
		}
	}
	return count;
	}
	public Doctors[] getPol1Doctors() {
		//-------------------------------------------
		//Summary: Returns the all doctors that registered to pol 1	
//				
		//-------------------------------------------		
		
		
		Doctors[] allDoctors = getDoctors();
	   Doctors[] pol1 = new Doctors[numOfDoctorFromP1()];
	int i = 0;
	while (i<numOfDoctorFromP1())  {
	for (Doctors d : allDoctors) {
		if(d.isPolikinlik1Status()) {
			pol1[i]=d;
	i++;
		}
	}
	}
	return pol1;

	}
	public Doctors[] getPol2Doctors() {
		//-------------------------------------------
		//Summary: Returns the all doctors that registered to pol 2	
//				
		//-------------------------------------------	
		
		
		
		Doctors[] allDoctors = getDoctors();
	   Doctors[] pol2 = new Doctors[numOfDoctorFromP2()];
	int i = 0;
	while (i<numOfDoctorFromP2()) {  
	for (Doctors d : allDoctors) {
		if(d.isPolikinlik2Status()) {
			pol2[i]=d;
	i++;
		}

	}
	}
	return pol2;

	}
	public Doctors[] getPol3Doctors() {
		//-------------------------------------------
		//Summary: Returns the all doctors that registered to pol 3	
//				
		//-------------------------------------------		
		
		
		
		Doctors[] allDoctors = getDoctors();
	   Doctors[] pol3 = new Doctors[numOfDoctorFromP3()];
	int i = 0;
	while (i<numOfDoctorFromP3()) {  
	for (Doctors d : allDoctors) {
		if(d.isPolikinlik3Status()) {
			pol3[i]=d;
	i++;
		}

	}
	}
	return pol3;

	}
	public void assignIcapForOnlyWeekends() {
		//-------------------------------------------
		//Summary: Since only icap can be assigned for weekends,randomly selected doctor is assigned to icap shift for weekends
//				
		//-------------------------------------------	
		
		
		Doctors randomIcapDoctor = getRandomDoctorForIcap();
		addEdge(randomIcapDoctor,shifts[4]);
	}
	
	
	public void assignIcapAfterYenidoganForWeekends(Doctors yenidogan) {
		//-------------------------------------------
		//Summary: If a doctor assigned to yenidogan on previos day,that doctor cannot be selected for icap shift duty 
//				  for this day.
		//-------------------------------------------	
		
		
		
		Doctors randomIcapDoctor;
		    do {
		        randomIcapDoctor = getRandomDoctorForIcap();
		    } while (randomIcapDoctor == yenidogan);
		    addEdge(randomIcapDoctor, shifts[4]);
	}

	public void assignYenidogan(Doctors doctor) {
		//-------------------------------------------
		//Summary: Assigns yenidogan shift to the specific doctor 
//				
		//-------------------------------------------	
		
		
		addEdge(doctor,shifts[3]);
	}
		

	public void AssignShiftsToDoctorsAfterTheDayYenidogan(Doctors yenidogan) {
//--------------------------------------------------
//Summary: Assigns shifts to doctor randomly on weekdays by considering the doctor who assigned to yenidogan on  		
//		  previous day because the doctor who assigned to yenidogan on yesterday cannot work for today
//--------------------------------------------------
		Doctors randomIcapDoctor;
	    do {
	        randomIcapDoctor = getRandomDoctorForIcap();
	    } while (randomIcapDoctor == yenidogan);
	    addEdge(randomIcapDoctor, shifts[4]);

	    Doctors randomPol1Doctor;
	    do {
	        randomPol1Doctor = getRandomDoctorFromPol1();
	    } while (randomPol1Doctor == yenidogan);
	    addEdge(randomPol1Doctor, shifts[0]);

	    Doctors randomPol2Doctor;
	    do {
	        randomPol2Doctor = getRandomDoctorFromPol2();
	    } while (randomPol2Doctor == yenidogan || adjMatrixForConnections[randomPol2Doctor.getDoctorId()][shifts[0].getShiftId()]);
	    addEdge(randomPol2Doctor, shifts[1]);

	    Doctors randomPol3Doctor;
	    do {
	        randomPol3Doctor = getRandomDoctorFromPol3();
	    } while (randomPol3Doctor == yenidogan || adjMatrixForConnections[randomPol3Doctor.getDoctorId()][shifts[0].getShiftId()] || adjMatrixForConnections[randomPol3Doctor.getDoctorId()][shifts[1].getShiftId()]);
	    addEdge(randomPol3Doctor, shifts[2]);

	    Doctors kons;
	    do {
	        kons = getRandomDoctorForKons();
	    } while (kons == yenidogan);
	    addEdge(kons, shifts[5]);

	    Doctors service;
	    do {
	        service = getRandomDoctorForService();
	    } while (service == yenidogan);
	    addEdge(service, shifts[6]);

	    Doctors endokrin;
	    do {
	        endokrin = getRandomDoctorForEndokrin();
	    } while (endokrin == yenidogan);
	    addEdge(endokrin, shifts[7]);
	}

	
	
	public void assignShiftsToDoctors() {
		//--------------------------------------------------
		//Summary: Assigns shifts to doctor randomly on weekdays 
		//--------------------------------------------------						
		Doctors randomIcapDoctor = getRandomDoctorForIcap();
		addEdge(randomIcapDoctor,shifts[4]);
		Doctors randomPol1Doctor=getRandomDoctorFromPol1();
	addEdge(randomPol1Doctor,shifts[0]);
	Doctors randomPol2Doctor = getRandomDoctorFromPol2();
	if(adjMatrixForConnections[randomPol2Doctor.getDoctorId()][shifts[0].getShiftId()]) {
		randomPol2Doctor=getRandomDoctorFromPol2();
	}
	addEdge(randomPol2Doctor,shifts[1]);

	Doctors randomPol3Doctor = getRandomDoctorFromPol3();
	if(adjMatrixForConnections[randomPol3Doctor.getDoctorId()][shifts[0].getShiftId()] || adjMatrixForConnections[randomPol3Doctor.getDoctorId()][shifts[1].getShiftId()]) {
		randomPol3Doctor=getRandomDoctorFromPol3();
	}
	addEdge(randomPol3Doctor,shifts[2]);

	Doctors kons = getRandomDoctorForKons();

	addEdge(kons,shifts[5]);

	Doctors service = getRandomDoctorForService();
	addEdge(service,shifts[6]);

	Doctors endokrin = getRandomDoctorForEndokrin();
	addEdge(endokrin,shifts[7]);
	}
	public void testPolikinlikStatus() {
		
	doctors[0].setPolikinlik1Status(true);
	doctors[0].setPolikinlik2Status(false);
	doctors[0].setPolikinlik3Status(false);


	doctors[1].setPolikinlik1Status(true);
	doctors[1].setPolikinlik2Status(true);
	doctors[1].setPolikinlik3Status(false);


	doctors[2].setPolikinlik1Status(false);
	doctors[2].setPolikinlik2Status(true);
	doctors[2].setPolikinlik3Status(false);

	doctors[3].setPolikinlik1Status(false);
	doctors[3].setPolikinlik2Status(true);
	doctors[3].setPolikinlik3Status(true);

	doctors[4].setPolikinlik1Status(false);
	doctors[4].setPolikinlik2Status(true);
	doctors[4].setPolikinlik3Status(true);


	doctors[5].setPolikinlik1Status(false);
	doctors[5].setPolikinlik2Status(false);
	doctors[5].setPolikinlik3Status(true);	    
	}

	public int findNumOfPolikinliksDoctorHas(Doctors doc) {
		//--------------------------------------------------
		//Summary: returns the total num of total polikinlik parameter doctor has  		
//				  
		//--------------------------------------------------
		
		
		int count = 0;
		if(doc.isPolikinlik1Status()) {
			count++;
		}
		if(doc.isPolikinlik2Status()) {
			count++;
		}
	if(doc.isPolikinlik3Status()) {
		count++;
	}
	return count;
	}
	public void setPolikinlikNumbersForAllDoctors() {
//------------------------------------------------------------------------
//Summary:set the polikinkik numbers based on preferences
//-----------------------------------------------------------------------		
		Doctors[] all =getDoctors();
		for (Doctors doc :all) {
			int polNum=findNumOfPolikinliksDoctorHas(doc);
			doc.setNumOfPolikinlikDoctorHas(polNum);
		}
	}
	public Doctors getRandomDoctorForKons() {
//-----------------------------------------------
//Summary:Selects randomly one doctor between 2 doctors between who not work on polikinliks and returns that randomly 
//selected doctor		
//-----------------------------------------------		
		Doctors[] konsAndServiceDoctors = getTwoDifferentDoctorsNotWorkingAtPolikinliks();
		 Random rand = new Random();
	while(true) {
		int randomIndex = rand.nextInt(konsAndServiceDoctors.length);
	    Doctors randomDoctor = konsAndServiceDoctors[randomIndex];
	    if (isShiftLegalForKonsAndService(randomDoctor)) {
	        return randomDoctor;
	    }
	}
	}
	public Doctors getRandomDoctorForEndokrin() {
		Doctors endokrinDoctor = getRandomDoctorFromPolikinliks();
	if(!isShiftLegalForEndokrin(endokrinDoctor)) {
		endokrinDoctor = getRandomDoctorFromPolikinliks();
	}
	return endokrinDoctor;
	}
	public Doctors getRandomDoctorForService() {
		//-----------------------------------------------
		//Summary:Selects randomly one doctor between 2 doctors between who not work on polikinliks and kons then 
		//returns that randomly selected doctor		
		//-----------------------------------------------	
		
		Doctors[] konsAndServiceDoctors = getTwoDifferentDoctorsNotWorkingAtPolikinliks();
		 Random rand = new Random();
	while(true) {
		int randomIndex = rand.nextInt(konsAndServiceDoctors.length);
	    Doctors randomDoctor = konsAndServiceDoctors[randomIndex];
	    if (isShiftLegalForKonsAndService(randomDoctor) && !adjMatrixForConnections[randomDoctor.getDoctorId()][shifts[5].getShiftId()]) {
	        return randomDoctor;
	    }
	}
	}
	public Doctors getRandomDoctorForIcap() {
//--------------------------------------------------------	  
//Summary:Selects one doctor between all doctors by considering constraints for icap shift
//---------------------------------------------------------		
		
		Doctors[] allDoctors = getDoctors();
	    Random rand = new Random();
	    
	    // Select a random doctor until a valid one for icap is found
	    while (true) {
	        int randomIndex = rand.nextInt(allDoctors.length);
	        Doctors randomDoctor = allDoctors[randomIndex];
	        
	        // Check if the random doctor is legal for icap shift
	        if (isShiftLegalForIcap(randomDoctor)) {
	            return randomDoctor;
	        }
	    }
	}
	public Doctors getRandomDoctorFromPol3() {
		//--------------------------------------------------------	  
		//Summary:Selects one doctor between the doctors registered to pol3  by considering constraints for pol3 shift
		//---------------------------------------------------------		
		
		Doctors[] pol3Doctors = getPol3Doctors();
	    if (pol3Doctors.length == 0) {
	        return null;
	    }

	    // calculate total weight
	    int totalWeight = 0;
	    for (Doctors doctor : pol3Doctors) {
	        totalWeight += (3 - doctor.getNumOfPolikinlikDoctorHas());  
	        //weight is inversely proportinal to the number of outpatient clinics the doctor has
	    }

	    // choice of random number
	    Random rand = new Random();
	    int randomWeight = rand.nextInt(totalWeight);

	    // choice doctor according to the random number
	    int currentWeightSum = 0;
	    for (Doctors doctor : pol3Doctors) {
	        currentWeightSum += (3 - doctor.getNumOfPolikinlikDoctorHas());
	        if (randomWeight < currentWeightSum) {
	            return doctor;
	        }
	    }

	    // for security,
	    return pol3Doctors[0];
	}

	public Doctors getRandomDoctorFromPol2() {
		//--------------------------------------------------------	  
		//Summary:Selects one doctor between the doctors registered to pol2  by considering constraints for pol3 shift
		//---------------------------------------------------------		  
		
		
		Doctors[] pol2Doctors = getPol2Doctors();
	    if (pol2Doctors.length == 0) {
	        return null;
	    }

	    // Calculate total weight
	    int totalWeight = 0;
	    for (Doctors doctor : pol2Doctors) {
	        totalWeight += (3 - doctor.getNumOfPolikinlikDoctorHas()); 
	      //weight is inversely proportinal to the number of outpatient clinics the doctor has
	    }

	    // choice of random num
	    Random rand = new Random();
	    int randomWeight = rand.nextInt(totalWeight);

	    
	    int currentWeightSum = 0;
	    for (Doctors doctor : pol2Doctors) {
	        currentWeightSum += (3 - doctor.getNumOfPolikinlikDoctorHas());
	        if (randomWeight < currentWeightSum) {
	            return doctor;
	        }
	    }

	    
	    return pol2Doctors[0];
	}
	public Doctors getRandomDoctorFromPol1() {
		//--------------------------------------------------------	  
		//Summary:Selects one doctor between the doctors registered to pol1  by considering constraints for pol3 shift
		//---------------------------------------------------------	    
		
		
		Doctors[] pol1Doctors = getPol1Doctors();
	    if (pol1Doctors.length == 0) {
	        return null;
	    }

	   
	    int totalWeight = 0;
	    for (Doctors doctor : pol1Doctors) {
	        totalWeight += (3 - doctor.getNumOfPolikinlikDoctorHas()); 
	      //weight is inversely proportinal to the number of outpatient clinics the doctor has
	    }


	    Random rand = new Random();
	    int randomWeight = rand.nextInt(totalWeight);

	    
	    int currentWeightSum = 0;
	    for (Doctors doctor : pol1Doctors) {
	        currentWeightSum += (3 - doctor.getNumOfPolikinlikDoctorHas());
	        if (randomWeight < currentWeightSum) {
	            return doctor;
	        }
	    }

	    
	    return pol1Doctors[0];
	}
	public Doctors getRandomDoctorFromPolikinliks() {
		//--------------------------------------------------------	  
		//Summary:Selects one doctor between the doctors who assigned to pol1 or pol2 or pol3 and returns that selected doctor
		//---------------------------------------------------------	
		
		
		
		Doctors[] polikinlikDoctors = getDoctorsWhoDoesWorkAtPolikinliks();
	    if (polikinlikDoctors.length == 0) {
	        return null; // No doctors working in polikinliks
	    }
	    Random rand = new Random();
	    int randomIndex = rand.nextInt(polikinlikDoctors.length);
	    return polikinlikDoctors[randomIndex];
	}



	public boolean isShiftLegalForIcap(Doctors doctor) {
		//----------------------------------------------------------------------------------------------------------------	
		//Parameter doctor can be assigned to Icap shift if the parameter doctor not assigned to the yenidogan on the same day
		//So it is legal to assign the parameter doctor to icap if the doctor not assigned to yenidogan		
		//----------------------------------------------------------------------------------------------------------------				
		
		
		
		if(adjMatrixForConnections[doctor.getDoctorId()][getSpecificShift("YENİDOĞAN NÖBET").getShiftId()]) {
			return false;

		}
	return true;
	}

	public boolean isShiftLegalForEndokrin(Doctors doctor) {
		//----------------------------------------------------------------------------------------------------------------	
		//Parameter doctor can be assigned to Endokrin içi shift if the doctor is  assigned to pol 1 or pol2 or pol 3 so it is
		//legal to assign the parameter doctor to endokrin içi		
		//----------------------------------------------------------------------------------------------------------------					
		if(!isShiftLegalForKonsAndService(doctor)) {
			
			return true;
		}
	return false;
	}
	public String getPolikinlik1DoctorNameAfterAssignShifts() {
		Doctors p1 =getPolikinlik1DoctorAfterAssignShifts();
		if(p1==null) {
			return "";
		}
		return p1.getNameSurname();
	}
	public String getPolikinlik2DoctorNameAfterAssignShifts() {
		Doctors p2 =getPolikinlik2DoctorAfterAssignShifts();
		if(p2==null) {
			return "";
		}
		return p2.getNameSurname();
	}
	public String getPolikinlik3DoctorNameAfterAssignShifts() {
		Doctors p3 =getPolikinlik3DoctorAfterAssignShifts();
		if(p3==null) {
			return "";
		}
		return p3.getNameSurname();
	}
	public String getYenidoganDoctorNameAfterAssignShifts() {
		Doctors y =getYenidoganDoctorAfterAssignShifts();
		if(y==null) {
			return "";
		}
		return y.getNameSurname();
	
	}
public String getIcapDoctorNameAfterAssignShifts() {
	Doctors i =getIcapDoctorAfterAssignShifts();
	if(i==null) {
		return "";
	}
	return i.getNameSurname();

	}
public String getKonsDoctorNameAfterAssignShifts() {
	Doctors k =getKonsDoctorAfterAssignShifts();
	if(k==null) {
		return "";
	}
	return k.getNameSurname();

}
public String getServiceDoctorNameAfterAssignShifts() {
	Doctors s =getServiceDoctorAfterAssignShifts();
	if(s==null) {
		return "";
	}
	return s.getNameSurname();

}
public String getEndokrinDoctorNameAfterAssignShifts() {
	Doctors e =getEndokrinDoctorAfterAssignShifts();
	if(e==null) {
		return "";
	}
	return e.getNameSurname();
}

	public Doctors getPolikinlik1DoctorAfterAssignShifts() {

		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Pol3. Used after assigning shifts.	
		//----------------------------------------------------------			
					
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[0].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getPolikinlik2DoctorAfterAssignShifts() {
		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Pol2. Used after assigning shifts.	
		//----------------------------------------------------------	
		
		
		
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[1].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getPolikinlik3DoctorAfterAssignShifts() {
	//----------------------------------------------------------
	//Summary: Returns the doctor who assigned to Pol3. Used after assigning shifts.	
	//----------------------------------------------------------	
	Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[2].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getYenidoganDoctorAfterAssignShifts() {
		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Yenidogan. Used after assigning shifts.	
		//----------------------------------------------------------	
		
		
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[3].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getIcapDoctorAfterAssignShifts() {
		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Icap. Used after assigning shifts.	
		//----------------------------------------------------------	
		
		
		
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[4].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getKonsDoctorAfterAssignShifts() {
		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Konsültasyon. Used after assigning shifts.	
		//----------------------------------------------------------	
		
		
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[5].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getServiceDoctorAfterAssignShifts() {
		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Service. Used after assigning shifts.	
		//----------------------------------------------------------		
		
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[6].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public Doctors getEndokrinDoctorAfterAssignShifts() {
		//----------------------------------------------------------
		//Summary: Returns the doctor who assigned to Endokrin. Used after assigning shifts.	
		//----------------------------------------------------------	
		
		
		
		Doctors[] dr = getDoctors();
	for(Doctors d : dr) {

	if(adjMatrixForConnections[d.getDoctorId()][shifts[7].getShiftId()]) {
		return d;
	}
	}
	return null;
	}
	public boolean isShiftLegalForKonsAndService(Doctors doctor) {
//----------------------------------------------------------------------------------------------------------------	
//Parameter doctor can be assigned to Kons or Service shifts if the doctor is not assigned to pol 1 or pol2 or pol 3 so it is
//legal to assign the parameter doctor to kons or service		
//----------------------------------------------------------------------------------------------------------------		
			if(adjMatrixForConnections[doctor.getDoctorId()][shifts[0].getShiftId()] || 
					adjMatrixForConnections[doctor.getDoctorId()][shifts[1].getShiftId()] || adjMatrixForConnections[doctor.getDoctorId()][shifts[2].getShiftId()] ) {
				return false;
			}
		return true;
	}
	
// Getter and Setters	
	
	public Doctors[] getDoctors() {
		return doctors;
	}
	public void setDoctors(Doctors[] doctors) {
		this.doctors = doctors;
	}
	public Shift[] getShifts() {
		return shifts;
	}
	public void setShifts(Shift[] shifts) {
		this.shifts = shifts;
	}

	public boolean[][] getAdjMatrixForConnections() {
		return adjMatrixForConnections;
	}
	public void setAdjMatrixForConnections(boolean[][] adjMatrixForConnections) {
		this.adjMatrixForConnections = adjMatrixForConnections;
	}
@Override
public String toString() {
    return String.format("%-20s %-20s %-20s %-20s %-20s %-20s %-20s %-20s",
            getPolikinlik1DoctorNameAfterAssignShifts(),
            getPolikinlik2DoctorNameAfterAssignShifts(),
            getPolikinlik3DoctorNameAfterAssignShifts(),
            getYenidoganDoctorNameAfterAssignShifts(),
            getIcapDoctorNameAfterAssignShifts(),
            getKonsDoctorNameAfterAssignShifts(),
            getServiceDoctorNameAfterAssignShifts(),
            getEndokrinDoctorNameAfterAssignShifts());
}

	}


