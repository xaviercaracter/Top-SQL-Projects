
public class Students {
	private int student_id;
    private String first_name;
    private String last_name;
    private java.sql.Date birth_date;

    public Students(int student_id, String first_name, String last_name, java.sql.Date birth_date) {
        this.student_id = student_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.birth_date = birth_date;
    }

    public int getStudentId() {
        return student_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public java.sql.Date getBirthdate() {
        return birth_date;
    }

    public String toString(){
        return String.format("%s | %s | %s | %s", student_id, first_name, last_name, birth_date);
    }

}
