package pl.com.medicalApi.repository.model.patient;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
class GenderConverter implements AttributeConverter<Gender, Character> {

    @Override
    public Character convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            throw new IllegalArgumentException("Gander can't be a null");
        }

        return gender.getCode();
    }

    @Override
    public Gender convertToEntityAttribute(Character s) {
        if (s == null) {
            throw new IllegalStateException("Gander can't be a null");
        }

        return Stream.of(Gender.values())
                .filter(g -> g.getCode().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
