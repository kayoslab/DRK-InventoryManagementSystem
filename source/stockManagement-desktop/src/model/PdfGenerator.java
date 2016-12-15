/*
 * Copyright (c) - All Rights Reserved
 *
 * Unauthorized copying of these files, via any medium is
 * strictly prohibited Proprietary and confidential
 *
 * NOTICE:
 * All information contained in this project is, and remains the property of the owner and its suppliers, if any.
 * The intellectual and technical concepts contained herein are proprietary to the owner and its suppliers and
 * are protected by trade secret or copyright law. Dissemination of this information or reproduction of this
 * material is strictly forbidden unless prior written permission is obtained by the owner.
 *
 * @author Amina
 *
 */

package model;

import model.databaseCommunication.DatabaseValueManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PdfGenerator {

	public void generatePDF() throws IOException {

		BufferedReader br = null;
		FileReader fr = null;
		int zahl = 700;
		
		try {

			System.out.println("Creating PDF");
			String file =  System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Inventory.pdf";

			PDDocument doc = new PDDocument();
			PDPage page = new PDPage();
			doc.addPage(page);

			File fontFile = new File ("./fonts/", "CourierCode-Roman.ttf");
			PDFont font = PDTrueTypeFont.loadTTF(doc, fontFile);
			PDPageContentStream content = new PDPageContentStream(doc, page);


			String pdfTitle = "Inventarliste";
			int pdfTitleFontSize = 26;
			content.beginText();
			content.setFont(font, pdfTitleFontSize);
			float titleWidth = font.getStringWidth(pdfTitle) / 1000 * pdfTitleFontSize;
			float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * pdfTitleFontSize;
			int marginTop = 20;

			content.moveTextPositionByAmount((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
			content.drawString(pdfTitle);

			content.endText();

		
			try{
				// Connection con = DriverManager.getConnection("");
				// Statement stmt = con.createStatement();
				DatabaseValueManager databaseValueManager = new DatabaseValueManager();
				// ResultSet rs = stmt.executeQuery("select `title`, `totalVolume`, `description` from `StockObject`");
				ResultSet rs = databaseValueManager.executeQuery("select `StockValue`.`batchNumber`, `StockValue`.`date` ,"
						+ "`StockObject`.`title`, `StockObject`.`description`, `StockObject`.`totalVolume`"
						+ "from `StockValue`, `StockObject` WHERE `StockObject`.`id` = `StockValue`.`stockObjectId`");
				while(rs.next()) {
					String title = rs.getString("title");
					String description = rs.getString("description");
					String totalVolume = rs.getString("totalVolume");
					String batchNumber = rs.getString("batchNumber");
					String date = rs.getString("date");
					
					String result = String.format("%-38s %-15s %-4s %-12s %-10s",title, batchNumber, totalVolume, date, description);

					content.beginText();
					content.setFont(font, 10);
					content.moveTextPositionByAmount(80, zahl);
					content.drawString(result);
					content.endText();
					zahl = zahl - 30;
					if (zahl < 70) {
						content.close();

						page = new PDPage();
						doc.addPage(page);

						content = new PDPageContentStream(doc, page);
						zahl = 700;
					}
				}
				
			}catch (SQLException e) {
				System.err.println("Fehler: " + e.getMessage());
			}		

			
			content.close();
			doc.save(file);
			doc.close();

		} catch (IOException e) {

			e.printStackTrace();

		} finally {
			try {
				if (br != null)
					br.close();

				if (fr != null)
					fr.close();

			} catch (IOException ex) {

				ex.printStackTrace();
			}
		}
	}
}