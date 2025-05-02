package id.ac.ui.cs.advprog.udehnihh.authentication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.ac.ui.cs.advprog.udehnihh.authentication.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    <Optional>User findByEmail(String email);
    
}
