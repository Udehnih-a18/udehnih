package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;

import java.util.UUID;

public interface TutorApplicationService {
    public TutorApplication createApplication(String token);
    public TutorApplication getApplication(String token); 
    public void deleteApplication(String token);
    void approveTutorApplication(UUID applicationID);
    void rejectTutorApplication(UUID applicationId, String rejectionReason);
}
