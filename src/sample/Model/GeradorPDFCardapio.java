package sample.Model;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.UnitValue;

import java.io.IOException;
import java.util.List;

public class GeradorPDFCardapio {

    private Document abreDocumento(String arq) throws IOException{
        PdfWriter writer = new PdfWriter(arq);
        PdfDocument pdf  = new PdfDocument(writer);
        Document document = new Document(pdf);

        return  document;
    }

    public void gerarCardapio(String arq, List<Produto> produtos){

        try {
            java.util.List<Produto> lista = produtos;

            Document document = abreDocumento(arq);

            Paragraph paragrafo = new Paragraph("Cardápio");
            paragrafo.setTextAlignment(TextAlignment.CENTER);
            paragrafo.setBold();
            document.add(paragrafo);

            Table table = new Table(UnitValue.createPercentArray(new float[]{50, 30, 20}))
                    .useAllAvailableWidth();

            String[] cabecalho = {"Produto","Descrição","Preço"};
            for(String s:cabecalho){
                Cell cell = new Cell();
                cell.add(new Paragraph(s));
                cell.setBackgroundColor(ColorConstants.LIGHT_GRAY);
                cell.setBorder(new SolidBorder(ColorConstants.BLACK,2));
                table.addHeaderCell(cell);
            }

            PdfFont font = PdfFontFactory.createFont(StandardFonts.COURIER);
            table.setFont(font);
            table.setFontSize(12);

            for(Produto p:lista){
                table.addCell(p.getNomeProduto());
                table.addCell(p.getDescProduto());
                table.addCell(""+p.getValor());
            }

            document.add(table);
            document.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
