
public class Instructors {
	private int instructor_id;
    private String first_name;
    private String last_name;
    private int academic_title_id;
	private String title;
	private String classCode;
	private String className;
	private String term;

    public Instructors(int instructor_id, String first_name, String last_name, int academic_title_id) {
        this.instructor_id = instructor_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.academic_title_id = academic_title_id;
    }
    public Instructors(String first_name, String last_name, String title, String classCode, String className, String term) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.title = title;
        this.classCode = classCode;
        this.className = className;
        this.term = term;
    }

    public int getInstructorId() {
        return instructor_id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public int getAcademicTitleId() {
        return academic_title_id;
    }

    public String toString(){
        return String.format("%s | %s | %s | %s | %s | %s", first_name, last_name, title, classCode, className, term);
    }

}
