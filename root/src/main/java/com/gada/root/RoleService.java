package com.gada.root;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleService {
    
    @Autowired
    private UserRepository userRepository;
    private RoleRepository roleRepo;
 Iterable<Role> findAll(){
     return roleRepo.findAll();
 }
 public Optional<Role> findById(Integer id){
     return roleRepo.findById(id);
 }
 public void save(Role role){
     roleRepo.save(role);
 }
public void delete(Integer id){
    roleRepo.deleteById(id);
}
public void assignUserRole(Integer userId, Integer roleId){
    User user  = userRepository.findById(userId).orElse(null);
    Role role = roleRepo.findById(roleId).orElse(null);
   Set<Role> userRoles = user.getRoles();
   userRoles.add(role);
   user.setRoles(userRoles);
   userRepository.save(user);
}
public void unassignUserRole(Integer userId, Integer roleId){
    User user  = userRepository.findById(userId).orElse(null);
    user.getRoles().removeIf(x -> x.getId()==roleId);
    userRepository.save(user);
}
public Set<Role> getUserRoles(User user){
    return user.getRoles();
}
// public List<Role> getUserNotRoles(User user){
//     return roleRepo.getUserNotRoles(user.getUser_id());
//  }
}
