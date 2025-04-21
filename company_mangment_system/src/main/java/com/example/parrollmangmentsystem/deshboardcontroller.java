package com.example.parrollmangmentsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

public class deshboardcontroller implements Initializable {
    @FXML
    private PieChart department_chart;
    @FXML
    private Button home_visiable;

    @FXML
    private Button add_employee_visiable;

    @FXML
    private Button Absent_visiable;

    @FXML
    private AnchorPane Home;

    @FXML
    private AnchorPane Add_Employee;

    @FXML
    private AnchorPane Absent;

    @FXML
    private TextField searchBar;

    @FXML
    private TextField add_address;

    @FXML
    private DatePicker add_birthday;

    @FXML
    private ComboBox<String> add_department;

    @FXML
    private TextField add_email;

    @FXML
    private TextField add_name;

    @FXML
    private ComboBox<String> add_gender;

    @FXML
    private Label add_id;

    @FXML
    private TableView<employeeData> table_view;


    @FXML
    private TableColumn<employeeData, Integer> employeeid;

    @FXML
    private TableColumn<employeeData, String> name;

    @FXML
    private TableColumn<employeeData, Date> birthdate;

    @FXML
    private TableColumn<employeeData, String> gender;

    @FXML
    private TableColumn<employeeData, String> email;

    @FXML
    private TableColumn<employeeData, String> phone;

    @FXML
    private TableColumn<employeeData, String> address;

    @FXML
    private TableColumn<employeeData, Double> salary;

    @FXML
    private TableColumn<employeeData, String> department;

    @FXML
    private TableView<attendancesData> attendancestable;

    @FXML
    private TableColumn<attendancesData, Integer> attendances_id;
    @FXML
    private TableColumn<attendancesData, String> attendances_name;
    @FXML
    private TableColumn<attendancesData, java.util.Date> attendances_date;
    @FXML
    private TableColumn<attendancesData, Time> attendances_time;

    private Connection conn;
    private PreparedStatement stmt;


    @FXML
    private TextField add_phone;
    @FXML
    private TextField add_salary;
    @FXML
    private ImageView add_image;
    private Image image;

    @FXML
    private Label imagepath;

    @FXML
    private TextField Enter_the_time;
    @FXML
    private DatePicker Enter_the_date;
    @FXML
    private Button update_the_Attendances;
    @FXML
    private TextField searchBar_for_Absent;
    @FXML
    private Label number_of_male;
    @FXML
    private Label number_of_female;
    @FXML
    private Label number_of;
    private String targetDirectory = "E:\\python\\new face\\image";


    private ObservableList<employeeData> addEmployeeList;

