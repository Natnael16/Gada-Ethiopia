package com.gada.root;

import static org.assertj.core.api.Assertions.assertThat;
 
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
 
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
 
    @Autowired
    private TestEntityManager entityManager;
     
    @Autowired
    private UserRepository repo;
    @Autowired
    private RoleRepository roleRepo;
     
    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("red04");
        user.setPassword("red2022");
        user.setFirstName("yared");
        user.setLastName("Tegegn");
         
        User savedUser = repo.save(user);
         
        User existUser = entityManager.find(User.class, savedUser.getUser_id());
         
        assertThat(user.getUsername()).isEqualTo(existUser.getUsername());
         
}
@Test 
public void testAddRoleToNewUser() {
    
    Role roleAdmin = roleRepo.findByName("Admin");
     
    User user = new User();
    user.setUsername("user3");
    user.setPassword("123456");
    user.setFirstName("yared");
    user.setLastName("Gebeyaw");
    user.addRole(roleAdmin);       
     
    User savedUser = repo.save(user);
     
    assertThat(savedUser.getRoles().size()).isEqualTo(1);
}
@Test
public void testAddRoleToExistingUser() {
    User user = repo.findById(1).get();
    Role roleUser = roleRepo.findByName("yared");
    Role roleClient = new Role(3);
     
    user.addRole(roleUser);
    user.addRole(roleClient);
     
    User savedUser = repo.save(user);
     
    assertThat(savedUser.getRoles().size()).isEqualTo(2);      
}
}