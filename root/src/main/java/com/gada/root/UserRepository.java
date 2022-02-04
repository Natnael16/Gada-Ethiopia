package com.gada.root;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    
    public User findByUsername( String username);

    public Optional<User> findById(Integer l);  
    
    public void deleteByUsername(String username);
    
  
}
