package com.kiethoang.RestAPI;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

import java.io.*;

@SpringBootApplication
public class RestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiApplication.class, args);

		try {
			InputStream docFile = new FileInputStream(new File("E:/Test.docx"));
			XWPFDocument doc = new XWPFDocument(docFile);
			PdfOptions pdfOptions = PdfOptions.create();
			OutputStream out = new FileOutputStream(new File("E:/Test.pdf"));
			PdfConverter.getInstance().convert(doc, out, pdfOptions);
			doc.close();
			out.close();
			System.out.println("Done");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
