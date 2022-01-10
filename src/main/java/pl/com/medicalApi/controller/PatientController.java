package pl.com.medicalApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pl.com.medicalApi.service.dtos.patient.GetPatientDto;
import pl.com.medicalApi.service.dtos.patient.PostPatientDto;
import pl.com.medicalApi.service.patient.IPatientService;

@RestController
public class PatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @Operation(summary = "Create a new patient")
    @PostMapping("/britemed/patient")
    public GetPatientDto create(@RequestBody PostPatientDto postPatientDto) {
        return patientService.create(postPatientDto);
    }

    @Operation(summary = "Delete patient")
    @DeleteMapping("/britemed/patient/{id}")
    public GetPatientDto delete(@PathVariable Long id) {
        return patientService.delete(id);
    }

    @Operation(summary = "Get patient")
    @GetMapping("/britemed/patient/{personalIdentityNumber}")
    public GetPatientDto getByPersonalIdentityNumber(@PathVariable String personalIdentityNumber) {
        return patientService.getByPersonalIdentityNumber(personalIdentityNumber);
    }

    @Operation(summary = "Update patient")
    @PatchMapping("/britemed/patient/{id}")
    public GetPatientDto update(
            @PathVariable Long id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String surname) {
        return patientService.update(id, name, surname);
    }
}
