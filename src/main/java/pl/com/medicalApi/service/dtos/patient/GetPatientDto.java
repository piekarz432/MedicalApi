package pl.com.medicalApi.service.dtos.patient;

public class GetPatientDto extends PatientDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
