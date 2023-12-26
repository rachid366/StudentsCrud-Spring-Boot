package com.rachid.Crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf-resources/";

    @Autowired
    private StudentService studentService;
    @Autowired
    private SpringTemplateEngine templateEngine;

    public File generatePdf() throws Exception {
        Context context = getContext();
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }

    private File renderPdf(String html) throws Exception {
        File file = File.createTempFile("students", ".pdf");
        try (OutputStream outputStream = new FileOutputStream(file)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
            renderer.layout();
            renderer.createPDF(outputStream);
        }
        file.deleteOnExit();
        return file;
    }

    private Context getContext() {
        Context context = new Context();
        context.setVariable("students", studentService.getAll());
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("pdfStudents", context);
    }
}
