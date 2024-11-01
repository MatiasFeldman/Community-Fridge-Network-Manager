package ar.edu.utn.frba.dds.models.entities.helpers.reportes;
import ar.edu.utn.frba.dds.models.entities.reportes.Reporte;
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
/*
    @Override
    public void guardarPdfEnPath(List<Reporte> reportes, String path) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String timeStamp = dateTime.format(dateFormat);

        String nombrePDF = path + "/reporte_semanal_" + timeStamp + ".pdf";

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(nombrePDF));
            document.open();

            // estilos de fuente
            Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font fontContenido = FontFactory.getFont(FontFactory.HELVETICA, 12);

            for (int i = 0; i < reportes.size(); i++) {
                Reporte reporte = reportes.get(i);

                // Añadir contenido de cada reporte a una nueva página
                document.add(new Paragraph("Reporte Semanal" + " - " + reporte.nombre()));
                document.add(new Paragraph("Fecha: " + timeStamp));
                document.add(new Paragraph(reporte.contenido()));

                if (i < reportes.size() - 1) {
                    document.newPage();
                }
            }

            System.out.println("PDF generado: " + nombrePDF);
            document.close();

        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    } */

    @Override
    public void guardarPdfEnPath(List<Reporte> reportes, String path) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Reporte reporte : reportes) {
            String timeStamp = dateTime.format(dateFormat);
            String nombrePDF = path + "/" + reporte.nombre().replaceAll(" ", "_") + "_" + dateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".pdf";

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
                Paragraph fecha = new Paragraph("Fecha: " + timeStamp, fontContenido);
                fecha.setAlignment(Element.ALIGN_CENTER);
                document.add(fecha);
                document.add(new Paragraph("\n")); // Espacio en blanco

                // Crear tabla
                PdfPTable tabla = new PdfPTable(2); // Dos columnas: nombre y valor
                tabla.setWidthPercentage(100);
                tabla.setSpacingBefore(10f);

                // Encabezados de la tabla
                PdfPCell celdaEncabezado1 = new PdfPCell(new Phrase("Heladera", fontContenido));
                PdfPCell celdaEncabezado2 = new PdfPCell(new Phrase("Cantidad de fallas", fontContenido));
                celdaEncabezado1.setHorizontalAlignment(Element.ALIGN_CENTER);
                celdaEncabezado2.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(celdaEncabezado1);
                tabla.addCell(celdaEncabezado2);

                // Datos del reporte en la tabla
                String[] lineas = reporte.contenido().split("\n");
                for (int j = 2; j < lineas.length; j++) { // Saltar encabezados del contenido
                    String[] datos = lineas[j].split("\t");
                    if (datos.length >= 2) { // Asegurarse de que haya al menos dos columnas de datos
                        tabla.addCell(new PdfPCell(new Phrase(datos[0], fontContenido)));
                        tabla.addCell(new PdfPCell(new Phrase(datos[1], fontContenido)));
                    }
                }

                document.add(tabla);

                System.out.println("PDF generado: " + nombrePDF);
                document.close();

            } catch (DocumentException | FileNotFoundException e) {
                throw new RuntimeException("Error al generar el PDF para " + reporte.nombre(), e);
            }
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

