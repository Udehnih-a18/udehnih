package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tutor-applications")
public class TutorApplicationController {

    private final TutorApplicationService tutorApplicationService;

    @Autowired
    public TutorApplicationController(TutorApplicationService tutorApplicationService) {
        this.tutorApplicationService = tutorApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<String> apply(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        tutorApplicationService.createApplication(token);

        return ResponseEntity.ok("Application submitted successfully");
    }

    @GetMapping("/status")
    public ResponseEntity<TutorApplication> getApplicationStatus(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        TutorApplication application = tutorApplicationService.getApplication(token);
        return ResponseEntity.ok(application);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteApplication(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        tutorApplicationService.deleteApplication(token);

        return ResponseEntity.ok("Application deleted successfully");
    }
}
