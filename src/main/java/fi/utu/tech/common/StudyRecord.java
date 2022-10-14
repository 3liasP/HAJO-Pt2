package fi.utu.tech.common;

/**
 * This class requires NO changes
 */
public class StudyRecord {

    public final int grade;
    public final String studentName;
    public final String courseCode; 

    public StudyRecord(String studentName, String courseCode, int grade) {
        this.grade = grade;
        this.studentName = studentName;
        this.courseCode = courseCode;
    }
    
}
