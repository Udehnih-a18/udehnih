package id.ac.ui.cs.advprog.udehnihh.course.controller;

import id.ac.ui.cs.advprog.udehnihh.course.dto.request.TutorApplicationRequest;
import id.ac.ui.cs.advprog.udehnihh.course.dto.response.TutorApplicationResponse;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.service.TutorApplicationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tutor-applications")
public class TutorApplicationController {

    private final TutorApplicationServiceImpl tutorApplicationService;

    @Autowired
    public TutorApplicationController(TutorApplicationServiceImpl tutorApplicationService) {
        this.tutorApplicationService = tutorApplicationService;
    }

    @PostMapping("/apply")
    public ResponseEntity<TutorApplicationResponse> apply(
            @RequestHeader("Authorization") String authorizationHeader,
            @RequestBody TutorApplicationRequest request) {
        
        String token = authorizationHeader.replace("Bearer ", "");

        TutorApplication application = tutorApplicationService.createApplication(token, request);

        TutorApplicationResponse response = mapToResponse(application);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/status")
    public ResponseEntity<TutorApplicationResponse> getApplicationStatus(
            @RequestHeader("Authorization") String authorizationHeader) {
        
        String token = authorizationHeader.replace("Bearer ", "");

        TutorApplication application = tutorApplicationService.getApplication(token);

        if (application == null) {
            return ResponseEntity.notFound().build();
        }

        TutorApplicationResponse response = mapToResponse(application);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteApplication(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "");

        tutorApplicationService.deleteApplication(token);

        return ResponseEntity.ok("Application deleted successfully");
    }

    // Helper method
    private TutorApplicationResponse mapToResponse(TutorApplication application) {
        TutorApplicationResponse response = new TutorApplicationResponse();

        response.setId(application.getId());
        response.setApplicantId(application.getApplicant().getId());
        response.setApplicantName(application.getApplicant().getFullName());
        response.setMotivation(application.getMotivation());
        response.setExperience(application.getExperience());
        response.setStatus(application.getStatus());
        response.setCreatedAt(application.getCreatedAt());

        return response;
    }
}
