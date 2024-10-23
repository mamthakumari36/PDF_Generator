package com.example.PDF_Generator.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.pdf.modal.Invoice;
import com.lowagie.text.Document;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.html.simpleparser.StyleSheet;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class PdfService {
	private final TemplateEngine templateEngine;

	public PdfService(TemplateEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	public String generatePdf(Invoice invoice) throws Exception {

		Context context = new Context();
		context.setVariable("invoice", invoice);

		String htmlContent = templateEngine.process("invoice", context);

		String fileName = invoice.getSellerGstin() + "-" + invoice.getBuyerGstin() + ".pdf";
		File pdfFile = new File("C:\\Users\\Lenovo\\Desktop\\Dev2\\" + fileName);

		try (OutputStream outputStream = new FileOutputStream(pdfFile)) {
			Document document = new Document();
			PdfWriter.getInstance(document, outputStream);

			document.open();

			ByteArrayInputStream htmlStream = new ByteArrayInputStream(htmlContent.getBytes());
			convertHtmlToPdf(htmlStream, document);

			document.close();
		}

		return pdfFile.getAbsolutePath();
	}

	private void convertHtmlToPdf(ByteArrayInputStream htmlStream, Document document) {
		try {
			StyleSheet styles = new StyleSheet();
			HTMLWorker worker = new HTMLWorker(document);

			worker.parse(new InputStreamReader(htmlStream, StandardCharsets.UTF_8));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
