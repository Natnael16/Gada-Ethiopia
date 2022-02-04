package com.gada.root;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final PostService pService;
    @GetMapping("/Search")
    public String searchResult(Model model, @RequestParam("q") String q) {
        List<Posts> listResult = (List<Posts>) pService.search(q);

        model.addAttribute("post", listResult);
        model.addAttribute("q", q);
         
        return "searchResult";
    }
    
}
