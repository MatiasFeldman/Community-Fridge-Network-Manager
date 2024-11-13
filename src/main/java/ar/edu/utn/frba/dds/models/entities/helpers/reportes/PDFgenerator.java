package ar.edu.utn.frba.dds.models.entities.helpers.reportes;
import ar.edu.utn.frba.dds.models.entities.reportes.Reporte;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteFallas;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteMovimientoViandas;
import ar.edu.utn.frba.dds.models.entities.reportes.ReporteViandasDonadas;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class PDFgenerator implements GeneradorPDF {
    @Override
    public void guardarPdfEnPath(List<Reporte> reportes, String path) {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime semanaAtras = dateTime.minusWeeks(1);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Reporte reporte : reportes) {
            String fechaActual = dateTime.format(dateFormat);
            String fechaInicioPeriodo = semanaAtras.format(dateFormat);
            String nombrePDF = path + "/" + reporte.nombre().replaceAll(" ", "_") + ".pdf";

            int nroColumnas = reporte.getNroColumnas();

            Document document = new Document();
            try {
                PdfWriter.getInstance(document, new FileOutputStream(nombrePDF));
                document.open();

                // Estilos para el título y contenido
                Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
                Font fontContenido = FontFactory.getFont(FontFactory.HELVETICA, 12);

                // Título del reporte
                Paragraph titulo = new Paragraph("Reporte Semanal - " + reporte.nombre(), fontTitulo);
                titulo.setAlignment(Element.ALIGN_CENTER);
                document.add(titulo);

                // Fecha del reporte en un nuevo párrafo
                Paragraph fechas = new Paragraph(fechaInicioPeriodo + " - " + fechaActual, fontContenido);
                fechas.setAlignment(Element.ALIGN_CENTER);
                document.add(fechas);
                document.add(new Paragraph("\n")); // Espacio en blanco

                // Crear tabla
                PdfPTable tabla = new PdfPTable(nroColumnas);
                tabla.setWidthPercentage(100);
                tabla.setSpacingBefore(10f);

                // Encabezados de la tabla
                String[] headers = this.devolverHeaders(reporte);
                for (int i = 0; i < nroColumnas; i++) {
                    PdfPCell headerCell = new PdfPCell(new Phrase(headers[i], fontContenido));
                    headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    tabla.addCell(headerCell);
                }

                // Datos del reporte en la tabla
                String[] lineas = reporte.contenido().split("\n");
                for (int j = 2; j < lineas.length; j++) { // Saltar encabezados
                    String[] datos = lineas[j].split("\\t+"); // Dividir por tabulación
                    for (int k = 0; k < nroColumnas && k < datos.length; k++) {
                        PdfPCell dataCell = new PdfPCell(new Phrase(datos[k], fontContenido));
                        dataCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        tabla.addCell(dataCell);
                    }
                }

                document.add(tabla);
                document.close();

            } catch (DocumentException | FileNotFoundException e) {
                throw new RuntimeException("Error al generar el PDF para " + reporte.nombre(), e);
            }
        }
    }

    private String[] devolverHeaders(Reporte reporte) {
        if (reporte instanceof ReporteFallas){
            return new String[]{"Heladera", "Cantidad de fallas"};
        } else if (reporte instanceof ReporteMovimientoViandas) {
            return new String[]{"Heladera", "Viandas Colocadas", "Viandas Retiradas"};
        } else if (reporte instanceof ReporteViandasDonadas){
            return new String[]{"Nombre", "Cantidad de viandas donadas"};
        } else {
            throw new RuntimeException("Tipo de reporte no soportado");
        }
    }

    @Override
    public ByteArrayOutputStream generarPdfParaEnviar(List<Reporte> reportes) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timeStamp = dateTime.format(dateFormat);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();

            for (int i = 0; i < reportes.size(); i++) {
                Reporte reporte = reportes.get(i);
                document.add(new Paragraph("Reporte Semanal - " + reporte.nombre()));
                document.add(new Paragraph("Fecha: " + timeStamp));
                document.add(new Paragraph(reporte.contenido()));

                if (i < reportes.size() - 1) {
                    document.newPage();
                }
            }

            document.close();
        } catch (DocumentException e) {
            throw new RuntimeException("Error al generar el PDF", e);
        }

        return byteArrayOutputStream;
    }

}

