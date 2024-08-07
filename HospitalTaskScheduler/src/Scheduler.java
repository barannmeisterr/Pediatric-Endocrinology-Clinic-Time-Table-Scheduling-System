import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
/*Title: Scheduler class
 * Author : Arda Baran
 * Description : This class represent time table of a month. Uses java time library to detect how many days in the selected month by
 * user of selected year by user , which days are weekdays which days are weekends. Takes the yenidogan days and doctors from
 * user and assign the doctor to yenidogan shift for yenidogan days.takes input from user to set doctor poliklinik preferences.
 * creates monthly doctor-shift-timetable-scheduler.Then extracts this created timetable schedule as excel file.
 *
 *
 */

public class Scheduler {
    int numOfDays;
    Scanner sc ;
    
  public Scheduler() {
	
  sc=new Scanner(System.in,"UTF-8");
  }
  
  public int getNumOfDays() {
	return numOfDays;
}

public void setNumOfDays(int numOfDays) {
	this.numOfDays = numOfDays;
}
public List<DirectedGraphRepresentationRelationsAndConstraints> TimeTableScheduler(boolean t1, boolean t2, boolean t3, boolean s1, boolean s2, boolean s3,
        boolean y1, boolean y2, boolean y3, boolean g1, boolean g2, boolean g3,
        boolean m1, boolean m2, boolean m3, boolean d1, boolean d2, boolean d3,Map<Integer,String> yenidoganSchedule ,int year, Month month) 

{
   
	
	DirectedGraphRepresentationRelationsAndConstraints schedule;
    List<DirectedGraphRepresentationRelationsAndConstraints> scheduleList = new ArrayList<>();
   
    
   
    YearMonth yearMonth = YearMonth.of(year, month);
    int numOfDays = yearMonth.lengthOfMonth();
  
    List<Integer> weekends = new ArrayList<>();
    List<Integer> weekdays = new ArrayList<>();
    // Determine the weekends
    for (int day = 1; day <= numOfDays; day++) {
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            weekends.add(day);
        }
    }
    // determine the weekdays
    for (int day = 1; day <= numOfDays; day++) {
        LocalDate date = LocalDate.of(year, month, day);
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.THURSDAY|| dayOfWeek == DayOfWeek.FRIDAY) {
        	weekdays.add(day);
        }
    }
   
    // assigns daily shifts
    for (int day = 1; day <= numOfDays; day++) {
        schedule = new DirectedGraphRepresentationRelationsAndConstraints();
       
 schedule.assignPolikinliksFromInput(t1,t2,t3,s1,s2,s3,y1,y2,y3,g1,g2,g3,m1,m2,m3,d1,d2,d3);//doctor pol preferences               
schedule.setPolikinlikNumbersForAllDoctors();//set the number of polikinliks for each doctor
        
        boolean isWeekend = weekends.contains(day);//is today weekend
        boolean hasYenidogan = yenidoganSchedule.containsKey(day);//is today a doctor assigned to yenidogan 
        boolean hadYenidoganYesterday = yenidoganSchedule.containsKey(day - 1);//is yesterday a doctor assigned to yenidogan
        boolean isWeekday = weekdays.contains(day);//is today  weekday
        if(isWeekday && !isWeekend && !hasYenidogan && !hadYenidoganYesterday) {
  /*----------------------------------------------------------------------------
   *If the day is weekday and there is no yenidogan shift on today and yesterday, assign doctors to shifts pol1 ,pol2 , pol3
   * icap,kons,service and endokrin for weekdays
   *///---------------------------------------------------------------------------
        	
        	
        	schedule.assignShiftsToDoctors();
        }
        if(!isWeekday && isWeekend && !hasYenidogan && !hadYenidoganYesterday) {
        	  /*----------------------------------------------------------------------------
        	   *If the day is weekday and there is no yenidogan shift on today and yesterday, assign a doctor to icap shift
        	   * for weekends      
        	   *///---------------------------------------------------------------------------      	
        	
        	
        	
        	schedule.assignIcapForOnlyWeekends();
        }
        if(isWeekday && !isWeekend && !hasYenidogan && hadYenidoganYesterday) {
        	  /*----------------------------------------------------------------------------
        	   *If the day is weekday and there is no yenidogan shift on today and there is yenidogan shift on yesterday, assign shifts to doctors for
        	   * weekdays by using AssignShiftsToDoctorsAfterTheDayYenidogan(prevDoctor)      
        	   *///--------------------------------------------------------------------------- 
        	
        	
        	
        	String prevDoctorName = yenidoganSchedule.get(day - 1);
            Doctors prevDoctor = schedule.getSpecificDoctor(prevDoctorName);
            schedule.AssignShiftsToDoctorsAfterTheDayYenidogan(prevDoctor);
                    
        }
        if(isWeekday && !isWeekend && hasYenidogan && !hadYenidoganYesterday) {
       
     /*If the day is weekday and there is yenidogan for today and there is no yenidogan on yesterday
      * , firstly assign yenidogan then assign doctors to all shifts
      *      	
      */
        	
        	
        	String curr = yenidoganSchedule.get(day);
            Doctors currYenidogan = schedule.getSpecificDoctor(curr);
            schedule.assignYenidogan(currYenidogan);
        schedule.assignShiftsToDoctors();
        }
        
        if(isWeekday && !isWeekend && hasYenidogan && hadYenidoganYesterday) {
        	
        	/*If the day is weekday and there is yenidogan for today and there is  yenidogan on yesterday
             * , firstly assign yenidogan for that day ,secondly determine the doctor who assigned to yenidogan yesterday 
             * then assign doctors to all shifts without the doctor who assigned to yenidogan yesterday
             *      	
             */      	
        	
        	
        	String currYenidogan = yenidoganSchedule.get(day);
        Doctors currYeniDoc = schedule.getSpecificDoctor(currYenidogan);
        schedule.assignYenidogan(currYeniDoc);
        String prevY = yenidoganSchedule.get(day - 1);
        Doctors yesterdayYeniDoc = schedule.getSpecificDoctor(prevY);
        schedule.AssignShiftsToDoctorsAfterTheDayYenidogan(yesterdayYeniDoc);
        }
        
        
 //Same operations for weekend days . only icap shift can be assigned on weekends       
        if(!isWeekday && isWeekend && hasYenidogan && !hadYenidoganYesterday) {
       
        	
        	String currYenidoganN = yenidoganSchedule.get(day);
        Doctors currYeniDocc = schedule.getSpecificDoctor(currYenidoganN);
        schedule.assignYenidogan(currYeniDocc);
      
        schedule.assignIcapForOnlyWeekends();
        } 
        if(!isWeekday && isWeekend && !hasYenidogan && hadYenidoganYesterday) {
        	String prevDoctorNamee = yenidoganSchedule.get(day - 1);
            Doctors prevDoctorr = schedule.getSpecificDoctor(prevDoctorNamee);
            schedule.assignIcapAfterYenidoganForWeekends(prevDoctorr);
        } 
        if(!isWeekday && isWeekend && hasYenidogan && hadYenidoganYesterday) {
        	String currWeekendYeniDogan = yenidoganSchedule.get(day);
        	Doctors currWeekendYeniDoganDoc = schedule.getSpecificDoctor(currWeekendYeniDogan);
        	schedule.assignYenidogan(currWeekendYeniDoganDoc);
        	String prevDoctorNameee = yenidoganSchedule.get(day - 1);
            Doctors prevDoctorrr = schedule.getSpecificDoctor(prevDoctorNameee);
            schedule.assignIcapAfterYenidoganForWeekends(prevDoctorrr);
        } 
        
        
        //adds all randomly selected doctors to the timetable day by day.
        scheduleList.add(schedule);
    }

