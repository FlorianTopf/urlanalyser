package com.immobilienscout.urlanalyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UrlAnalyzerController {

    @Autowired
    private AnalyzerService analyzerService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("url", new Url());
        return "index";
    }

    @PostMapping("/analysis")
    public String urlSubmit(@ModelAttribute Url url, Model model) {
        Analysis analysis = analyzerService.processUrl(url);
        model.addAttribute("analysis", analysis);
        return "result";
    }
}
