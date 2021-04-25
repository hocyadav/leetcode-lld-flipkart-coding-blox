package io.hari.demo.entity;

import io.hari.demo.entity.converter.ContestQuestionsConverter;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;

/**
 * @Author hayadav
 * @create 4/24/2021
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"contests"}, callSuper = true)
@Builder
@Entity
@Table(name = "users")
public class User extends BaseEntity{
    @Column(unique = true)
    String username;

    BigInteger score;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Contest> contests;

    @Convert(converter = ContestQuestionsConverter.class)
    ContestQuestions contestQuestions;
}
