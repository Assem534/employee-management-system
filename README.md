# employee-management-system
this is simple employee management system to manage Employees you can use database by some button and you can manage salary.
<br>And you can know the absent and attendances by face-recognition.
<br>
<br>
## login-page
![image alt](https://github.com/Assem534/employee-management-system/blob/ef788d13f6826bacca0604c5be7e32b80f19bc2d/Screenshot%202025-04-21%20125204.png) 
<br>
<br>**username:** Admin
<br>**password** 1442009
<br>
<br>
## Home-page
**This pie chart is dinamic**
<br> he calculate the percentage for the number of employees and show it
<br>
<br>
![image alt](https://github.com/Assem534/employee-management-system/blob/ef788d13f6826bacca0604c5be7e32b80f19bc2d/Screenshot%202025-04-21%20125223.png)

<br>
<br>
![image alt](https://github.com/Assem534/employee-management-system/blob/ef788d13f6826bacca0604c5be7e32b80f19bc2d/Screenshot%202025-04-21%20125235.png)
![image alt](https://github.com/Assem534/employee-management-system/blob/ef788d13f6826bacca0604c5be7e32b80f19bc2d/Screenshot%202025-04-21%20125246.png)

## Tools
 **front-end** { javafx , css }
<br> **back-end** { java }
<br> **database** { postgresql }
<br> **face-recogniton** { python }
## Installation for face-recogniton
First, make sure you have Python installed (version 3.7+ recommended).
<br>**To create the virtual file**
<br> write in terminal.
```bash
python -m venv venv
```
**To activate this file**
```bash
venv\Scripts\activate
```
Then, install all required libraries using pip:
```bash
pip install cmake dlib face_recognition opencv-python numpy psycopg2-binary
```
this is for python file.

## installation for java program in intellij
<br> **you must install javafx** 
<br> 
<br>file>project structure >librarys > + > add the lib in the library file.
 
 <br> **To make connection with database**
 <br> go to the DatabaseConnection.java file 
  ```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://your-host:tour-port/your-database-name";
    // if you use local host you will write in host digite localhost and in port 5432
    private static final String USER = "your-username"; //by default it called postgres
    private static final String PASSWORD = "1442009";

    public static Connection connect() {
        Connection conn =null ;
        try {
            conn  = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

}
```
 <br> go to the deshbourdController.java file in the variable is called targetDirectory.
 <br> make his value the path for the image file in the face-recognition file.
 <br>
 ```java
private String targetDirectory = "path/to/face_recognition/image";
```




