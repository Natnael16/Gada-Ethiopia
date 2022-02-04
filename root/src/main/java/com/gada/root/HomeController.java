package com.gada.root;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
// import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
// import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostsRepositary postRepo;
    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")

    public String homePage(Model model) {
        List<Posts> posts = this.postRepo.findAll() ;
        // this.postRepo.findAll().forEach(i -> posts.add(i));

        model.addAttribute("titles", posts);

        return "home";
    }

    @GetMapping("/post")
    public String addPosts(Posts post) {
    
        return "addPosts";

    }

    @PostMapping("/post")
    public String proccesPosts(@Valid @ModelAttribute("posts") Posts post,
            Errors errors, Model model,
            @RequestParam("image") MultipartFile multipartFile)
            throws IOException {
       
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            if (fileName.contains("..")) {
                System.out.println("not valid file");
                return "addPosts";
            }
          
            LocalDateTime ldt = LocalDateTime.now();
            post.setDate(ldt);
            try {
                byte[] image = Base64.getEncoder().encode(multipartFile.getBytes());
                String result = new String(image);
                System.out.println(result);
                post.setProductImg(image);
                post.setBase64Img(result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (errors.hasErrors()) {
                log.info(errors.toString());
                return "addPosts";

            }
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User myUser;
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                myUser = this.userRepo.findByUsername(username);
            } else {
                String username = principal.toString();
                myUser = this.userRepo.findByUsername(username);
            }
            post.setUser(myUser);
            this.postRepo.save(post);

            return "redirect:/";
        

    }

}
