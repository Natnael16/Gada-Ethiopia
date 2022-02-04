package com.gada.root;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.message.Message;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class DonationController {
    private final DonationRepository repo;
    private final PostsRepositary pRepo;
    private final UserRepository uRepo;


    @GetMapping("/fundraiser/{postId}/donate")
    public String donationForm(@AuthenticationPrincipal CustomUserDetails user, @PathVariable Long postId, Model model){
        Posts post = this.pRepo.findPostById(postId);
        if (user == null){
            model.addAttribute("user", "");
        }
        else{model.addAttribute("user", user.getUsername());}
        model.addAttribute("post", post);
        model.addAttribute("donation", new Donation());
        return "donationForm";
    }
    @PostMapping("/fundraiser/{postId}/donate")
    public String processDonation(@ModelAttribute("donation") @Valid Donation donation,  Errors errors, @AuthenticationPrincipal CustomUserDetails user, @PathVariable Long postId, Model model){
           Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username;
            if (principal instanceof CustomUserDetails) {
            username = ((CustomUserDetails)principal).getUsername();
            
            } else {
            username = principal.toString();
}

            User activUser = this.uRepo.findByUsername(username);
            if(errors.hasErrors()){
                Posts post = this.pRepo.findPostById(postId);
                model.addAttribute("post", post);
                return "donationForm";
            }

            
            LocalDateTime ldt = LocalDateTime.now();
            Posts post = this.pRepo.findPostById(postId);

            
            post.setTotalAmount(post.getTotalAmount() + donation.getAmount());
            
            if(donation.getDonatorName() == "" || donation.getDonatorName() == null){
                donation.setDonatorName("Anonymous");
            }
            else{ donation.setDonatorName(donation.getDonatorName());
                donation.setUser(activUser);}
            donation.setDate(ldt);
            donation.setPost(post);

            
            this.pRepo.save(post);
            this.repo.save(donation);
        return "thankYou";
    }
}

