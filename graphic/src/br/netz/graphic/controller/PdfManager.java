/*
	NETZ - Network management support system
    Copyright (C) 2011  Alana de Almeida Brand�o (alana.brandao@yahoo.com.br)
    					Frederico Ferreira Costa (fredericoferreira@live.com)

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package br.netz.graphic.controller;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.itextpdf.awt.DefaultFontMapper;
import org.jfree.chart.JFreeChart;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;


public class PdfManager {

	
	public void savePdf(JFreeChart grafico, int width, int height){
    	JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));  
		fileChooser.setSelectedFile(new File("Trafego.pdf"));
    	int returnVal = fileChooser.showSaveDialog(null);  
    	switch (returnVal) {
    	 case JFileChooser.APPROVE_OPTION:
    		String file = fileChooser.getSelectedFile()+"";
    		if(!file.contains(".pdf"))
    			file = file + ".pdf";
 	        generatePdf(grafico, width, height, file);
 	        break;
    	 case JFileChooser.CANCEL_OPTION:
    		 fileChooser.cancelSelection();
 	        break;
		}
	}
	
    public void generatePdf(JFreeChart grafico, int width, int height, String arquivo){
        PdfWriter pdfWriter = null;
        
        Document document = new Document(new Rectangle(width,height));
        
        try {
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(arquivo));
            document.open();
            
            PdfContentByte contentByte = pdfWriter.getDirectContent();
            PdfTemplate template = contentByte.createTemplate(width, height);
            Graphics2D graphics2D = template.createGraphics(width, height,
            		new DefaultFontMapper());
            Rectangle2D rectangle2D = new Rectangle2D.Double(0,0,width,height);
            
            grafico.draw(graphics2D, rectangle2D);
            
            graphics2D.dispose();
            contentByte.addTemplate(template, 0, 0);
        
        } catch (FileNotFoundException e) {
        	JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel salvar o gr�fico em PDF!", "Erro",
					JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } catch (DocumentException e) {
        	JOptionPane.showMessageDialog(null,
					"N�o foi poss�vel salvar o gr�fico em PDF!", "Erro",
					JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        document.close();
        JOptionPane.showMessageDialog(
				null,
				"O gr�fico foi salvo com com sucesso! ",
				"Gr�fico",
				JOptionPane.INFORMATION_MESSAGE);
    }
}
