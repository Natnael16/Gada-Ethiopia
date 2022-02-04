package com.gada.root;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "roles")
public class Role {
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "roles_id")
    private Integer id;
    @Column(nullable = false, length = 45)
    private String name;

    public Role(String name){
        this.name = name;
    }
    public Role(Integer id){
        this.id = id;
    }
    public String toString(){
        return this.name;
    }
    
}