    public ObservableList<employeeData> getEmployees() {
        ObservableList<employeeData> listData = FXCollections.observableArrayList();
        String query = "SELECT  * FROM employees";
        conn = DatabaseConnection.connect();
        try {
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            employeeData employee;
            while (rs.next()) {
                employee = new employeeData(
                        rs.getString("emails"),
                        rs.getDouble("salary"),
                        rs.getString("name"),
                        rs.getString("phones"),
                        rs.getString("gender"),
                        rs.getString("department"),
                        rs.getString("address"),
                        rs.getDate("birthdate"),
                        rs.getInt("employeeid"),
                        rs.getString("image")
                );

                listData.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void addEmployeeListData() {
        addEmployeeList = getEmployees();
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        salary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        email.setCellValueFactory(new PropertyValueFactory<>("emails"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phones"));
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthdate.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        employeeid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        table_view.setItems(addEmployeeList);
    }

    public void addEmplyeeSelected() throws ParseException {
        employeeData employeeD = table_view.getSelectionModel().getSelectedItem();
        int num = table_view.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }

        add_name.setText(employeeD.getName());
        add_gender.setValue(employeeD.getGender());
        add_phone.setText(employeeD.getPhones());
        add_department.setValue(employeeD.getDepartment());
        add_email.setText(employeeD.getEmails());
        add_address.setText(employeeD.getAddress());
        add_salary.setText(String.valueOf(employeeD.getSalary()));
        Date sqlDate = (Date) employeeD.getBirthdate();
        LocalDate birthdate = sqlDate.toLocalDate();
        add_birthday.setValue(birthdate);
        String uri = employeeD.getImage();
        image = new Image(uri, 117, 148, false, true);
        add_image.setImage(image);
        add_id.setText(String.valueOf(employeeD.getEmployeeid()));
        imagepath.setText(employeeD.getImage());
    }


    private ObservableList<attendancesData> addAttendancesList;

    public ObservableList<attendancesData> getAttendances() {
        ObservableList<attendancesData> listData = FXCollections.observableArrayList();
        String query = "SELECT employeeid , name ,date , time FROM attendances";
        conn = DatabaseConnection.connect();
        try {
            stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            attendancesData attendances;
            while (rs.next()) {
                attendances = new attendancesData(
                        rs.getInt("employeeid"),
                        rs.getString("name"),
                        rs.getDate("date"),
                        rs.getTime("time")
                );

                listData.add(attendances);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listData;
    }

    public void addAttendancesListData() {
        addAttendancesList = getAttendances();
        attendances_id.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        attendances_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        attendances_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        attendances_time.setCellValueFactory(new PropertyValueFactory<>("time"));
        attendancestable.setItems(addAttendancesList);
    }

    public void addAttendancesSelected() throws ParseException {
        attendancesData attendancesData = attendancestable.getSelectionModel().getSelectedItem();
        int num = attendancestable.getSelectionModel().getSelectedIndex();
        if ((num - 1) < -1) {
            return;
        }

        Enter_the_time.setText(String.valueOf(attendancesData.getTime()));
        Date sqlDate = (Date) attendancesData.getDate();
        LocalDate Date_for_attendances = sqlDate.toLocalDate();
        Enter_the_date.setValue(Date_for_attendances);
        add_id.setText(String.valueOf(attendancesData.getEmployeeid()));
    }


    public void Update_Data_Attendances() {
        Time time_for_Attendances;
        Date Attendances_Date;

        String Name = Enter_the_time.getText();
        time_for_Attendances = Time.valueOf(Name);
        Attendances_Date = Date.valueOf(Enter_the_date.getValue());

        conn = DatabaseConnection.connect();
        if (conn == null) {
            ErrorAlert("Error", "Database connection failed!");
            return;
        }
        String sql = "UPDATE attendances " +
                "SET time=?," +
                " date=? " +
                " WHERE employeeid=? ";
        try {
            int id = Integer.parseInt(add_id.getText());
            stmt = conn.prepareStatement(sql);
            stmt.setTime(1, time_for_Attendances);
            stmt.setDate(2, Attendances_Date);
            stmt.setInt(3, id);
            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to Updates values ?");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design.css")).toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    addEmployeeListData();
                    addAttendancesListData();
                    InformationAlert("Success", "Updated Employee data successfully!");
                    SearchBar_Attendances();
                }

            } else {
                ErrorAlert("Error", "No employee found with the given ID.");
            }
        } catch (SQLException q) {
            ErrorAlert("Database error", q.getMessage());
        }

    }

    @FXML
    private void handleImportImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        Stage stage = (Stage) add_image.getScene().getWindow();
        File file = fileChooser.showOpenDialog(stage);

        conn = DatabaseConnection.connect();
        String sql = "select pg_get_serial_sequence('employees','employeeid')";
        String sequenceName = "";
        try {
            stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                sequenceName = rs.getString(1);
            }
        } catch (SQLException e) {

        }
        String nameOfPicture = "";

        if (sequenceName != null) {
            conn = DatabaseConnection.connect();
            String sql2 = "select last_value from " + sequenceName;
            try {
                stmt = conn.prepareStatement(sql2);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    nameOfPicture = rs.getString(1);
                }
            } catch (SQLException e) {

            }
        }

