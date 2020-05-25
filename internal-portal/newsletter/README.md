Newsletter Module

Newsletter form made with SummerNote UI

Access by URL behind:

http://localhost:8081/newsletter

Access swagger:

http://localhost:8081/swagger-ui.html

Create database and tables

Run pgadmin and create database named "lms" , next run the scripts in ./resource folder
You can find the file app-DB-Script.sql in resources folder.
psql -U postgres -d lms -a -f newsletter-DB-Script.sql  
psql -U postgres -d lms -a -f newsletter-DB-Inserts.sql

To see Newletter cards with images it is recommended to use Mozilla Quantum or Chrome with admin rights
