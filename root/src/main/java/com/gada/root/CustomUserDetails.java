package com.gada.root;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails  implements UserDetails{

    
    private User user;
    public CustomUserDetails(User user){
        this.user = user;

    }
    private Role role;
    public CustomUserDetails(Role role){
        this.role = role;

    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      Set<Role> roles = user.getRoles();
      List<SimpleGrantedAuthority> authorities = new ArrayList<>();
      
      for (Role role : roles){
          authorities.add(new SimpleGrantedAuthority(role.getName()));
      }
        return authorities;
    }

    @Override
    public String getPassword() {
        
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {
        
        return true;
    }
    
    public String getFulllName(){
        return user.getFirstName() + " " + user.getLastName();
    }
    public void SetFirstName(String firstName){
        this.user.setFirstName(firstName);
    }
    public void SetLastName(String lastName){
        this.user.setLastName(lastName);
    }
    // public boolean hasRole(String roleName){
    //     return user.hasRole(roleName);
    // }
    // public void setRole(Role userRole) {
    //     this.user.setRoles(userRole);
        
    // }
}
