package herve.pro.intergiciel.dosmed.CONVERTIR;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;

import java.io.IOException;
import java.util.List;

public abstract class JsonListConverter<T> implements AttributeConverter<List<T>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<List<T>> typeReference;

    protected JsonListConverter(TypeReference<List<T>> typeReference) {
        this.typeReference = typeReference;
    }

    @Override
    public String convertToDatabaseColumn(List<T> attribute) {
        if (attribute == null) return null;
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new IllegalArgumentException("Erreur de sérialisation JSON (liste)", e);
        }
    }

    @Override
    public List<T> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) return null;
        try {
            return objectMapper.readValue(dbData, typeReference);
        } catch (IOException e) {
            throw new IllegalArgumentException("Erreur de désérialisation JSON (liste)", e);
        }
    }
}
