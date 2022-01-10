package pl.com.medicalApi.utils;

import pl.com.medicalApi.repository.model.Status;
import pl.com.medicalApi.repository.model.visit.Payment;
import pl.com.medicalApi.service.dtos.visit.PostVisitDto;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitTestUtils {

    public static final String visitPath = "/visit";

    public static PostVisitDto createValidPostVisitDto() {
        PostVisitDto postVisitDto = new PostVisitDto();
        postVisitDto.setDate(LocalDate.of(2022, 9, 9));
        postVisitDto.setTime(LocalTime.of(15, 0));
        postVisitDto.setPayment(Payment.UNPAID);
        postVisitDto.setStatus(Status.ACTIVE);
        postVisitDto.setMedicalProcedure(20L);

        return postVisitDto;
    }
}
