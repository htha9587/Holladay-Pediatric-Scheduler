# Holladay-Pediatric-Scheduler
JavaFX desktop application that can schedule appointments with customers, and their given contacts. Also manages customers, either adding new ones, making edits to preexisting customers, or removing them altogether. The scheduler makes use of a MySQL database which cannot be changed from its original schema.

### Purpose of Application
The Holladay Pediatric Scheduler has been developed to fulfill the requirements set by the World Pediatrician Governing Body. 
It's a JavaFX desktop application made for a company(Holladay Pediatric) that intends to have these functionalities: Scheduling of appointments with customers, and their given contacts.
In addition, the application allows the user to manage customers, either adding new ones, making edits to preexisting customers, or removing them altogether. 
The scheduler makes use of a MySQL database which cannot be changed from its original schema.


### Author Details
Author: Harrison Thacker  
Email: harrison.thacker69@gmail.com  
Version Of Application: 1.0  
Period Of Development: April - July 2023


### IDE + Miscellaneous Dependencies
IntelliJ IDEA 2021.1.3 (Community Edition)  
Build #IC-211.7628.21, built on June 30, 2021.  
Runtime version: 11.0.11+9-b1341.60 amd64  
VM: OpenJDK 64-Bit Server VM by JetBrains s.r.o.  
JDK Version: Java SE 17.0.6  
JavaFX Version: JavaFX-SDK-17.0.6


### Directions on how to run Holladay Pediatric Scheduler

1: Open up IntelliJ.  
2: Click on the File Tab and open up Settings. Look for the Path Variables Section.     
3: Under Path Variables, Create a new path called PATH_TO_FX.  
4: Have it set to the directory of where your JavaFX SDK library is located. (/javafx-sdk-17.0.6/lib.)  
5: Build the Project, and if successful, then click the green run button to start the application.


### Directions on how to operate Holladay Pediatric Scheduler

Upon starting the application, you'll be greeted with the login page. Currently, the only existing users in the database are as follows: 
1: Username and Password are both: "test". 2: Username and Password are both: "admin". Upon using either of these credentials and logging in successfully, you'll be directed to the Scheduler navigator.
From here, you can exit the program, view Scheduler reports, or see the Customers and Appointments windows.  


The Customers window has a TableView showing customers in the database. Here, the user can add new ones, make edits to existing ones, or remove customers from the records entirely. 
You'll also find ComboBoxes for selecting customer countries and first-level divisions(Province) that are part of the country. The available divisions are determined by the country the user has selected. Clicking the Edit button populates the fields with the attributes of the given record. 

By clicking "Save To Records", edits are saved to the records with the database being updated. To edit or delete a customer, you must first select one in the Customer TableView. At the very bottom of the page is a search field where you can look up customers by their name or ID.

Within the Appointments window, there's a TableView listing all appointments. Radio buttons above it allow the user to sort by week, month, or view all appointments.  Here, additions, edits and deletions can be made to scheduled appointments. 
When making edits or additions, there are ComboBoxes for the appointment start + end times, appointment contacts, customers, and scheduler users. For start + end times, these match the company requirements and remain within their business hours: 8 AM to 10 PM EST. As a whole the database and its tables operates in UTC(Military Time). 

By clicking on an Appointment in the TableView, the fields are populated with the attributes of the selected appointment. The "Save To Records" button operates the same way as the one in the customers window. It saves edits made to the appointment records. At the very bottom of the page, there's a search field which can lookup appointments by their name or ID. 

The Scheduler Reports window list three separate tabs. Each with a TableView showing their respective records. There's one listing the total count for customer appointments by month, and total count for appointment types. The second report tab shows the appointment schedule for a given contact.
By using the ComboBox above, you choose to view the schedule for the contact. The third tab is my custom report TableView. It showcases the total count of Customers by their respective countries and divisions.



### Description Of Custom Scheduler Report

My custom report lists the total count of customers by their respective countries and divisions. The SQL query selects the respective columns from the customer and division tables, counts them up as sumCountryDivisionTotal. It sets them equal to their matching foreign keys. 

(Example: customers.Division_ID = first_level_divisions.Division_ID + first_level_divisions.Country_ID = countries.Country_ID. 

Then the columns are grouped together, and ordered in a descending fashion.

Having the code separated with the DAO framework worked really well, and the simplicity then allowed the query PreparedStatement + ResultSet to be retrieved by the
ObservableList in the Report controller class. That would then proceed to set the attributes for my TableView. This report would be handy when looking up the sum total of customers in not only a given country, but even a division.


### MySQL Connector Driver Version Number
mysql-connector-java-8.0.25
