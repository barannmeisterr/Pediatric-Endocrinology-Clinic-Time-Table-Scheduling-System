import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Scanner;
import java.awt.BorderLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.time.Month;
import java.util.Scanner;
import java.util.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
/**
 *Title: Main class
 * Author : Arda Baran
 * Description : This class provides advanced graphical user interface to user.Java Swing is used.
 * 
 * 
 * 
 *
 *
 */


public class Main {
	List<DirectedGraphRepresentationRelationsAndConstraints> timetable;
    private JFrame frame;
    private JPanel mainPanel;
    private JTextField shiftCountField;
    private JButton submitShiftCountButton;
    private JPanel shiftsPanel;
    private JButton submitAllButton;
    private Map<Integer, JTextField> dayFields;
    private Map<Integer, JTextField> doctorFields;
    private Map<Integer, String> yenidoganSchedule;
    private JSpinner yearSpinner;
    private JSpinner monthSpinner;
    private int selectedYear, selectedMonthIndex;
    private Month selectedMonth;
    private JCheckBox tp1, tp2, tp3, sp1, sp2, sp3, yp1, yp2, yp3, gp1, gp2, gp3, mp1, mp2, mp3, dp1, dp2, dp3;
    private JButton saveButton;
    private boolean t1, t2, t3, s1, s2, s3, y1, y2, y3, g1, g2, g3, m1, m2, m3, d1, d2, d3;
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JButton exportButton;
    
    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Çocuk Endokrinoloji Nöbet Çizelgesi");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(500); 

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        yearSpinner = new JSpinner();
        SpinnerModel yearModel = new SpinnerNumberModel(currentYear, 2024, 2050, 1);
        yearSpinner.setModel(yearModel);
        JSpinner.NumberEditor yearEditor = new JSpinner.NumberEditor(yearSpinner, "####");
        yearSpinner.setEditor(yearEditor);

