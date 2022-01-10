package pl.com.medicalApi.configuration.security;

public enum Role {

    ADMIN("ROLE_ADMIN"),
    RECEPTIONIST("ROLE_RECEPTIONIST"),
    PSYCHIATRIST("ROLE_PSYCHIATRIST"),
    PSYCHOLOGIST("ROLE_PSYCHOLOGIST");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
