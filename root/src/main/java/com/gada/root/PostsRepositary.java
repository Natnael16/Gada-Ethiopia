package com.gada.root;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface PostsRepositary extends CrudRepository<Posts, Long>  {
     
     public Posts findPostById(Long postId);
     public List<Posts> findPostByTitle(String title);

     @Query("SELECT p FROM Posts p WHERE p.title LIKE %:key%"
            + " OR p.description LIKE %:key%")
     public List<Posts> SearchBykeyword(@Param("key") String key);

     public List<Posts> findAll();
     public List<Posts> findByUser(User user);

}