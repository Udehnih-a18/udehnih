package id.ac.ui.cs.advprog.udehnihh.course.service;

import id.ac.ui.cs.advprog.udehnihh.course.dto.request.TutorApplicationRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;

public interface TutorApplicationService {
    public TutorApplication createApplication(String token, TutorApplicationRequest request);
    public TutorApplication getApplication(String token); 
    public void deleteApplication(String token);
}
