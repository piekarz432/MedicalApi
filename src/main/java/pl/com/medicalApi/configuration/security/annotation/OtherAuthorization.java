package pl.com.medicalApi.configuration.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@PreAuthorize("hasAnyRole('ROLE_RECEPTIONIST', 'ROLE_PSYCHIATRIST', 'ROLE_PSYCHOLOGIST')")
public @interface OtherAuthorization {
}
