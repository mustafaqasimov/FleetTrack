package com.mustafaqasimov.fleettrack.controller;

import com.mustafaqasimov.fleettrack.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Reports",description = "Endpoints for generating various reports")
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "Download a fleet summary report as PDF",
            description = "Generates and downloads a PDF report containing a summary of the fleet's performance")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "PDF report generated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/fleet-summary/pdf")
    public void fleetSummaryPdf(HttpServletResponse response) throws IOException {
        reportService.generateFleetSummaryPdf(response);
    }

}
