In this lab you will have to develop a server-side web application in PHP. The web application has to manipulate a Mysql database with 1 to 3 tables and should implement the following base operations on these tables: select, insert, delete, update. Also the web application must use AJAX for getting data asynchronously from the web server and the web application should contain at least 5 web pages (client-side html or server-side php).

For the database, you can use the mysql database on www.scs.ubbcluj.ro. On this myql server you have an account, a password and a database, all identical to your username and password on the SCS network.

Please make sure that you avoid sql-injection attacks when working with the database.

Have in mind the user experience when you implement the problem:

    add different validation logic for input fields
    do not force the user to input an ID for an item if he wants to delete/edit/insert it; this should happen automatically (e.g. the user clicks an item from a list, and a page/modal prepopulated with the data for that particular item is opened, where the user can edit it)
    add confirmation when the user deletes/cancels an item
    do a bare minimum CSS that at least aligns the various input fields

Documentation can be found at:
1) http://www.cs.ubbcluj.ro/~forest/wp
2) http://www.php.net/manual/en
3) http://www.w3schools.com/php
4) http://www.w3schools.com/ajax 

Write a web application for assigning grades to students for various courses. The application will have two types of users: professors and students. A student can only display his own grades. Students are organized in groups. A professor can add or modify a grade for the students in a group at a specific course. In order to retrieve the list of students from a group, the web application will use AJAX. Prior to using the application the users (professors and students) must log in using a username and a password. Students in a group should be displayed on pages with maximum 4 students on a page (you should be able to go to the previous and the next page). 