        JLabel yearLabel = new JLabel("Yıl:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        containerPanel.add(yearLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        containerPanel.add(yearSpinner, gbc);

        monthSpinner = new JSpinner();
        SpinnerModel monthModel = new SpinnerNumberModel(currentMonth, 1, 12, 1);
        monthSpinner.setModel(monthModel);

        JLabel monthLabel = new JLabel("Ay:");
        gbc.gridx = 2;
        gbc.gridy = 0;
        containerPanel.add(monthLabel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        containerPanel.add(monthSpinner, gbc);

        JLabel shiftCountLabel = new JLabel("1 ayda kaç tane yenidoğan nöbeti var?");
        shiftCountField = new JTextField(5);
        submitShiftCountButton = new JButton("Tamam");

        gbc.gridx = 0;
        gbc.gridy = 1;
        containerPanel.add(shiftCountLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        containerPanel.add(shiftCountField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        containerPanel.add(submitShiftCountButton, gbc);

        leftPanel.add(containerPanel, BorderLayout.NORTH);

        shiftsPanel = new JPanel();
        shiftsPanel.setLayout(new GridBagLayout());

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        containerPanel.add(shiftsPanel, gbc);

        dayFields = new HashMap<>();
        doctorFields = new HashMap<>();
        yenidoganSchedule = new HashMap<>();

       
        JPanel clinicPanel = new JPanel();
        clinicPanel.setLayout(new GridLayout(6, 3));

        clinicPanel.add(new JLabel("Taner Baran"));
        tp1 = new JCheckBox("Poliklinik 1");
        clinicPanel.add(tp1);
        tp2 = new JCheckBox("Poliklinik 2");
        clinicPanel.add(tp2);
        tp3 = new JCheckBox("Poliklinik 3");
        clinicPanel.add(tp3);

        clinicPanel.add(new JLabel("Serpil Baş"));
        sp1 = new JCheckBox("Poliklinik 1");
        clinicPanel.add(sp1);
        sp2 = new JCheckBox("Poliklinik 2");
        clinicPanel.add(sp2);
        sp3 = new JCheckBox("Poliklinik 3");
        clinicPanel.add(sp3);

        clinicPanel.add(new JLabel("Yusuf Cürek"));
        yp1 = new JCheckBox("Poliklinik 1");
        clinicPanel.add(yp1);
        yp2 = new JCheckBox("Poliklinik 2");
        clinicPanel.add(yp2);
        yp3 = new JCheckBox("Poliklinik 3");
        clinicPanel.add(yp3);

        clinicPanel.add(new JLabel("Gamze Çelmeli"));
        gp1 = new JCheckBox("Poliklinik 1");
        clinicPanel.add(gp1);
        gp2 = new JCheckBox("Poliklinik 2");
        clinicPanel.add(gp2);
        gp3 = new JCheckBox("Poliklinik 3");
        clinicPanel.add(gp3);

        clinicPanel.add(new JLabel("Müge Atar"));
        mp1 = new JCheckBox("Poliklinik 1");
        clinicPanel.add(mp1);
        mp2 = new JCheckBox("Poliklinik 2");
        clinicPanel.add(mp2);
        mp3 = new JCheckBox("Poliklinik 3");
        clinicPanel.add(mp3);

        clinicPanel.add(new JLabel("Doğa Türkkahraman"));
        dp1 = new JCheckBox("Poliklinik 1");
        clinicPanel.add(dp1);
        dp2 = new JCheckBox("Poliklinik 2");
        clinicPanel.add(dp2);
        dp3 = new JCheckBox("Poliklinik 3");
        clinicPanel.add(dp3);

        leftPanel.add(clinicPanel, BorderLayout.CENTER);

        submitShiftCountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int shiftCount = Integer.parseInt(shiftCountField.getText());
                renderShiftInputs(shiftCount);
            }
        });

        submitAllButton = new JButton("Nöbet Çizelgesi Oluştur");
        submitAllButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateYearAndMonth();
                saveShifts();
                updateClinicPreferences();
             Scheduler scheduler=new Scheduler();
             timetable=scheduler.TimeTableScheduler(t1,t2,t3,s1,s2,s3,y1,y2,y3,g1,g2,g3,m1,m2,m3,d1,d2,d3, yenidoganSchedule, selectedYear, selectedMonth);
          
             updateTable();
            }
        });
        leftPanel.add(submitAllButton, BorderLayout.SOUTH);

        
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());

      
        tableModel = new DefaultTableModel();
        scheduleTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        tableModel.addColumn("Gün");
        tableModel.addColumn("Poliklinik 1");
        tableModel.addColumn("Poliklinik 2");
        tableModel.addColumn("Poliklinik 3");
        tableModel.addColumn("Yenidoğan Nöbeti");
        tableModel.addColumn("İcap");
        tableModel.addColumn("KONSÜLTASYON");
        tableModel.addColumn("Servis");
        tableModel.addColumn("ENDOKRİN İÇİ");
        scheduleTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting())
         { 
                    int selectedRow = scheduleTable.getSelectedRow();
                    if (selectedRow != -1) {
                        DirectedGraphRepresentationRelationsAndConstraints selectedData = timetable.get(selectedRow);
                      
                    }
                }
            }
        });
        scheduleTable.getModel().addTableModelListener((TableModelListener) new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.UPDATE)
         { 
                    int row = e.getFirstRow();
                    int column = e.getColumn();
                   
                    String newCellValue = (String) scheduleTable.getValueAt(row, column);
              
               if(isNameCorrect(newCellValue)) {
            	   switch(column) {
            	   case 1:
            		  if(!isYesterdayYenidoganDay(row)) {
            		   
            		   timetable.get(row).changePol1(frame,newCellValue);
                       
                       updateTable();
            		  }else {
            			  timetable.get(row).changePol1(frame,newCellValue);
                          timetable.get(row).changeMethodHelper(getYesterdayYenidoganDoctorName(row));
                          updateTable();
            			  
            		  }
                       
                       break;
            	   case 2:
            		 
            		   
            		   
            		   if(!isYesterdayYenidoganDay(row)) {
                		   
                		   timetable.get(row).changePol2(frame,newCellValue);
                           
                           updateTable();
                		  }else {
                			  timetable.get(row).changePol2(frame,newCellValue);
                              timetable.get(row).changeMethodHelper(getYesterdayYenidoganDoctorName(row));
                              updateTable();
                			  
                		  }
                       break;
            	   case 3:
            		   if(!isYesterdayYenidoganDay(row)) {
                		   
                		   timetable.get(row).changePol3(frame,newCellValue);
                           
                           updateTable();
                		  }else {
                			  timetable.get(row).changePol3(frame,newCellValue);
                              timetable.get(row).changeMethodHelper(getYesterdayYenidoganDoctorName(row));
                              updateTable();
                			  
                		  }
            		   break;
            	   default:
            		   JOptionPane.showMessageDialog(frame, "Bu Alan Düzenlenemez! " );
            		   updateTable();
            		   return;
            	   }
               }else {
            	   JOptionPane.showMessageDialog(frame, "Lütfen Doktorun İsmini Doğru Giriniz!");
            	   updateTable();
            	   return;
               }
                }
            }
        });
        rightPanel.add(scrollPane, BorderLayout.CENTER);
        exportButton = new JButton("Excel'e Kaydet");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Scheduler exportToExcel=new Scheduler();
                exportToExcel.writeToExcel(frame, timetable, selectedMonth);
            }
        });  
        rightPanel.add(exportButton, BorderLayout.SOUTH);
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);

        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }
