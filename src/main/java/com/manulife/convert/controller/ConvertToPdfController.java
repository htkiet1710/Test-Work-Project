package com.manulife.convert.controller;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.*;
import java.nio.file.*;
import java.util.Date;

@RestController
public class ConvertToPdfController {

    @PostMapping("/convert-to-pdf")
    public ResponseEntity<byte[]> convertToPdf(@RequestParam MultipartFile file) {
        try {
            Date date = new Date();
            String value = String.valueOf(date.getTime());
            String filename = String.format("target/%s.pdf", value);
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            PdfOptions pdfOptions = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File(filename));
            PdfConverter.getInstance().convert(doc, out, pdfOptions);
            ByteArrayOutputStream pdf = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(doc, pdf, pdfOptions);
            doc.write(pdf);
            doc.close();
            out.close();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String outputFilename = "output.pdf";
            headers.setContentDispositionFormData(outputFilename, outputFilename);
            ResponseEntity<byte[]> response = new ResponseEntity<>(pdf.toByteArray(), headers, HttpStatus.OK);
            Files.deleteIfExists(Path.of(filename));
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
