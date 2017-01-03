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
import model.databaseObjects.environment.Location;
import model.databaseObjects.stockObjects.StockObject;
import model.databaseObjects.stockValues.StockObjectValue;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;

import java.io.IOException;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;

public class PdfGenerator {
	public void generatePDF(StockObjectValue[] stockObjectValues, String url) throws IOException {
		BufferedReader br = null;
		FileReader fr = null;
		int zahl = 700;

		try {
			// System.out.println("Creating PDF");
			// String file =  System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Inventory.pdf";

			PDPage page = new PDPage();
			PDDocument doc = new PDDocument();
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


			for (StockObjectValue stockObjectValue : stockObjectValues) {
				StockObject stockObject = DatabaseReadManager.getStockObject(stockObjectValue.stockObjectID);
				Location location = DatabaseReadManager.getLocation(stockObjectValue.locationID);
				if (stockObject != null && location != null) {
					String title = stockObject.title;
					String locationTitle = location.title;
					String volume = "" + stockObjectValue.volume;
					String result = String.format("%1$-50s %2$-20s %3$5s",title, locationTitle, volume);
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
			}
			content.close();
			doc.save(url);
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







//	public void generatePDF(StockObjectValue[] stockObjectValues, String url) throws IOException {
//		BufferedReader br = null;
//		FileReader fr = null;
//		int zahl = 700;
//
//		try {
//			// System.out.println("Creating PDF");
//			// String file =  System.getProperty("user.home") + File.separator + "Desktop" + File.separator + "Inventory.pdf";
//
//			PDPage page = new PDPage();
//			PDDocument doc = new PDDocument();
//			doc.addPage(page);
//
//			File fontFile = new File ("./fonts/", "CourierCode-Roman.ttf");
//			PDFont font = PDTrueTypeFont.loadTTF(doc, fontFile);
//			PDPageContentStream content = new PDPageContentStream(doc, page);
//
//			String pdfTitle = "Inventarliste";
//			int pdfTitleFontSize = 26;
//			content.beginText();
//			content.setFont(font, pdfTitleFontSize);
//			float titleWidth = font.getStringWidth(pdfTitle) / 1000 * pdfTitleFontSize;
//			float titleHeight = font.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * pdfTitleFontSize;
//			int marginTop = 20;
//			content.moveTextPositionByAmount((page.getMediaBox().getWidth() - titleWidth) / 2, page.getMediaBox().getHeight() - marginTop - titleHeight);
//			content.drawString(pdfTitle);
//			content.endText();
//			content.setFont(font, 10);
//
//			String[][] data = new String[stockObjectValues.length][3];
//			int counter = 0;
//			for (StockObjectValue stockObjectValue : stockObjectValues) {
//				StockObject stockObject = DatabaseReadManager.getStockObject(stockObjectValue.stockObjectID);
//				Location location = DatabaseReadManager.getLocation(stockObjectValue.locationID);
//				if (stockObject != null && location != null) {
//					String title = stockObject.title;
//					String locationTitle = location.title;
//					String volume = "" + stockObjectValue.volume;
//					data[counter][0] = title;
//					data[counter][1] = locationTitle;
//					data[counter][2] = volume;
//					counter++;
//				}
//			}
//
//			drawTable(page, content, 700, 100, data);
//
//			content.close();
//			doc.save(url);
//			doc.close();
//
//		} catch (IOException e) {
//
//			e.printStackTrace();
//
//		} finally {
//			try {
//				if (br != null)
//					br.close();
//
//				if (fr != null)
//					fr.close();
//
//			} catch (IOException ex) {
//
//				ex.printStackTrace();
//			}
//		}
//	}
//
//	public static void drawTable(PDPage page, PDPageContentStream contentStream, float y, float margin, String[][] content) throws IOException {
//		final int rows = content.length;
//		final int cols = content[0].length;
//		final float rowHeight = 20f;
//		final float tableWidth = page.getMediaBox().getWidth() - margin - margin;
//		final float tableHeight = rowHeight * rows;
//		final float colWidth = tableWidth/(float)cols;
//		final float cellMargin=5f;
//
//		//draw the rows
//		float nexty = y ;
//		for (int i = 0; i <= rows; i++) {
//			contentStream.drawLine(margin, nexty, margin+tableWidth, nexty);
//			nexty-= rowHeight;
//		}
//
//		//draw the columns
//		float nextx = margin;
//		for (int i = 0; i <= cols; i++) {
//			contentStream.drawLine(nextx, y, nextx, y-tableHeight);
//			nextx += colWidth;
//		}
//		float textx = margin+cellMargin;
//		float texty = y-15;
//		for(int i = 0; i < content.length; i++){
//			for(int j = 0 ; j < content[i].length; j++){
//				String text = content[i][j];
//				contentStream.beginText();
//				contentStream.moveTextPositionByAmount(textx,texty);
//				contentStream.drawString(text);
//				contentStream.endText();
//				textx += colWidth;
//			}
//			texty-=rowHeight;
//			textx = margin+cellMargin;
//		}
//	}
