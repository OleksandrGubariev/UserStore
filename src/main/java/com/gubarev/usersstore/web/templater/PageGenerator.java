package com.gubarev.usersstore.web.templater;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.Map;


public class PageGenerator {
    private static final String HTML_DIR = "/webapp";

    private static PageGenerator pageGenerator;
    private final Configuration cfg;

    public static PageGenerator instance() {
        if (pageGenerator == null)
            pageGenerator = new PageGenerator();
        return pageGenerator;
    }

    public String getPage(String filename, Map<String, Object> data) {
        Writer stream = new StringWriter();
        try {
            cfg.setClassForTemplateLoading(this.getClass(), HTML_DIR);
            Template template = cfg.getTemplate(filename);
            template.process(data, stream);
        } catch (IOException | TemplateException e) {
            throw new RuntimeException(e);
        }
        return stream.toString();
    }

    private PageGenerator() {
        cfg = new Configuration();
    }
}