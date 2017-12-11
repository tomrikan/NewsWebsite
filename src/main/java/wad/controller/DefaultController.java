/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;

/**
 *
 * @author Tomi
 */
@Controller
public class DefaultController {
    
    @Autowired
    private NewsRepository newsRep;
    @Autowired
    private CategoryRepository catRep;

    @GetMapping("/")
    public String home(Model model) {
        
        model.addAttribute("categories", catRep.findAll());

        PageRequest pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "pubTime");
        model.addAttribute("news", newsRep.findAll(pageable));

        return "index";
    }
    
    @GetMapping(path = "/pic", produces = "image/*")
    @ResponseBody
    public byte[] showPic(@PathVariable Long newsId) {
        return newsRep.getOne(newsId).getPic();
    }
    
    @GetMapping("/mostviewed")
    public String mostViewed(Model model) {
        PageRequest pageable = PageRequest.of(0, 10, Sort.Direction.DESC, "viewCount");
        model.addAttribute("news", newsRep.findAll(pageable));
        
        return "mostviewed";
    }
    
    @GetMapping("/pubtime")
    public String pubTime(Model model) {
        PageRequest pageable = PageRequest.of(0, 100, Sort.Direction.DESC, "pubTime");
        model.addAttribute("news", newsRep.findAll(pageable));
        
        return "pubtime";
    }
    
    @GetMapping("/management")
    public String management() {
        return "management";
    }
    
}
