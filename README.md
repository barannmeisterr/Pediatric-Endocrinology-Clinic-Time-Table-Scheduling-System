# Pediatric-Endocrinology-Clinic-Time-Table-Scheduling-System
 This project is a friendly graphical user interface comprehensive scheduling system for a Pediatric Endocrinology Clinic. It leverages a Directed Graph data structure to efficiently model the complex relationships and constraints between doctors and their shifts. The system ensures that all constraints related to doctor-shift assignments are respected, providing an optimal and feasible schedule.Also generated optimal time table schedule extracted as excel xlsx file.
## Author

- Arda Baran
## Features

- **Shift Assignments:** Ensures each doctor is assigned to appropriate shifts while avoiding conflicts.
- **Constraints Handling:** Incorporates constraints such as non-overlapping shifts, rest periods, and specific shift preferences.
- **Optimal Scheduling:** Provides an optimal schedule considering all the constraints and preferences.
- **Excel Export:** Extracts the created timetable schedule as an Excel file.
- **User-Friendly GUI Support:** Features a clean and intuitive graphical user interface built with Java Swing for easy interaction.
- **Dynamic JTable:** Allows editing only in specific columns (clinic preferences), ensuring data integrity while providing flexibility.
- **Doctor Shift Scheduling:** Users can assign shifts to doctors with the option to select year,month, and specific days for yenidogan shift.
- **Automated Updates:** Easily exports the generated schedule to an Excel file for further use or printing.
  
## Constant Doctors

- TANER BARAN --- Doctor ID: 0
- SERPİL BAŞ  --- Doctor ID: 1
- YUSUF CÜREK --- Doctor ID: 2
- GAMZE ÇELMELİ --- Doctor ID: 3
- MÜGE ATAR    --- Doctor ID: 4
- DOĞA TÜRKKAHRAMAN --- Doctor ID: 5

## Constant Shifts

- POLİKLİNİK 1 --- Shift ID: 0
- POLİKLİNİK 2 --- Shift ID: 1 
- POLİKLİNİK 3 --- Shift ID: 2
- YENİDOĞAN NÖBETİ (Neonatal Duty) --- Shift ID: 3
- İCAP (On-Call) --- Shift ID: 4
- KONSÜLTASYON (Consultation) --- Shift ID: 5
- SERVİS (Service) --- Shift ID: 6
- ENDOKRİN (Endocrine) --- Shift ID: 7

## Scheduling Rules

- **Weekdays:** Poliklinik 1, Poliklinik 2, Poliklinik 3, Kons, Service, and Endokrin shifts are only assigned on weekdays.
- **Weekends:** Only the İcap shift can be assigned.
- **Neonatal Duty:** Can be assigned any day and is predetermined by the user or hospital management.
- **Constraints:** Various constraints are considered, such as no overlapping shifts for the same doctor, rest periods after Neonatal Duty, and specific preferences.

## Technologies And Data Structures Used
- Java
- HashMap
- ArrayList
- Directed Graph
- Object Oriented Programming
- boolean adjacency matrix 
- Array of List
- xlsx file
- Java Swing
- Maven
- Boolean Algebra
- Apache Poi
- JDK 21
- Launch4j
## Classes

### Course Class
- **Description:** Represents a Pediatric Endocrinology Clinic Doctor. Each doctor has:
  - `doctorId`: A unique identifier for representation in the directed graph.
  - `nameSurname`: The name of the doctor.
  - `numberOfPoliklinikRegistered`: The number of poliklinik registrations.
  - `statusPoliklinik1`: Indicates if the doctor is registered to poliklinik 1.
  - `statusPoliklinik2`: Indicates if the doctor is registered to poliklinik 2.
  - `statusPoliklinik3`: Indicates if the doctor is registered to poliklinik 3.

### Shift Class
- **Description:** Represents a shift in the Pediatric Endocrinology Clinic. Each shift has:
  - `shiftId`: A unique identifier for representation in the directed graph.
  - `name`: The name of the shift.

### DirectedGraphRepresentationRelationsAndConstraints Class
- **Description:** Models the relationships and constraints between doctors and their shifts using a Directed Graph data structure. The primary responsibilities include:
  - Assigning doctors to shifts based on their poliklinik preferences.
  - Ensuring constraints are respected, such as non-overlapping shifts and rest periods.
  - Representing relationships and the impact of one shift on another, especially for critical shifts like Neonatal Duty (Yenidoğan Nöbeti).
  - Relationships are represented as a two-dimensional boolean adjacency matrix.

### Scheduler Class
- **Description:** Represents the timetable of a month. Uses the Java Time library to:
  - Detect the number of days in the selected month and year.
  - Determine weekdays and weekends.
  - Assign doctors to the Neonatal Duty based on user input.
  - Set doctor poliklinik preferences.
  - Create a monthly doctor-shift-timetable-scheduler and export it as an Excel file.

### Main Class
- **Description:** Runs the program with graphical user interface.

## Usage

1. Clone the repository.
2. Ensure you have Java installed.(JDK 21 )
3. Run the `Main` class to start the program.
4. Or just click the executable file HospitalTaskScheduler.exe


## File Structure
- HospitalTaskScheduler\src\: source codes
- HospitalTaskScheduler\lib\: Apache poi libraries for dealing with excel files.
- \: executable .exe file named HospitalTaskScheduler.exe
