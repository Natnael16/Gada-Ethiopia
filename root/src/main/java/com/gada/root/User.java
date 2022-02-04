package com.gada.root;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import java.util.Optional;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import lombok.Data;

@Entity
@Data
@Table(name ="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  user_id;
    @Column(nullable = false, length = 20)
    private String firstName;
    @Column(nullable = false, length = 20)
    private String lastName;
    @Column(unique = true, length =45, nullable =  false)
    private String username;
    @Column(nullable = false, length = 64)
    private String password;
    private String phoneNumber;
    private String email;
    @OneToMany(mappedBy = "user")
    Set<Posts> posts;
    @ManyToMany(fetch= FetchType.EAGER) 
        @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new  HashSet<>();

    public Set<Role> getRoles(){
        return roles;
    }
    public void setRoles(Set<Role> roles){
        this.roles = roles;
    }

 
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    
    public void addRole(Role role){
        this.roles.add(role);
    }
     

} 