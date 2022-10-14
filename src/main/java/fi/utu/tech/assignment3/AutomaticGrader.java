package fi.utu.tech.assignment3;

import java.util.List;
import java.util.Random;

import fi.utu.tech.common.Submission;

public class AutomaticGrader extends Thread {

    private List<Submission> ungradedSubmissions;
    private List<Submission> gradedSubmissions;
    private Random rnd = new Random();

    public AutomaticGrader(List<Submission> ungradedSubmissions, List<Submission> gradedSubmissions) {
        this.ungradedSubmissions = ungradedSubmissions;
        this.gradedSubmissions = gradedSubmissions;
    }

    @Override
    public void run() {
        for (var s : ungradedSubmissions) {
            gradedSubmissions.add(grade(s));
        }

    }

    public Submission grade(Submission s) {
        try {
            Thread.sleep(s.getDifficulty());
        } catch (InterruptedException e) {
            System.err.println("Who dared to interrupt my sleep?!");
        }
        return s.grade(rnd.nextInt(6));
    }


}