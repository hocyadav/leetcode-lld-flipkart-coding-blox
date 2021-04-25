package io.hari.demo.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hari.demo.entity.UserContest;
import lombok.SneakyThrows;

import javax.persistence.AttributeConverter;

/**
 * @Author hayadav
 * @create 4/24/2021
 */
public class UserContestConverter implements AttributeConverter<UserContest, String> {
    ObjectMapper objectMapper = new ObjectMapper();
    @SneakyThrows
    @Override
    public String convertToDatabaseColumn(UserContest userContest) {
        if (userContest == null ) return "";
        return objectMapper.writeValueAsString(userContest);
    }

    @SneakyThrows
    @Override
    public UserContest convertToEntityAttribute(String s) {
        if (s == null || s.length() == 0) return new UserContest();
        return objectMapper.readValue(s, UserContest.class);
    }
}
