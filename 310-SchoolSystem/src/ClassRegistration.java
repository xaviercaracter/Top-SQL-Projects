
public class ClassRegistration {
	private int class_registration_id;
    private int class_section_id;
    private int student_id;
    private int grade_id;
    private java.sql.Timestamp signup_timestamp;
	private String first_name;
	private String last_name;
	private String classCode;
	private String className;
	private String term;
	private String letterGrade;

    public ClassRegistration(int class_registration_id, int class_section_id, int student_id, int grade_id, java.sql.Timestamp signup_timestamp) {
        this.class_registration_id = class_registration_id;
        this.class_section_id = class_section_id;
        this.student_id = student_id;
        this.grade_id = grade_id;
        this.signup_timestamp = signup_timestamp;
    }
    public ClassRegistration(int student_id,  int class_section_id, String first_name, String last_name, String classCode, String className, String term, String letterGrade) {
        this.student_id =student_id;
        this.class_section_id = class_section_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.classCode = classCode;
        this.className = className;
        this.term = term;
        this.letterGrade = letterGrade;
        
    }
    public ClassRegistration(int class_registration_id, int student_id, int class_section_id) {
    	this.class_registration_id = class_registration_id;
    	this.student_id = student_id;
    	this.class_section_id = class_section_id;
    }

    public int getClassRegistrationId() {
        return class_registration_id;
    }

    public int getClassSectionId() {
        return class_section_id;
    }

    public int getStudentId() {
        return student_id;
    }

    public int getGradeId() {
        return grade_id;
    }
    
    public java.sql.Timestamp getSignupTimestamp() {
    	return signup_timestamp;
    }
    public String toString(){
        return String.format("%s | %s | %s | %s | %s | %s | %s | %s", student_id, class_section_id, first_name, last_name, classCode, className, term, letterGrade);
    }
    public String regToString() {
    	return String.format("%s | %s | %s", class_registration_id, student_id, class_section_id);
    }
}
