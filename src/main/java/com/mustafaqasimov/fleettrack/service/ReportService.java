package com.mustafaqasimov.fleettrack.service;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.mustafaqasimov.fleettrack.entity.Vehicle;
import com.mustafaqasimov.fleettrack.enums.VehicleStatus;
import com.mustafaqasimov.fleettrack.repository.MaintenanceRepository;
import com.mustafaqasimov.fleettrack.repository.VehicleRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private static final Font TITLE_FONT = new Font(Font.HELVETICA, Font.BOLD, 16);
    private static final Font HEADER_FONT = new Font(Font.HELVETICA, Font.BOLD, 12);
    private static final Font NORMAL_FONT = new Font(Font.HELVETICA,10);

    private final VehicleRepository vehicleRepository;
    private final MaintenanceRepository maintenanceRepository;

    public void generateFleetSummaryPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=fleet-summary.pdf");

        Document document = new Document(PageSize.A4,40,40,50,50);

        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();

            addTitle(document);
            addSummarySection(document);
            addMaintenanceDueSection(document);

        } catch (DocumentException e) {
            throw new IOException("Error generating PDF", e);
        }
    }

    private void addTitle(Document document) throws DocumentException {
        Paragraph title = new Paragraph("Fleet Report", TITLE_FONT);
        title.setSpacingAfter(4);
        document.add(title);

        Paragraph date = new Paragraph(
                "Generated on: " + java.time.LocalDate.now().format(DateTimeFormatter.ISO_DATE), NORMAL_FONT);
        date.setSpacingAfter(20);
        document.add(date);
    }

    private void addSummarySection(Document document) throws DocumentException {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        long availableVehicles = vehicles.stream().filter(vehicle -> vehicle.getStatus() == VehicleStatus.AVAILABLE).count();
        long inService = vehicles.stream().filter(vehicle -> vehicle.getStatus() == VehicleStatus.IN_SERVICE).count();
        long outOfService = vehicles.stream().filter(vehicle -> vehicle.getStatus() == VehicleStatus.OUT_OF_SERVICE).count();

        BigDecimal totalMaintenanceCost = maintenanceRepository.findAll().stream()
                .map(maintenance -> maintenance.getCost() != null ? maintenance.getCost() : BigDecimal.ZERO)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        document.add(new Paragraph("Summary", HEADER_FONT));

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(60);
        table.setSpacingAfter(20);
        table.setSpacingBefore(8);

        addRow(table, "Total vehicles", String.valueOf(vehicles.size()));
        addRow(table, "Available", String.valueOf(availableVehicles));
        addRow(table, "In service", String.valueOf(inService));
        addRow(table, "Out of service", String.valueOf(outOfService));
        addRow(table, "Total maintenance cost", totalMaintenanceCost + " AZN");
        document.add(table);
    }


    private void addMaintenanceDueSection(Document document) throws DocumentException {
        List<Vehicle> dueVehicles = vehicleRepository
                .findAllByNextServiceDueLessThanEqual(LocalDate.now());

        document.add(new Paragraph("Vehicles Due for Maintenance", HEADER_FONT));

        if (dueVehicles.isEmpty()) {
            document.add(new Paragraph("None - all vehicles are up to date.", NORMAL_FONT));
            return;
        }

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100);
        table.setSpacingBefore(8);

        addHeaderCell(table, "License Plate");
        addHeaderCell(table, "Make / Model");
        addHeaderCell(table, "Due Date");

        for (Vehicle v : dueVehicles) {
            table.addCell(new PdfPCell(new Phrase(v.getLicensePlate(), NORMAL_FONT)));
            table.addCell(new PdfPCell(new Phrase(v.getMake() + " " + v.getModel(), NORMAL_FONT)));
            table.addCell(new PdfPCell(new Phrase(String.valueOf(v.getNextServiceDue()), NORMAL_FONT)));
        }

        document.add(table);

        document.close();
    }

    private void addRow(PdfPTable table, String label, String value) {
        table.addCell(new PdfPCell(new Phrase(label, NORMAL_FONT)));
        table.addCell(new PdfPCell(new Phrase(value, NORMAL_FONT)));
    }

    private void addHeaderCell(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, HEADER_FONT));
        cell.setBackgroundColor(new Color(230, 230, 230));
        table.addCell(cell);
    }
}


