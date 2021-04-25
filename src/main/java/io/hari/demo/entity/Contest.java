package io.hari.demo.entity;

import io.hari.demo.constant.ContestStatus;
import io.hari.demo.constant.Level;
import io.hari.demo.entity.converter.ContestQuestionsConverter;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {}, callSuper = true)
@Builder
@Entity
@Table(name = "contests")
public class Contest extends BaseEntity{
    String name;

    Long userId;

    @Enumerated(EnumType.STRING)
    Level level;

    @Enumerated(EnumType.STRING)
    ContestStatus status;

    @Convert(converter = ContestQuestionsConverter.class)
    ContestQuestions contestQuestions;
}
