package io.hari.demo.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hari.demo.entity.ContestUser;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;

/**
 * @Author hayadav
 * @create 4/24/2021
 */
public class ContestUserConverter implements AttributeConverter<ContestUser, String> {
    ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(ContestUser contestUser) {
        if (contestUser == null) return "";
        return objectMapper.writeValueAsString(contestUser);
    }

    @SneakyThrows
    @Override
    public ContestUser convertToEntityAttribute(String s) {
        if (s == null || s.length() == 0) return new ContestUser();
        return objectMapper.readValue(s, ContestUser.class);
    }
}