private boolean isYesterdayYenidoganDay(int currentIndex) {
	if(currentIndex==0 || !timetable.get(currentIndex - 1).isYenidoganDay()) {
		return false;
	}
return true;
}
private String getYesterdayYenidoganDoctorName(int currentIndex) {
	if(isYesterdayYenidoganDay(currentIndex) && currentIndex!=0) {
		return timetable.get(currentIndex - 1).getYenidoganDoctorAfterAssignShifts().getNameSurname();
	}
return "";
}
    private void renderShiftInputs(int shiftCount) {
        shiftsPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        for (int i = 1; i <= shiftCount; i++) {
            JLabel dayLabel = new JLabel(i + ". nöbet gün numarası:");
            JTextField dayField = new JTextField(5);
            JLabel doctorLabel = new JLabel(i + ". nöbetçi doktor:");
            JTextField doctorField = new JTextField(10);

            gbc.gridx = 0;
            gbc.gridy = i - 1;
            shiftsPanel.add(dayLabel, gbc);

            gbc.gridx = 1;
            gbc.gridy = i - 1;
            shiftsPanel.add(dayField, gbc);

            gbc.gridx = 2;
            gbc.gridy = i - 1;
            shiftsPanel.add(doctorLabel, gbc);

            gbc.gridx = 3;
            gbc.gridy = i - 1;
            shiftsPanel.add(doctorField, gbc);

            dayFields.put(i, dayField);
            doctorFields.put(i, doctorField);
        }

        shiftsPanel.revalidate();
        shiftsPanel.repaint();
    }

    private void saveShifts() {
        yenidoganSchedule.clear();
        for (Map.Entry<Integer, JTextField> entry : dayFields.entrySet()) {
            int index = entry.getKey();
            String day = entry.getValue().getText();
            String doctor = doctorFields.get(index).getText();
            yenidoganSchedule.put(Integer.parseInt(day), doctor);
        }
    }

    private void updateYearAndMonth() {
        selectedYear = (Integer) yearSpinner.getValue();
        selectedMonthIndex = (Integer) monthSpinner.getValue();
        selectedMonth = Month.of(selectedMonthIndex);
    }

    private void updateClinicPreferences() {
        t1 = tp1.isSelected();
        t2 = tp2.isSelected();
        t3 = tp3.isSelected();
        s1 = sp1.isSelected();
        s2 = sp2.isSelected();
        s3 = sp3.isSelected();
        y1 = yp1.isSelected();
        y2 = yp2.isSelected();
        y3 = yp3.isSelected();
        g1 = gp1.isSelected();
        g2 = gp2.isSelected();
        g3 = gp3.isSelected();
        m1 = mp1.isSelected();
        m2 = mp2.isSelected();
        m3 = mp3.isSelected();
        d1 = dp1.isSelected();
        d2 = dp2.isSelected();
        d3 = dp3.isSelected();
    }
private boolean isNameCorrect(String name) {
	if(name.equals("TANER BARAN") || name.equals("SERPİL BAŞ") || name.equals("YUSUF CÜREK") || name.equals("GAMZE ÇELMELİ") || name.equals("MÜGE ATAR") || name.equals("DOĞA TÜRKKAHRAMAN")) {
		return true;
	}
return false;
}
    private void updateTable() {
        tableModel.setRowCount(0); 

        for (int i = 1; i <= timetable.size(); i++) {
            int day = i;
            String pol1=timetable.get(i-1).getPolikinlik1DoctorNameAfterAssignShifts();
            String pol2=timetable.get(i-1).getPolikinlik2DoctorNameAfterAssignShifts();
            String pol3=timetable.get(i-1).getPolikinlik3DoctorNameAfterAssignShifts();
            String yenidogan=timetable.get(i-1).getYenidoganDoctorNameAfterAssignShifts();
            String icap=timetable.get(i-1).getIcapDoctorNameAfterAssignShifts();
            String kons=timetable.get(i-1).getKonsDoctorNameAfterAssignShifts();
            String service=timetable.get(i-1).getServiceDoctorNameAfterAssignShifts();
            String endokrin=timetable.get(i-1).getEndokrinDoctorNameAfterAssignShifts();
            
            
            Object[] rowData = {day,pol1, pol2, pol3, yenidogan,icap,kons,service,endokrin};
            tableModel.addRow(rowData);
        }    
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
