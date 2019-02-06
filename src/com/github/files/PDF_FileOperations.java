package com.github.files;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.text.PDFTextStripperByArea;

/**
 * https://www.inetsoftware.de/products/pdf-content-comparer/compare-online
 * 
 * @author yashwanth.m
 *
 */
public class PDF_FileOperations {
	public static void main(String[] args) throws IOException {
		/*String s1 = getPDFasString( "D:/JarFiles/PDF1.pdf" );
		String s2 = getPDFasString( "D:/JarFiles/PDF2.pdf" );
		if( s1.equalsIgnoreCase(s2) ) {
			System.out.println("String contentes are equal.");
		} else {
			System.out.println("Not equal.");
		}*/
		
		
		String absoluteFileName = "D:/JarFiles/PDF1.pdf";
		PDDocument document = PDDocument.load(new File( absoluteFileName  ));
		int numberOfPages = document.getNumberOfPages();
		System.out.println("Number of Pages in PDF : "+ numberOfPages);
		
		/*List<PDSignatureField> signatureFields = document.getSignatureFields();
		System.out.println("SignatureFields ...");
		for (PDSignatureField pdSignatureField : signatureFields) {
			String valueAsString = pdSignatureField.getValueAsString();
			System.out.println( valueAsString );
		}
		
		List<PDSignature> signatureDictionaries = document.getSignatureDictionaries();
		System.out.println("SignatureDictionaries ..." );*/
		
		PDFTextStripperByArea stripper = new PDFTextStripperByArea();
		String text = stripper.getText(document);
		System.out.println("Text:" + text);
		
		document.close();
	}
	public static String getPDFasString(String absoluteFileName ) throws InvalidPasswordException, IOException {
		String text = "";
		PDDocument document = PDDocument.load(new File( absoluteFileName  ));
		int numberOfPages = document.getNumberOfPages();
		System.out.println("Number of Pages in PDF : "+ numberOfPages);
		if (!document.isEncrypted()) {
			PDFTextStripperByArea stripper = new PDFTextStripperByArea();
			text = stripper.getText(document);
			System.out.println("Text:" + text);
			System.out.println("===== End of File =====");
		}
		document.close();
		return text;
	}
}
