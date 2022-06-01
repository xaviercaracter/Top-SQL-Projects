import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This application will keep track of things like what classes are offered by
 * the school, and which students are registered for those classes and provide
 * basic reporting. This application interacts with a database to store and
 * retrieve data.
 */
public class SchoolManagementSystem {

    public static void getAllClassesByInstructor(String first_name, String last_name) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = "SELECT \n" + 
            		" instructors.first_name,\n" + 
            		" instructors.last_name,\n" + 
            		" academic_titles.title,\n" + 
            		" classes.code,\n" + 
            		" classes.name,\n" + 
            		" terms.name AS term\n" + 
            		"FROM class_sections \n" + 
            		"INNER JOIN terms ON class_sections.term_id = terms.term_id\n" + 
            		"INNER JOIN classes ON classes.class_id = class_sections.class_id\n" + 
            		"INNER JOIN instructors ON instructors.instructor_id = class_sections.instructor_id\n" + 
            		"INNER JOIN academic_titles ON instructors.academic_title_id = academic_titles.academic_title_id\n" + 
            		"WHERE instructors.first_name LIKE " +"'"+ first_name +"'"+ " and instructors.last_name LIKE " +"'"+ last_name +"'" + ";";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            List<Instructors> instructors = new ArrayList<Instructors>();

