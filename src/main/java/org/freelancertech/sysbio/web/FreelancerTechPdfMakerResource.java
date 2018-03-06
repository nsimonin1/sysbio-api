package org.freelancertech.sysbio.web;

import org.freelancertech.sysbio.utils.DocxPdfUtils;
import org.freelancertech.sysbio.utils.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * Created by simon on 01/03/2018.
 */
@RestController
@RequestMapping("/api")
public class FreelancerTechPdfMakerResource {
    private static final Logger LOGGER= LoggerFactory.getLogger(FreelancerTechPdfMakerResource.class);

    /**
     *
     * @param file
     * @return
     */
    @PostMapping("/upload-docx-pdf")
    public ResponseEntity<PdfData> makePdf(@RequestParam("file") MultipartFile file){
        try {
            long timeStart=System.currentTimeMillis();
            byte[] flux= DocxPdfUtils.ConvertToPDF(file.getBytes());
            long delta=(System.currentTimeMillis()-timeStart)/1000;
            LOGGER.info("Temps: {} seconds",delta);
            return ResponseEntity.ok(new PdfData(flux,"N/A"));
        } catch (IOException ex) {
            LOGGER.error(ex.getLocalizedMessage(),ex);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "Maker","ER0001",ex.getMessage()
            )).build();
        }
    }

    /**
     *
     * @param docx
     * @return
     */
    @PostMapping("/docx-pdf")
    public ResponseEntity<PdfData> makePdf(@RequestBody @Valid DocxData docx){
        try {
            long timeStart=System.currentTimeMillis();
            byte[] flux= DocxPdfUtils.ConvertToPDF(docx.getFlux());
            long delta=(System.currentTimeMillis()-timeStart)/1000;
            LOGGER.info("Temps: {} seconds",delta);
            return ResponseEntity.ok(new PdfData(flux,"N/A"));
        } catch (IOException ex) {
            LOGGER.error(ex.getLocalizedMessage(),ex);
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(
                    "Maker","ER0001",ex.getMessage()
            )).build();
        }
    }

    private class DocxData {
        @NotNull
        private byte[] flux;
        private String hashCode;

        public byte[] getFlux() {
            return flux;
        }

        public void setFlux(byte[] flux) {
            this.flux = flux;
        }

        public String getHashCode() {
            return hashCode;
        }

        public void setHashCode(String hashCode) {
            this.hashCode = hashCode;
        }
    }

    private class PdfData {
        @NotNull
        private final byte[] flux;
        private final String hashCode;

        public PdfData(byte[] flux, String hashCode) {
            this.flux = flux;
            this.hashCode = hashCode;
        }

        public byte[] getFlux() {
            return flux;
        }

        public String getHashCode() {
            return hashCode;
        }
    }
}
