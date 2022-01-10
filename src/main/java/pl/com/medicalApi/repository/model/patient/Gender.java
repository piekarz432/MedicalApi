package pl.com.medicalApi.repository.model.patient;

public enum Gender {
    FEMALE('F'), MALE('M');

    private final Character code;

    Gender(Character code) {
        this.code = code;
    }

    public Character getCode() {
        return code;
    }
}
