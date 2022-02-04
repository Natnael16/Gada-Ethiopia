package com.gada.root;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;

    public void saveUserWithDefaultRole(User user){

BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String encodedPassword = encoder.encode(user.getPassword());
user.setPassword(encodedPassword);

Role userRole = roleRepo.findByName("User");
user.addRole(userRole);
userRepo.save(user);

    }
    public List<User> listAll(){
        return userRepo.findAll();
        
    }
    public User get(Integer id) {
        return userRepo.findById(id).get();
    }
    public User getByUsername(String username){
        return userRepo.findByUsername(username);
    }
     
    public List<Role> listRoles() {
        return roleRepo.findAll();
    }
    public void save(User user) {
        userRepo.save(user);
    }
}
