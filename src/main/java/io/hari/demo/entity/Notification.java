package io.hari.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author Hariom Yadav
 */
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "notifications")
public class Notification extends BaseEntity {
    private String message;
    private Long userId;
}
