/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import wad.domain.NewsItem;
import wad.repository.CategoryRepository;
import wad.repository.NewsRepository;
import wad.repository.WriterRepository;

/**
 *
 * @author Tomi
 */
@Controller
public class NewsController {

    @Autowired
    private NewsRepository newsRep;
    @Autowired
    private CategoryRepository catRep;
    @Autowired
    private WriterRepository writerRep;

    @GetMapping("/management/news")
    public String news(Model model) {
        model.addAttribute("news", newsRep.findAll());
        model.addAttribute("writers", writerRep.findAll());
        model.addAttribute("categories", catRep.findAll());
        return "news";
    }

    @PostMapping("/management/news")
    public String addNewsItem(@RequestParam String headline, String lead, String text) {

        if (!headline.isEmpty() && headline.length() > 3
                && !lead.isEmpty() && !text.isEmpty()) {
            NewsItem ni = new NewsItem();
            ni.setHeadline(headline);
            ni.setLead(lead);
            ni.setText(text);

            newsRep.save(ni);
        }

        return "redirect:/management/news";
    }

    @GetMapping("/news/{newsId}")
    public String newsItem(Model model, @PathVariable Long newsId) {
        NewsItem ni = newsRep.getOne(newsId);
        int tmp = ni.getViewCount();
        ni.setViewCount(tmp + 1);
        newsRep.save(ni);

        model.addAttribute("newsitem", newsRep.getOne(newsId));
        model.addAttribute("writers", newsRep.getOne(newsId).getWriters());
        return "newsitem";
    }

    @GetMapping(path = "/news/{newsId}/pic", produces = "image/*")
    @ResponseBody
    public byte[] showPic(@PathVariable Long newsId) {
        return newsRep.getOne(newsId).getPic();
    }

    @PostMapping("/management/news/{newsId}")
    public String savePic(@PathVariable Long newsId, @RequestParam("file") MultipartFile file) throws IOException {

        if (file != null) {

            NewsItem ni = newsRep.getOne(newsId);
            ni.setPic(file.getBytes());

            newsRep.save(ni);
        }

        return "redirect:/management/news";
    }

    @DeleteMapping("/management/news/{newsId}")
    public String remove(Model model, @PathVariable Long newsId) {
        newsRep.deleteById(newsId);
        return "redirect:/management/news";
    }

}
