/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wad.domain.NewsItem;
import wad.domain.Writer;
import wad.repository.NewsRepository;
import wad.repository.WriterRepository;

/**
 *
 * @author Tomi
 */
@Controller
public class WriterController {
    
    @Autowired
    private WriterRepository writerRep;
    @Autowired
    private NewsRepository newsRep;
    
    @GetMapping("/management/writers")
    public String writers(Model model) {
        model.addAttribute("writers", writerRep.findAll());
        model.addAttribute("news", newsRep.findAll());
        return "writers";
    }
    
    @PostMapping("/management/writers")
    public String addWriter(@RequestParam String name) {
        if (writerRep.findByName(name) == null) {
            Writer w = new Writer();
            w.setName(name);

            writerRep.save(w);
        }
        return "redirect:/management/writers";
    }
    
    @PostMapping("/management/writers/{writerId}")
    public String addNews(@PathVariable Long writerId, @RequestParam Long newsitemId) {
        Writer w = writerRep.getOne(writerId);
        NewsItem ni = newsRep.getOne(newsitemId);
        
        w.getWrittenNews().add(ni);
        ni.getWriters().add(w);
        
        writerRep.save(w);
        newsRep.save(ni);
        
        return "redirect:/management/writers";
    }
    
    @DeleteMapping("/management/writers/{writerId}")
    public String remove(Model model, @PathVariable Long writerId) {
        writerRep.deleteById(writerId);
        return "redirect:/management/writers";
    }
}
