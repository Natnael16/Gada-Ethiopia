package com.gada.root;

import java.util.List;

import javax.persistence.OrderBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    
    @Autowired
    private PostsRepositary postRepo;
  
    public List<Posts> listAll(){
        return (List<Posts>) postRepo.findAll();
        
    }
    // public List<Posts> listById(Long id){
    //     return (List<Posts>) postRepo.findById(id).get();
        
    // }
    public Posts get(Long id) {
        return postRepo.findById(id).get();
    }
    public void save(Posts post) {
        postRepo.save(post);
    }

    public Iterable<Posts> search(String keyword){
        if (keyword != null) {
            return postRepo.SearchBykeyword(keyword);
        }

        return postRepo.findAll();
    }
    
}
