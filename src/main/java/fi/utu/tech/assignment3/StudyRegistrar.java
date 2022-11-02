package fi.utu.tech.assignment3;

import java.util.List;


public class StudyRegistrar extends Thread {

    private List<Submission> submissionQueue;
    private List<StudyRecord> finalGrades;
    private String courseCode;

    public StudyRegistrar(List<Submission> gradedSubmissions, List<StudyRecord> finalGrades, String courseCode) {
        submissionQueue = gradedSubmissions;
        this.finalGrades = finalGrades;
        this.courseCode = courseCode;
    }

    public void run() {
        while (true) {
            var s = submissionQueue.remove(0);
            addToStudyRegistery(s.getGrade(), s.getSubmittedBy());

        }
    }

    void addToStudyRegistery(int grade, String studentName) {
        System.out.printf("Adding grade %d for %s on %s%n", grade, studentName, this.courseCode);
        finalGrades.add(new StudyRecord(studentName, this.courseCode, grade));
    }
}
