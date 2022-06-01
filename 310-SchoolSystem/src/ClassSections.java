
public class ClassSections {
	private int class_section_id;
    private int class_id;
    private int instructor_id;
    private int term_id;
    private String classCode;
    private String className;
    private String term;

    public ClassSections(int class_section_id, int class_id, int instructor_id, int term_id) {
        this.class_section_id = class_section_id;
        this.class_id = class_id;
        this.instructor_id = instructor_id;
        this.term_id = term_id;
    }
    public ClassSections(int class_section_id, String classCode, String className, String term) {
    	this.class_section_id = class_section_id;
    	this.classCode = classCode;
    	this.className = className;
    	this.term = term;
    }
    public int getClassSectionId() {
        return class_section_id;
    }

    public int getClassId() {
        return class_id;
    }

    public int getInstructorId() {
        return instructor_id;
    }

    public int getTermId() {
        return term_id;
    }
    
    public String toString(){
        return String.format("%s | %s | %s | %s", class_section_id, class_id, instructor_id, term_id);
    }
    
    public String class_sectionToString(){
        return String.format("%s | %s | %s | %s", class_section_id, classCode, className, term);
    }

}