            while (resultSet.next()) {
                String first_name_ = resultSet.getString(1);
                String last_name_ = resultSet.getString(2);
                String title = resultSet.getString(3);
                String classCode = resultSet.getString(4);
                String className = resultSet.getString(5);
                String term = resultSet.getString(6);
                
                

                Instructors newInstructor = new Instructors(first_name_, last_name_, title, classCode, className, term);
                instructors.add(newInstructor);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("First Name | Last Name | Instructor Title | Class Code | Class Name | Term" + "\n" + "--------------------------------------------------------------------------------");
            for (Instructors anInstructor : instructors) {
                System.out.println(anInstructor.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get Instructor data");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public static void submitGrade(String studentId, String classSectionID, String grade) {
        Connection connection = null;
        Statement sqlStatement = null;
        int gradeiD = 0;
        if (grade.equals("A") || grade.equals("a")) {
        	gradeiD = 1;
        } else if (grade.equals("B") || grade.equals("b")) {
        	gradeiD = 2;
        } else if (grade.equals("C") || grade.equals("c")) {
        	gradeiD = 3;
        } else if (grade.equals("D") || grade.equals("d")) {
        	gradeiD = 4;
        } else if (grade.equals("F") || grade.equals("f")) {
        	gradeiD = 5;
        }
        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = String.format("Update class_registrations\n" + 
            		"SET grade_id = '%s'\n" + 
            		"WHERE student_id = '%s' AND class_section_id = '%s';",
                    gradeiD, studentId, classSectionID);
            @SuppressWarnings("unused")
			int updateSet = sqlStatement.executeUpdate(sql);

            sqlStatement.close();
            connection.close();
            System.out.println("Grade has been submitted!");
        } catch (SQLException sqlException) {
            System.out.println("Failed to submit grade");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void registerStudent(String studentId, String classSectionID) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = String.format("INSERT INTO class_registrations (student_id, class_section_id) VALUES ('%s' , '%s');",
                    studentId, classSectionID);
            String sql2 = "SELECT class_registration_id, student_id, class_section_id FROM class_registrations\n" + 
            		"ORDER BY class_registration_id DESC\n" + 
            		"LIMIT 1;";
            @SuppressWarnings("unused")
			int insertSet = sqlStatement.executeUpdate(sql);
            ResultSet resultSet = sqlStatement.executeQuery(sql2);
            List<ClassRegistration> registrations = new ArrayList<ClassRegistration>();

            while (resultSet.next()) {
                int class_registration_id = resultSet.getInt(1);
                int student_id = resultSet.getInt(2);
                int class_section_id = resultSet.getInt(3);

                ClassRegistration newRegistration = new ClassRegistration(class_registration_id, student_id, class_section_id);
                registrations.add(newRegistration);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("Class Registration ID | Student ID | Class Section ID" + "\n" + "--------------------------------------------------------------------------------");
            for (ClassRegistration aRegStudent : registrations) {
                System.out.println(aRegStudent.regToString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to register student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void deleteStudent(String studentId) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = String.format("DELETE FROM students WHERE students.student_id = '%s';",
                    studentId);
            @SuppressWarnings("unused")
			int deleteSet = sqlStatement.executeUpdate(sql);
            sqlStatement.close();
            connection.close();
            System.out.println("Student with ID "+studentId+ " was deleted.");
        } catch (SQLException sqlException) {
            System.out.println("Failed to delete student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void createNewStudent(String firstName, String lastName, String birthdate) {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = String.format("INSERT INTO students (first_name, last_name, birthdate) VALUES ('%s' , '%s', '%s');",
                    firstName, lastName, birthdate);
            String sql2 = "SELECT * FROM students\n" + 
            		"ORDER BY student_id DESC\n" + 
            		"LIMIT 1;";
            @SuppressWarnings("unused")
			int insertSet = sqlStatement.executeUpdate(sql);
            ResultSet resultSet = sqlStatement.executeQuery(sql2);
            List<Students> newStudent = new ArrayList<Students>();

            while (resultSet.next()) {
                int student_id = resultSet.getInt(1);
                String first_name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                java.sql.Date birth_date = resultSet.getDate(4);

                Students student = new Students(student_id, first_name, last_name, birth_date);
                newStudent.add(student);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("StudentId | First Name | Last Name | Birthdate" + "\n" + "--------------------------------------------------------------------------------");
            for (Students student : newStudent) {
                System.out.println(student.toString());
            }
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to create student");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public static void listAllClassRegistrations() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = "SELECT  \n" + 
            		" students.student_id,\n" + 
            		" class_sections.class_section_id,\n" + 
            		" students.first_name,\n" + 
            		" students.last_name,\n" + 
            		" classes.code,\n" + 
            		" classes.name,\n" + 
            		" terms.name AS term,\n" + 
            		" grades.letter_grade\n" + 
            		"FROM class_registrations\n" + 
            		"INNER JOIN students on students.student_id = class_registrations.student_id\n" + 
            		"INNER JOIN class_sections on class_sections.class_section_id = class_registrations.class_section_id\n" + 
            		"INNER JOIN terms ON terms.term_id = class_sections.term_id\n" + 
            		"LEFT JOIN grades on grades.grade_id = class_registrations.grade_id\n" + 
            		"INNER JOIN classes ON classes.class_id = class_sections.class_id;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            List<ClassRegistration> classRegistration = new ArrayList<ClassRegistration>();

            while (resultSet.next()) {
                int student_id = resultSet.getInt(1);
                int class_section_id = resultSet.getInt(2);
                String first_name = resultSet.getString(3);
                String last_name = resultSet.getString(4);
                String classCode = resultSet.getString(5);
                String className = resultSet.getString(6);
                String term = resultSet.getString(7);
                String letterGrade = resultSet.getString(8);
                

                ClassRegistration newRegistration = new ClassRegistration(student_id, class_section_id, first_name, last_name, classCode, className, term, letterGrade);
                classRegistration.add(newRegistration);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("Student Id | Class Section Id | First Name | Last Name | Class Code | Class Name | Term | Letter Grade " + "\n" + "--------------------------------------------------------------------------------");
            for (ClassRegistration aClassRegistration : classRegistration) {
                System.out.println(aClassRegistration.toString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class registrations");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void listAllClassSections() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = "SELECT  \n" + 
            		" class_sections.class_section_id,\n" + 
            		" classes.code AS code,\n" + 
            		" classes.name AS name,\n" + 
            		" terms.name AS term\n" + 
            		"FROM class_sections\n" + 
            		"INNER JOIN classes ON classes.class_id = class_sections.class_id\n" + 
            		"INNER JOIN terms on terms.term_id = class_sections.term_id;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            List<ClassSections> classSections = new ArrayList<ClassSections>();

            while (resultSet.next()) {
                int class_section_id = resultSet.getInt(1);
                String classCode = resultSet.getString(2);
                String className = resultSet.getString(3);
                String term = resultSet.getString(4);

                ClassSections newSection = new ClassSections(class_section_id, classCode, className, term);
                classSections.add(newSection);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("Class Section Id | Class Code | Class Name | Term " + "\n" + "--------------------------------------------------------------------------------");
            for (ClassSections aClassSection : classSections) {
                System.out.println(aClassSection.class_sectionToString());
            }
        } catch (SQLException sqlException) {
            System.out.println("Failed to get class sections");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void listAllClasses() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
        	connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = "SELECT * FROM classes;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            List<Classes> classes = new ArrayList<Classes>();

            while (resultSet.next()) {
                int class_id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                String code = resultSet.getString(4);
                int maximum_students = resultSet.getInt(5);

                Classes class1 = new Classes(class_id, name, description, code, maximum_students);
                classes.add(class1);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("ClassId | Class Code | Class Name | Description " + "\n" + "--------------------------------------------------------------------------------");
            for (Classes aClass : classes) {
                System.out.println(aClass.toString());
            }
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to get classes");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public static void listAllStudents() {
        Connection connection = null;
        Statement sqlStatement = null;

        try {
            connection = Database.getDatabaseConnection();
            sqlStatement = connection.createStatement();

            String sql = "SELECT * FROM students;";
            ResultSet resultSet = sqlStatement.executeQuery(sql);

            List<Students> students = new ArrayList<Students>();

            while (resultSet.next()) {
                int student_id = resultSet.getInt(1);
                String first_name = resultSet.getString(2);
                String last_name = resultSet.getString(3);
                java.sql.Date birth_date = resultSet.getDate(4);

                Students student = new Students(student_id, first_name, last_name, birth_date);
                students.add(student);
            }
            sqlStatement.close();
            resultSet.close();
            connection.close();
            System.out.println("StudentId | First Name | Last Name | Birthdate" + "\n" + "--------------------------------------------------------------------------------");
            for (Students student : students) {
                System.out.println(student.toString());
            }
        	
        	
        } catch (SQLException sqlException) {
            System.out.println("Failed to get students");
            System.out.println(sqlException.getMessage());

        } finally {
            try {
                if (sqlStatement != null)
                    sqlStatement.close();
            } catch (SQLException se2) {
            }
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /***
     * Splits a string up by spaces. Spaces are ignored when wrapped in quotes.
     *
     * @param command - School Management System cli command
     * @return splits a string by spaces.
     */
    public static List<String> parseArguments(String command) {
        List<String> commandArguments = new ArrayList<String>();
        Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(command);
        while (m.find()) commandArguments.add(m.group(1).replace("\"", ""));
        return commandArguments;
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the School Management System");
        System.out.println("-".repeat(80));

        Scanner scan = new Scanner(System.in);
        String command = "";

        do {
            System.out.print("Command: ");
            command = scan.nextLine();
            ;
            List<String> commandArguments = parseArguments(command);
            command = commandArguments.get(0);
            commandArguments.remove(0);

            if (command.equals("help")) {
                System.out.println("-".repeat(38) + "Help" + "-".repeat(38));
                System.out.println("test connection \n\tTests the database connection");

                System.out.println("list students \n\tlists all the students");
                System.out.println("list classes \n\tlists all the classes");
                System.out.println("list class_sections \n\tlists all the class_sections");
                System.out.println("list class_registrations \n\tlists all the class_registrations");
                System.out.println("list instructor <first_name> <last_name>\n\tlists all the classes taught by that instructor");


                System.out.println("delete student <studentId> \n\tdeletes the student");
                System.out.println("create student <first_name> <last_name> <birthdate> \n\tcreates a student");
                System.out.println("register student <student_id> <class_section_id>\n\tregisters the student to the class section");

                System.out.println("submit grade <studentId> <class_section_id> <letter_grade> \n\tcreates a student");
                System.out.println("help \n\tlists help information");
                System.out.println("quit \n\tExits the program");
            } else if (command.equals("test") && commandArguments.get(0).equals("connection")) {
                Database.testConnection();
            } else if (command.equals("list")) {
                if (commandArguments.get(0).equals("students")) listAllStudents();
                if (commandArguments.get(0).equals("classes")) listAllClasses();
                if (commandArguments.get(0).equals("class_sections")) listAllClassSections();
                if (commandArguments.get(0).equals("class_registrations")) listAllClassRegistrations();

                if (commandArguments.get(0).equals("instructor")) {
                    getAllClassesByInstructor(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("create")) {
                if (commandArguments.get(0).equals("student")) {
                    createNewStudent(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("register")) {
                if (commandArguments.get(0).equals("student")) {
                    registerStudent(commandArguments.get(1), commandArguments.get(2));
                }
            } else if (command.equals("submit")) {
                if (commandArguments.get(0).equals("grade")) {
                    submitGrade(commandArguments.get(1), commandArguments.get(2), commandArguments.get(3));
                }
            } else if (command.equals("delete")) {
                if (commandArguments.get(0).equals("student")) {
                    deleteStudent(commandArguments.get(1));
                }
            } else if (!(command.equals("quit") || command.equals("exit"))) {
                System.out.println(command);
                System.out.println("Command not found. Enter 'help' for list of commands");
            }
            System.out.println("-".repeat(80));
        } while (!(command.equals("quit") || command.equals("exit")));
        System.out.println("Bye!");
    }
}

