package id.ac.ui.cs.advprog.udehnihh.course.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import id.ac.ui.cs.advprog.udehnihh.authentication.enums.Role;
import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;
import id.ac.ui.cs.advprog.udehnihh.authentication.repository.UserRepository;
import id.ac.ui.cs.advprog.udehnihh.authentication.service.JwtService;
import id.ac.ui.cs.advprog.udehnihh.course.model.TutorApplication;
import id.ac.ui.cs.advprog.udehnihh.course.repository.TutorApplicationRepository;

@Service
public class TutorApplicationServiceImpl implements TutorApplicationService {

    private final TutorApplicationRepository tutorApplicationRepository;
    private final UserRepository userRepository;
    private final JwtService jwtTokenUtil;

    @Autowired
    public TutorApplicationServiceImpl(TutorApplicationRepository tutorApplicationRepository,
                                   UserRepository userRepository,
                                   JwtService jwtTokenUtil) {
        this.tutorApplicationRepository = tutorApplicationRepository;
        this.userRepository = userRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    public TutorApplication createApplication(String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            throw new IllegalStateException("User not found");
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
        application.setStatus(TutorApplication.ApplicationStatus.PENDING);

        return tutorApplicationRepository.save(application);
    }

    public TutorApplication getApplication(String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseThrow(() -> new IllegalStateException("User not found"));

        return tutorApplicationRepository.findByApplicant(user)
                .orElseThrow(() -> new IllegalStateException("No application found"));
    }

    public void deleteApplication(String token) {
        String email = jwtTokenUtil.getEmailFromToken(token);
        Optional<User> optionalUser = userRepository.findByEmail(email);

        User user = optionalUser.orElseThrow(() -> new IllegalStateException("User not found"));

        TutorApplication application = tutorApplicationRepository.findByApplicant(user)
                .orElseThrow(() -> new IllegalStateException("No application found"));

        tutorApplicationRepository.delete(application);
    }
}
