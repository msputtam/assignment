
Prerequisite

	java 1.8
	maven


1. Application will refer the below path by default for monthly reports which are defined in application.properties file as below.

	report.basepath= C:\\raboassignment  (Base path of the directory)
	report.csv.filename=records.csv   (CSV file name inside base directory)
	report.xml.filename=records.xml    (XML file name inside base directory)
	report.csv.outputfilename=failed-statements.csv (Output written to this file in the same directory)

2. We can change all the above mentioned properties as command line arguemens as below
   
   java -jar reports-0.0.1-SNAPSHOT.jar --server.port=9099 --report.basepath=C://rabobank 
   
3. Final report generates in the base path which is provided in runtime or default path.
4. logs will generate in project root directory or exceution root directory.
4. to build mvn clean package
5. to run  java -jar reports-0.0.1-SNAPSHOT.jar --server.port=9099 --report.basepath=C://rabobank 
   

	