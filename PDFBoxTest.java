import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;


public class PDFBoxTest {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new PDFBoxTest().encryptedPDF();
	}
	
	public void createPDF(){
		PDDocument document = new PDDocument();
		PDPage page = new PDPage();
		document.addPage(page);
		PDFont font = PDType1Font.HELVETICA_BOLD;
		try {
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.beginText();
			contentStream.setFont( font, 12 );
			contentStream.moveTextPositionByAmount( 100, 700 );
			contentStream.drawString( "Hello World" );
			contentStream.endText();
			contentStream.close();
						
			document.save("myPDF.pdf");
			document.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void encryptedPDF(){
		PDDocument doc = null;
		try {
			doc = PDDocument.load(new FileInputStream("myPDF.pdf"),"myPDF.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Define the length of the encryption key.
		// Possible values are 40, 128 or 256.
		int keyLength = 40;
		    
		AccessPermission ap = new AccessPermission();
		        
		// disable printing, everything else is allowed
		ap.setCanPrint(false);
		 
		String ownerPassword = "1234567890123456789012345678901234567890";
		String userPassword = "abcdefghijabcdefghijabcdefghijabcdefghij";
		// owner password (to open the file with all permissions) is "12345"
		// user password (to open the file but with restricted permissions, is empty here) 
		StandardProtectionPolicy spp = new StandardProtectionPolicy(ownerPassword, userPassword, ap);
		spp.setEncryptionKeyLength(keyLength);
		spp.setPermissions(ap);
		try {
			doc.protect(spp);
			doc.save("filename-encrypted.pdf");
			doc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
