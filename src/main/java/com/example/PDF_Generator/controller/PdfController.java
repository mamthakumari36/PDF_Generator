package com.example.PDF_Generator.controller;

import java.io.File;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.PDF_Generator.service.PdfService;
import com.example.pdf.modal.Invoice;

@RestController
@RequestMapping(value = "/pdf")
public class PdfController {

	private final PdfService pdfService;

	public PdfController(PdfService pdfService) {
		this.pdfService = pdfService;
	}
	
	private final String pdfDirectory = "C:\\Users\\Lenovo\\Desktop\\Dev2\\";

	@PostMapping("/generate")
	public ResponseEntity<String> generatePdf(@RequestBody Invoice invoice) {
		try {
			String filePath = pdfService.generatePdf(invoice);
			return ResponseEntity.ok("PDF generated and stored at: " + filePath);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error generating PDF");
		}
	}

//	give the filename.pdf to download
	@GetMapping("/download/{filename}")
	public ResponseEntity<FileSystemResource> downloadPdf(@PathVariable String filename) {
		File file = new File(pdfDirectory, filename);

		if (!file.exists()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}

		FileSystemResource resource = new FileSystemResource(file);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
				.contentType(org.springframework.http.MediaType.APPLICATION_PDF).body(resource);
	}
}
