package pl.com.britenet.javastepone.service.visit;

import pl.com.britenet.javastepone.service.dtos.visit.GetVisitDto;
import pl.com.britenet.javastepone.service.dtos.visit.PostVisitDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface IVisitService {
    GetVisitDto create(Long patientId, PostVisitDto postVisitDto);

    List<GetVisitDto> getAll(Long patientId);

    GetVisitDto getDetails(Long patientId, Long visitId);

    GetVisitDto cancel(Long patientId, Long visitId);

    GetVisitDto update(Long patientId, Long visitId, LocalDate date, LocalTime time);
}
