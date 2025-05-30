package id.ac.ui.cs.advprog.udehnihh.course.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.course.dto.request.TutorApplicationRequest;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.repository.TutorApplicationRepository;

@Service
public class TutorApplicationServiceImpl implements TutorApplicationService {

    private final TutorApplicationRepository tutorApplicationRepository;
    private final UserRepository userRepository;
    private final JwtService jwtTokenUtil;
    private static final String USER_NOT_FOUND = "User not found";
    private static final String NO_APPLICATION_FOUND = "No application found";

    @Autowired
    public TutorApplicationServiceImpl(TutorApplicationRepository tutorApplicationRepository,
                                   UserRepository userRepository,
                                   JwtService jwtTokenUtil) {
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.userRepository = userRepository;

        this.jwtTokenUtil = jwtTokenUtil;
    }

    public TutorApplication createApplication(String token, TutorApplicationRequest request) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException(USER_NOT_FOUND);
        }

        User user = optionalUser.get();

        if (!user.getRole().equals(Role.STUDENT)) {
            throw new IllegalStateException("Only students can apply as tutor");
        }

        if (tutorApplicationRepository.existsByApplicant(user)) {
            throw new IllegalStateException("User has already applied");
        }

        TutorApplication application = new TutorApplication();
        application.setApplicant(user);
        application.setExperience(request.getExperience());
        application.setMotivation(request.getMotivation());
        application.setStatus(TutorApplication.ApplicationStatus.PENDING);

        return tutorApplicationRepository.save(application);
    }

    public TutorApplication getApplication(String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseThrow(() -> new IllegalStateException(USER_NOT_FOUND));

        return tutorApplicationRepository.findByApplicant(user)
                .orElseThrow(() -> new IllegalStateException(NO_APPLICATION_FOUND));
    }

    public void deleteApplication(String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseThrow(() -> new IllegalStateException(USER_NOT_FOUND));

        TutorApplication application = tutorApplicationRepository.findByApplicant(user)
                .orElseThrow(() -> new IllegalStateException(NO_APPLICATION_FOUND));

        tutorApplicationRepository.delete(application);
    }
}
