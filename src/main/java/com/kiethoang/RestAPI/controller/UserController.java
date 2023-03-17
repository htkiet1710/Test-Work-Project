package com.kiethoang.RestAPI.controller;

import com.kiethoang.RestAPI.model.User;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {
    private List<User> users = new ArrayList<User>();

    @PostMapping("/user")
    public ResponseEntity<byte[]> create(@RequestParam MultipartFile file){
        try {
            XWPFDocument doc = new XWPFDocument(file.getInputStream());
            PdfOptions pdfOptions = PdfOptions.create();
            OutputStream out = new FileOutputStream(new File("E:/Test.pdf"));
            PdfConverter.getInstance().convert(doc, out, pdfOptions);
            ByteArrayOutputStream pdf = new ByteArrayOutputStream();
            PdfConverter.getInstance().convert(doc, pdf, pdfOptions);
            doc.write(pdf);
            doc.close();
            out.close();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "output.pdf";
            headers.setContentDispositionFormData(filename, filename);
            ResponseEntity<byte[]> response = new ResponseEntity<>(pdf.toByteArray(), headers, HttpStatus.OK);
            return response;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/users")
    public List<User> getAll() {
        return users;
    }

}