return scheduleList;


}

public void writeToExcel(JFrame frame,List<DirectedGraphRepresentationRelationsAndConstraints> scheduleList,Month month) {
/*
 * Summary: extract the generated timetable schedule as excel file with correct format.
 *    
 */
	
	String turkishMonthName = convertMonthNameToTurkish(month);
	Workbook workbook = new XSSFWorkbook();
    Sheet sheet = workbook.createSheet("Aylık Nöbet Çizelgesi");

    // create header rows
    Row headerRow = sheet.createRow(0);
    String[] headers = {"Gün", "POLİKLİNİK 1", "POLİKLİNİK 2", "POLİKLİNİK 3", "YENİDOĞAN NÖBETİ", "İCAP", "KONSÜLTASYON", "SERVİS", "ENDOKRİN İÇİ"};

    // add header columns
    for (int i = 0; i < headers.length; i++) {
        Cell cell = headerRow.createCell(i);
        cell.setCellValue(headers[i]);
        sheet.setColumnWidth(i, 20 * 256);// Set column widths to 20 units
    }

    // adds datas for each day
    for (int day = 1; day <= scheduleList.size(); day++) {
        DirectedGraphRepresentationRelationsAndConstraints schedule = scheduleList.get(day - 1);
        Row row = sheet.createRow(day);
       
        // writes the day
        Cell dayCell = row.createCell(0);
        dayCell.setCellValue(day);

        // Adds Poliklinik 1, 2, 3, Yenidoğan, İcap, Konsültasyon, Service, Endokrin İçi doctors in sequence
        Cell pol1Cell = row.createCell(1);
        pol1Cell.setCellValue(schedule.getPolikinlik1DoctorNameAfterAssignShifts());

        Cell pol2Cell = row.createCell(2);
        pol2Cell.setCellValue(schedule.getPolikinlik2DoctorNameAfterAssignShifts());

        Cell pol3Cell = row.createCell(3);
        pol3Cell.setCellValue(schedule.getPolikinlik3DoctorNameAfterAssignShifts());

        Cell yenidoganCell = row.createCell(4);
        yenidoganCell.setCellValue(schedule.getYenidoganDoctorNameAfterAssignShifts());

        Cell icapCell = row.createCell(5);
        icapCell.setCellValue(schedule.getIcapDoctorNameAfterAssignShifts());

        Cell konsultasyonCell = row.createCell(6);
        konsultasyonCell.setCellValue(schedule.getKonsDoctorNameAfterAssignShifts());

        Cell servisCell = row.createCell(7);
        servisCell.setCellValue(schedule.getServiceDoctorNameAfterAssignShifts());

        Cell endokrinCell = row.createCell(8);
        endokrinCell.setCellValue(schedule.getEndokrinDoctorNameAfterAssignShifts());
    }
String fileName= "ÇocukEndikronoloji"+turkishMonthName+"AyıNöbetÇizelgesi.xlsx";
    // save the generated excel file
    try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
        workbook.write(outputStream);
        JOptionPane.showMessageDialog(frame, "Excel dosyası başarıyla oluşturuldu: " + fileName);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
public String convertMonthNameToTurkish(Month month) {
switch(month.name()) {
case"JANUARY":
	return "Ocak";
case"FEBRUARY":
return "Şubat";
case"MARCH":
	return "Mart";
case"APRIL":
	return"Nisan";
case"MAY":
	return "Mayıs";
case "JUNE":
	return "Haziran";

case"JULY":
	return "Temmuz";
case"AUGUST":
return "Ağustos";
case"SEPTEMBER":
	return "Eylül";
case"OCTOBER":
	return"Ekim";
case"NOVEMBER":
	return "Kasım";
case "DECEMBER":
	return "Aralık";	
default:
	return "Aylık";
}
	
	
}
public String getDoctorName(String firstLetterOfName) {
switch(firstLetterOfName) {
case "t":
	return "TANER BARAN";
case"s":
	return "SERPİL BAŞ";
case "y":
	return "YUSUF CÜREK";

case "g":
return "GAMZE ÇELMELİ";
case "m":
	return "MÜGE ATAR";
case "d":
	return"DOĞA TÜRKKAHRAMAN";
	default:
		return "";
}	
}
}