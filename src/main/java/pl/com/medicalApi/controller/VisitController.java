package pl.com.medicalApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pl.com.medicalApi.service.dtos.visit.GetVisitDto;
import pl.com.medicalApi.service.dtos.visit.PostVisitDto;
import pl.com.medicalApi.service.visit.IVisitService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
public class VisitController {

    private final IVisitService visitService;

    public VisitController(IVisitService visitService) {
        this.visitService = visitService;
    }

    @Operation(summary = "Create a new visit")
    @PostMapping("/britemed/patient/{patientId}/visit")
    public GetVisitDto create(@PathVariable Long patientId, @RequestBody PostVisitDto postVisitDto) {
        return visitService.create(patientId, postVisitDto);
    }

    @Operation(summary = "Get all patient visits")
    @GetMapping("/britemed/patient/{patientId}/visit")
    public List<GetVisitDto> getAll(@PathVariable Long patientId) {
        return visitService.getAll(patientId);
    }

    @Operation(summary = "Get visit details")
    @GetMapping("/britemed/patient/{patientId}/visit/{visitId}")
    public GetVisitDto getDetails(@PathVariable Long patientId, @PathVariable Long visitId) {
        return visitService.getDetails(patientId, visitId);
    }

    @Operation(summary = "Update visit")
    @PatchMapping("/britemed/patient/{patientId}/visit/{visitId}")
    public GetVisitDto update(
            @PathVariable Long patientId,
            @PathVariable Long visitId,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time) {
        return visitService.update(patientId, visitId, date, time);
    }

    @Operation(summary = "Delete visit")
    @DeleteMapping("/britemed/patient/{patientId}/visit/{visitId}")
    public GetVisitDto cancel(@PathVariable Long patientId, @PathVariable Long visitId) {
        return visitService.cancel(patientId, visitId);
    }
}
