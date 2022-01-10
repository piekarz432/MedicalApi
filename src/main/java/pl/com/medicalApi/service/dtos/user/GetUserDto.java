package pl.com.medicalApi.service.dtos.user;

public class GetUserDto extends UserDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
