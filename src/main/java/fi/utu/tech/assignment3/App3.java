package fi.utu.tech.assignment3;

import java.util.ArrayList;
import java.util.List;

import fi.utu.tech.common.StudyRecord;
import fi.utu.tech.common.Submission;
import fi.utu.tech.common.SubmissionGenerator;
import fi.utu.tech.common.SubmissionGenerator.Strategy;

public class App3 {

    public static void main(String[] args) {
        List<Submission> gradedSubmissions = new ArrayList<Submission>(30);
        
        // Palautusköntit generoidaan täällä ja annetaan automaattitarkastajille
        List<AutomaticGrader> autograders = new ArrayList<>();
        for (int i=0; i<50; i++) {
            var submissions = SubmissionGenerator.generateSubmissions(20, 2000, Strategy.RANDOM);
            autograders.add(new AutomaticGrader(submissions, gradedSubmissions));
        }

        // "Arvosanarekisteri" - StudyRegistrar kirjaa arvosanat tänne
        List<StudyRecord> studyRegistry = new ArrayList<>();
        // Annetaan studyRegistrarille viittaus arvosanarekisteriin, sekä 
        StudyRegistrar studyRegistrar = new StudyRegistrar(gradedSubmissions, studyRegistry, "DTEK0095");
        studyRegistrar.start();

        for (var grader : autograders) {
            grader.start();
        }

        for (var grader : autograders) {
            try {
                grader.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        studyRegistrar.interrupt();
    }
}
