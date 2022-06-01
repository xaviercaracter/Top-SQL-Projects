
public class Classes {
	private int class_id;
    private String name;
    private String description;
    private String code;
    private int maximum_students;

    public Classes(int class_id, String name, String description, String code, int maximum_students) {
        this.class_id = class_id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.maximum_students = maximum_students;
    }

    public int getClassId() {
        return class_id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCode() {
        return code;
    }
    
    public int getMaximumStudents() {
    	return maximum_students;
    }
    public String toString(){
        return String.format("%s | %s | %s | %s", class_id, code, name, description);
    }

}
