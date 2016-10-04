package com.immobilienscout.urlanalyzer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class UrlAnalyzerController {

    @GetMapping("/")
    public String greeting(Model model) {
        model.addAttribute("url", new Url());
        return "index";
    }

    @PostMapping("/analysis")
    public String greetingSubmit(@ModelAttribute Url url, Model model) {
        Analysis analysis = new Analysis();

        try {
            Document doc = Jsoup.connect(url.getLocation()).get();
            Elements h1Tags = doc.getElementsByTag("h1");
            analysis.setTitle(doc.title());
            analysis.setHeadingOneCount(h1Tags.size());
        } catch (IOException e) {
            // TODO add error handling
            analysis.setTitle("error");
        }

        model.addAttribute("analysis", analysis);

        return "result";
    }
}
