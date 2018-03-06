package org.freelancertech.sysbio.utils;

import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;

import java.io.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
/**
 * Created by simon on 01/03/2018.
 */
public class DocxPdfUtils {
    private DocxPdfUtils() {
    }

    public static byte[] ConvertToPDF(byte[] fluxDocx) throws IOException {
        //rg.apache.poi.POIXMLTypeLoader loader;
        try(InputStream doc = new ByteArrayInputStream(fluxDocx);
            ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            XWPFDocument document = new XWPFDocument(doc);
            PdfOptions options = PdfOptions.create();
            PdfConverter.getInstance().convert(document, out, options);
            return out.toByteArray();
        }
    }
}
