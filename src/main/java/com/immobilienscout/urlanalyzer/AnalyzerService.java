package com.immobilienscout.urlanalyzer;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.DocumentType;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class AnalyzerService {

    public Analysis processUrl(Url url) {
        Analysis analysis = new Analysis();
        try {
            Connection.Response response = Jsoup.connect(url.getLocation()).execute();
            Document doc = response.parse();
            analysis.setTitle(doc.title());
            analysis.setHeadingsCount(this.processHeadings(doc));
            analysis.setHtmlVersion(this.processHtmlVersion(doc));
            analysis.setLinksCount(this.processLinks(doc, url));
            analysis.setHasLogin(this.hasLoginForms(doc));
        } catch (HttpStatusException e) {
            analysis.setError(e.getStatusCode() + " " + e.getMessage());
        } catch (Exception e) {
            analysis.setError(e.getMessage());
        }
        return analysis;
    }

    private Map<String, Integer> processHeadings(Document doc) {
        Elements headings = doc.select("h1, h2, h3, h4, h5, h6");
        Map<String, Integer> headingsCount = new HashMap<>();

        headings.forEach(
                (h)->headingsCount.put(
                    h.tagName(),
                    incrementElementCount(headingsCount.get(h.tagName()))
                )
        );

        return headingsCount;
    }

    private Integer incrementElementCount(Integer count) {
        return null == count ? 1 : count + 1;
    }

    private String processHtmlVersion(Document doc) {
        Element html = doc.getElementsByTag("html").get(0);
        DocumentType documentType = (DocumentType) html.previousSibling();
        String htmlVersion = "-";

        if (null != documentType) {
            if (documentType.attr("publicId").toLowerCase().contains("html 4.01")) {
                htmlVersion = "HTML 4.01";
            } else if (documentType.attr("publicId").toLowerCase().contains("xhtml 1.0")) {
                htmlVersion = "XHTML 1.0";
            } else if (documentType.attr("publicId").toLowerCase().contains("xhtml 1.1")) {
                htmlVersion = "XHTML 1.1";
            } else if (documentType.toString().toLowerCase().contains("html")) {
                htmlVersion = "HTML 5";
            }
        }

        return htmlVersion;
    }

    private Map<String, Integer> processLinks(Document doc, Url url) {
        Map<String, Integer> linksCount = new HashMap<>();

        try {
            URL parsedUrl = new URL(url.getLocation());
            Elements links = doc.getElementsByTag("a");

            links.forEach((l)->{
                String urlToCheck;

                if(l.attr("href").contains(parsedUrl.getHost())) {
                    linksCount.put(
                            "internal links",
                            this.incrementElementCount(linksCount.get("internal links"))
                    );
                    urlToCheck = l.attr("href");
                } else if(l.attr("href").startsWith("/")) {
                    linksCount.put(
                            "internal links",
                            this.incrementElementCount(linksCount.get("internal links"))
                    );
                    urlToCheck = parsedUrl.getProtocol() + "://" + parsedUrl.getHost() + l.attr("href");
                } else {
                    linksCount.put(
                            "external links",
                            this.incrementElementCount(linksCount.get("external links"))
                    );
                    urlToCheck = l.attr("href");
                }

                if (null != Jsoup.parse(urlToCheck)) {
                    linksCount.put("working links", this.incrementElementCount(linksCount.get("working links")));
                } else {
                    linksCount.put("broken links", this.incrementElementCount(linksCount.get("broken links")));
                }
            });
        } catch (MalformedURLException e) {
            return linksCount;
        }

        return linksCount;
    }

    private Boolean hasLoginForms(Document doc) {
        return doc.select("input[type=password]").size() > 0;
    }
}
