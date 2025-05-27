package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.request.TutorApplicationRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorDetailRequest;
import id.ac.ui.cs.advprog.udehnihh.dashboard.dto.TutorListRequest;

import java.util.UUID;

public interface TutorApplicationService {
    public TutorApplication createApplication(String token, TutorApplicationRequest request);
    public TutorApplication getApplication(String token); 
    public void deleteApplication(String token);
    void approveTutorApplication(UUID applicationID);
    void rejectTutorApplication(UUID applicationId, String rejectionReason);
    public TutorDetailRequest convertToTutorDetailRequest(TutorApplication application);
    public TutorListRequest convertToTutorListRequest();
}
