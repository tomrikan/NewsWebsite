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
import wad.domain.Category;
import wad.domain.NewsItem;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;

/**
 *
 * @author Tomi
 */
@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository catRep;
    @Autowired
    private NewsRepository newsRep;

    @GetMapping("/management/categories")
    public String categories(Model model) {
        model.addAttribute("categories", catRep.findAll());
        model.addAttribute("news", newsRep.findAll());
        return "categories";
    }

    @GetMapping("/category/{categoryId}")
    public String category(Model model, @PathVariable Long categoryId) {
        Category category = catRep.getOne(categoryId);
        model.addAttribute("category", category);
        model.addAttribute("news", category.getNews());

        return "category";
    }

    @PostMapping("/management/categories")
    public String addCategory(@RequestParam String name) {

        if (!name.isEmpty() && name.length() > 2) {

            if (catRep.findByName(name) == null) {
                Category c = new Category();
                c.setName(name);

                catRep.save(c);
            }
        }
        return "redirect:/management/categories";
    }

    @PostMapping("/management/categories/{categoryId}")
    public String addNewsItemToCategory(@PathVariable Long categoryId, @RequestParam Long newsitemId) {
        Category c = catRep.getOne(categoryId);
        NewsItem ni = newsRep.getOne(newsitemId);

        c.getNews().add(ni);
        ni.getCategories().add(c);

        catRep.save(c);
        newsRep.save(ni);

        return "redirect:/management/categories";
    }

    @DeleteMapping("/management/categories/{categoryId}")
    public String remove(Model model, @PathVariable Long categoryId) {
        catRep.deleteById(categoryId);
        return "redirect:/management/categories";
    }
}