        if (file != null) {
            try {
                String extension = file.getName().substring(file.getName().lastIndexOf("."));
                String newFileName = (Integer.valueOf(nameOfPicture) + 1) + extension;
                File folder = new File(targetDirectory);
                if (!folder.exists()) folder.mkdirs();
                File newFile = new File(folder, newFileName);
                Files.copy(file.toPath(), newFile.toPath());
                Image image = new Image("file:" + targetDirectory + "\\" + newFileName, 117, 148, false, true);
                add_image.setImage(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


    @FXML
    public void Insert_employee() {
        String Name = add_name.getText();
        String gender = add_gender.getValue();
        String email = add_email.getText();
        String phone = add_phone.getText();
        String address = add_address.getText();
        String salary = add_salary.getText();
        Image img = add_image.getImage();
        String department = add_department.getValue();
        String imgURl;
        Date birthday;
        if (Error_funcation()) {
            return;
        }

        birthday = Date.valueOf(add_birthday.getValue());
        imgURl = img.getUrl();
        if (imgURl.startsWith("file:/")) {
            imgURl = imgURl.substring(6);
        }

        conn = DatabaseConnection.connect();
        if (conn == null) {
            ErrorAlert("Error", "Database connection failed!");
            return;
        }
        String sql = "INSERT INTO employees (name, birthdate, gender,emails, phones, address, salary,image, department ) VALUES ( ?, ?, ?, ?, ?, ?,?,?,?)";

        try {
            double salary_Double = Double.parseDouble(salary);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Name);
            stmt.setDate(2, birthday);
            stmt.setString(3, gender);
            stmt.setString(4, email);
            stmt.setString(5, phone);
            stmt.setString(6, address);
            stmt.setDouble(7, salary_Double);
            stmt.setString(8, imgURl);
            stmt.setString(9, department);
            stmt.executeUpdate();
            addEmployeeListData();
            InformationAlert("Success", "Employee added successfully!");
            SearchBar();

        } catch (SQLException exception) {

            ErrorAlert("Database error", exception.getMessage());

        }
        try {
            number_of_male.setText(String.valueOf(count_gender("Male")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            number_of_female.setText(String.valueOf(count_gender("Female")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int numM = Integer.valueOf(number_of_male.getText());
        int numF = Integer.valueOf(number_of_female.getText());
        int total = numM + numF;
        number_of.setText(String.valueOf(total));
        try {
            setupDepartmentPieChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (Error_funcation()==false){
        clear();
        }

    }


    public void Update_Data() {
        String Name = add_name.getText();
        String gender = add_gender.getValue();
        String email = add_email.getText();
        String phone = add_phone.getText();
        String address = add_address.getText();
        String salary = add_salary.getText();
        Image img = add_image.getImage();
        String department = add_department.getValue();
        String imgURl;
        Date Birthdate;
        Error_funcation();
        Birthdate = Date.valueOf(add_birthday.getValue());
        imgURl = img.getUrl();
        if (imgURl.startsWith("file:/")) {
            imgURl = imgURl.substring(6);  // Remove the first 5 characters ("file:/")
        }
        conn = DatabaseConnection.connect();
        if (conn == null) {
            ErrorAlert("Error", "Database connection failed!");
            return;
        }
        String sql = "UPDATE employees SET name=?," +
                " birthdate=? ," +
                "phones=?," +
                "emails=? ," +
                "address=?," +
                "image=?," +
                "gender=?," +
                "salary=?," +
                "department=?" +
                " WHERE employeeid=? ";
        try {
            int id = Integer.parseInt(add_id.getText());
            double salary_Double = Double.parseDouble(salary);
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, Name);
            stmt.setDate(2, Birthdate);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, imgURl);
            stmt.setString(7, gender);
            stmt.setDouble(8, salary_Double);
            stmt.setString(9, department);
            stmt.setInt(10, id);
            int rowsUpdated = stmt.executeUpdate();
            if (Error_funcation()){
                return;
            }
            if (rowsUpdated > 0) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Update");
                alert.setHeaderText(null);
                alert.setContentText("Do you want to Updates values ?");
                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design.css")).toExternalForm());
                dialogPane.getStyleClass().add("dialog-pane");
                if (alert.showAndWait().get() == ButtonType.OK) {
                    addEmployeeListData();
                    InformationAlert("Success", "Updated Employee data successfully!");
                    SearchBar();
                }

            } else {
                ErrorAlert("Error", "No employee found with the given ID.");
            }
        } catch (SQLException q) {
            ErrorAlert("Database error", q.getMessage());
        }
        try {
            number_of_male.setText(String.valueOf(count_gender("Male")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            number_of_female.setText(String.valueOf(count_gender("Female")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int numM = Integer.valueOf(number_of_male.getText());
        int numF = Integer.valueOf(number_of_female.getText());
        int total = numM + numF;
        number_of.setText(String.valueOf(total));
        try {
            setupDepartmentPieChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (Error_funcation()==false){
            clear();
        }
    }

    public void DleteEmployee() {
        String imageuri = imagepath.getText();
        if (Error_funcation()) {
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to remove this employee?");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");

        if (imageuri.startsWith("file:")) {
            imageuri = imageuri.substring(5);
        }
        if (imageuri.startsWith("file:/")) {
            imageuri = imageuri.substring(6);
        }
        File file = new File(imageuri);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Image deleted");
            }
        }

        conn = DatabaseConnection.connect();
        if (conn == null) {
            ErrorAlert("Error", "Connection with database failed");
            return;
        }

        String deleteEmployeeSql = "DELETE FROM employees WHERE employeeid=?";
        String deleteAttendancesSql = "DELETE FROM attendances WHERE employeeid=?";

        if (alert.showAndWait().get() == ButtonType.OK) {
            try {
                int id = Integer.parseInt(add_id.getText());

                stmt = conn.prepareStatement(deleteAttendancesSql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                stmt = conn.prepareStatement(deleteEmployeeSql);
                stmt.setInt(1, id);
                int rowsUpdated = stmt.executeUpdate();
                addEmployeeListData();
                addAttendancesListData();

                if (rowsUpdated > 0) {
                    InformationAlert("Delete", "You removed the employee with ID: " + id);
                    addEmployeeListData();
                    SearchBar();
                }

            } catch (SQLException e1) {
                ErrorAlert("Database Error", e1.getMessage());
            }

            // Update UI elements
            try {
                number_of_male.setText(String.valueOf(count_gender("Male")));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                number_of_female.setText(String.valueOf(count_gender("Female")));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            int numM = Integer.valueOf(number_of_male.getText());
            int numF = Integer.valueOf(number_of_female.getText());
            int total = numM + numF;
            number_of.setText(String.valueOf(total));
            try {
                setupDepartmentPieChart();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            // Clear data if no errors
            if (!Error_funcation()) {
                clear();
            }
        }
    }

    public void SearchBar() {
        FilteredList<employeeData> filter = new FilteredList<>(addEmployeeList, _ -> true);

        searchBar.textProperty().addListener((_, _, newValue) -> filter.setPredicate(predicateEmployeeData -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateEmployeeData.getEmployeeid()).toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getAddress() != null && predicateEmployeeData.getAddress().toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getBirthdate() != null && String.valueOf(predicateEmployeeData.getBirthdate()).startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getDepartment() != null && predicateEmployeeData.getDepartment().toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getGender() != null && predicateEmployeeData.getGender().toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getPhones() != null && predicateEmployeeData.getPhones().toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getEmails() != null && predicateEmployeeData.getEmails().toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateEmployeeData.getSalary() != null && String.valueOf(predicateEmployeeData.getSalary()).startsWith(searchKey)) {
                return true;
            }

            return false;
        }));

        SortedList<employeeData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(table_view.comparatorProperty());
        table_view.setItems(sortList);
    }

    public void SearchBar_Attendances() {
        FilteredList<attendancesData> filter = new FilteredList<>(addAttendancesList, _ -> true);

        searchBar_for_Absent.textProperty().addListener((_, _, newValue) -> filter.setPredicate(predicateAttendancesData -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String searchKey = newValue.toLowerCase();

            if (String.valueOf(predicateAttendancesData.getEmployeeid()).toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateAttendancesData.getTime() != null && String.valueOf(predicateAttendancesData.getTime()).toLowerCase().startsWith(searchKey)) {
                return true;
            }
            if (predicateAttendancesData.getDate() != null && String.valueOf(predicateAttendancesData.getDate()).startsWith(searchKey)) {
                return true;
            }
            if (predicateAttendancesData.getName() != null && predicateAttendancesData.getName().toLowerCase().startsWith(searchKey)) {
                return true;
            }


            return false;
        }));

        SortedList<attendancesData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(attendancestable.comparatorProperty());
        attendancestable.setItems(sortList);
    }


    public int count_gender(String gender) throws SQLException {
        conn = DatabaseConnection.connect();
        if (conn == null) {
            ErrorAlert("Error", "The connection with the database failed.");
        }

        String sql = "SELECT COUNT(*) FROM employees WHERE gender = ?";
        int count = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, gender);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public int count(String department) {
        conn = DatabaseConnection.connect();
        if (conn == null) {
            ErrorAlert("Error", "The connection with the database failed.");
        }

        String sql = "SELECT COUNT(*) FROM employees WHERE department = ?";
        int count = 0;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, department);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    private void setupDepartmentPieChart() throws SQLException {
        ObservableList<PieChart.Data> chart = FXCollections.observableArrayList(
                new PieChart.Data("Frontend Development", count("Frontend Development")),
                new PieChart.Data("Backend Development", count("Backend Development")),
                new PieChart.Data("Full-Stack Development", count("Full-Stack Development")),
                new PieChart.Data("Mobile Development", count("Mobile Development")),
                new PieChart.Data("DevOps Engineering", count("DevOps Engineering")),
                new PieChart.Data("Manual Testing", count("Manual Testing")),
                new PieChart.Data("Automated Testing", count("Automated Testing")),
                new PieChart.Data("Performance Testing", count("Performance Testing")),
                new PieChart.Data("User Interface Design", count("User Interface Design")),
                new PieChart.Data("Graphic Design", count("Graphic Design")),
                new PieChart.Data("Data Analysis", count("Data Analysis")),
                new PieChart.Data("Technical Support", count("Technical Support")),
                new PieChart.Data("Security Analysis", count("Security Analysis")),
                new PieChart.Data("Machine Learning Engineering", count("Machine Learning Engineering")),
                new PieChart.Data("Cloud Management", count("Cloud Management"))

        );

        department_chart.setData(chart);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setupDepartmentPieChart();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        add_department.getItems().addAll(department_opions());
        String[] gendar_options = {"Male", "Female"};
        add_gender.getItems().addAll(gendar_options);

        addEmployeeListData();
        addAttendancesListData();
        SearchBar();
        SearchBar_Attendances();
        table_view.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> {
            try {
                addEmplyeeSelected();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
        attendancestable.getSelectionModel().selectedItemProperty().addListener((_, _, _) -> {
            try {
                addAttendancesSelected();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });

        try {
            number_of_male.setText(String.valueOf(count_gender("Male")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            number_of_female.setText(String.valueOf(count_gender("Female")));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        int numM = Integer.valueOf(number_of_male.getText());
        int numF = Integer.valueOf(number_of_female.getText());
        int total = numM + numF;
        number_of.setText(String.valueOf(total));


    }


    public String[] department_opions() {
        return new String[]{
                "Frontend Development",
                "Backend Development",
                "Full-Stack Development",
                "Mobile Development",
                "DevOps Engineering",
                "Manual Testing",
                "Automated Testing",
                "Performance Testing",
                "User Interface Design",
                "Graphic Design",
                "Data Analysis",
                "Technical Support",
                "Security Analysis",
                "Machine Learning Engineering",
                "Cloud Management"
        };
    }

    public void ErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();
    }

    public void InformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        alert.showAndWait();
    }

    public boolean Error_funcation() {
        String Name = add_name.getText();
        String gender = add_gender.getValue();
        String email = add_email.getText();
        String phone = add_phone.getText();
        String address = add_address.getText();
        String salary = add_salary.getText();
        Image img = add_image.getImage();
        String department = add_department.getValue();

        boolean hasError = false;

        if (Name.isEmpty()) {
            add_name.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_name.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (add_birthday.getValue() == null) {
            add_birthday.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_birthday.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (gender == null) {
            add_gender.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_gender.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (email.isEmpty()) {
            add_email.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_email.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (phone.isEmpty()) {
            add_phone.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_phone.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (department == null) {
            add_department.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_department.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (address.isEmpty()) {
            add_address.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_address.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }

        if (salary.isEmpty()) {
            add_salary.setStyle("-fx-border-color:red;");
            hasError = true;
        } else {
            add_salary.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        }
        try {
            double salary_double = Double.parseDouble(add_salary.getText());

        } catch (Exception e) {
            ErrorAlert("Error","salary must be Double value !");
            hasError=true;
        }

        if (hasError) {
            ErrorAlert("Error", "All fields must be filled!");
            return hasError;
        }

        if (!email.endsWith("@gmail.com")) {
            add_email.setStyle("-fx-border-color:red;");
            ErrorAlert("Error", "Please use a valid Gmail address ending with @gmail.com!");
            hasError = true;
        } else {
            add_email.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");

        }


        if (img == null) {
            ErrorAlert("Error", "You must import an image!");
            hasError = true;
        }

        return hasError;
    }

    public void clear() {
        add_name.clear();
        add_email.clear();
        add_salary.clear();
        add_phone.clear();
        add_address.clear();
        add_birthday.setValue(null);
        add_gender.getSelectionModel().clearSelection();
        add_department.getSelectionModel().clearSelection();
        add_birthday.setPromptText("birthday");
        add_image.setImage(null);

        add_name.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_salary.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_address.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_department.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_email.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_phone.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_gender.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");
        add_birthday.setStyle("-fx-border-color:linear-gradient(to bottom right ,#272b3f,#256b51);");

    }


    @FXML
    public void log_out(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("logout");
        alert.setHeaderText(null);
        alert.setContentText("Do you want to save before logout?");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image("file:src\\images\\icon.jpeg"));
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(Objects.requireNonNull(getClass().getResource("design.css")).toExternalForm());
        dialogPane.getStyleClass().add("dialog-pane");
        if (alert.showAndWait().get() == ButtonType.OK) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
            Parent root = loader.load();
            Scene newScene = new Scene(root);
            Stage login = new Stage();
            login.setTitle("payroll management system");
            Image image = new Image("file:src\\images\\icon.jpeg");
            login.getIcons().add(image);
            login.setScene(newScene);
            login.setResizable(false);
            login.show();
            Stage loginStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            loginStage.close();
        }

    }

    public void Set_Home() {
        Home.setVisible(true);
        Add_Employee.setVisible(false);
        Absent.setVisible(false);
        home_visiable.setStyle("-fx-background-color:linear-gradient(to bottom right ,#3a4368,#256b51,#28966c);");
        add_employee_visiable.setStyle("-fx-background-color:transparent;");
        Absent_visiable.setStyle("-fx-background-color:transparent;");

    }

    public void Set_Add_Employee() {
        Home.setVisible(false);
        Add_Employee.setVisible(true);
        Absent.setVisible(false);
        add_employee_visiable.setStyle("-fx-background-color:linear-gradient(to bottom right ,#3a4368,#256b51,#28966c);");
        home_visiable.setStyle("-fx-background-color:transparent;");
        Absent_visiable.setStyle("-fx-background-color:transparent;");
        SearchBar();

    }

    public void Set_Apsent() {
        Home.setVisible(false);
        Add_Employee.setVisible(false);
        Absent.setVisible(true);
        Absent_visiable.setStyle("-fx-background-color:linear-gradient(to bottom right ,#3a4368,#256b51,#28966c);");
        add_employee_visiable.setStyle("-fx-background-color:transparent;");
        home_visiable.setStyle("-fx-background-color:transparent;");

    }


}
