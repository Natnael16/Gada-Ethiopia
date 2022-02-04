package com.gada.root;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class FundraiserController {
    @Autowired
    private PostsRepositary repo;
    @Autowired
    private DonationRepository dRepo;
    @Autowired
    private final CommentRepositary cRepo;
    private final UserRepository uRepo;

    @GetMapping("/fundraiser/{postId}")
    public String showFundraiser(@PathVariable Long postId, Model model) {
        Posts posts = this.repo.findPostById(postId);
        model.addAttribute("post", posts);
        model.addAttribute("post", posts);
        List<Donation> donations = this.dRepo.findDonationByPostId(posts);

        List<Donation> recentDonations = donations.size() < 5 ? donations : donations.subList(donations.size()-5, donations.size());
        Collections.reverse(recentDonations);
        
        model.addAttribute("donationAmount", donations.size());
        model.addAttribute("Donations", recentDonations);
        Comment c = new Comment();
        List<Comment> byPost = this.cRepo.findByPostId(postId);
        

        model.addAttribute("comment", c);
        // model.addAttribute("uuu",c.getUser().getUsername());
        model.addAttribute("comments", byPost);

        List<String> u = new ArrayList<>();
        for (Comment com : byPost) {
            // System.out.println(com.getUser().getUsername());
            // System.out.println("yeah");
            u.add(com.getUser().getUsername());

        }

        System.out.println(u);
        model.addAttribute("username", u);
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        model.addAttribute("curUser", username);

        return "fundraiser";
    }

    @PostMapping("/fundraiser/{postId}/c")
    public String postController(@ModelAttribute("c") Comment c, @PathVariable Long postId, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User myUser;
        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            myUser = this.uRepo.findByUsername(username);
        } else {
            String username = principal.toString();
            myUser = this.uRepo.findByUsername(username);
        }

        c.setUser(myUser);
        c.setPost(this.repo.findPostById(postId));
        this.cRepo.save(c);

        return "redirect:/fundraiser/{postId}#comments";
    }

    @PostMapping("/fundraiser/{postId}/delete/{cId}")
    public String deleteComment(@PathVariable("cId") Long cId) {
        
        this.cRepo.deleteById(cId);
        return "redirect:/fundraiser/{postId}#comments";

    }

    // @PostMapping("fundraiser/{postId}/edit")
    // public String editComment(@PathVariable("commentId") Long postId) {
    //     return "redirect:/fundraiser/{postId}#comments";
    // }

}
