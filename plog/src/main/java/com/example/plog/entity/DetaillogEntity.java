package com.example.plog.entity;

import java.time.LocalTime;

import com.example.plog.entity.Enum.Mealtype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "DetailLog")
public class DetaillogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @OneToOne
    @JoinColumn(name = "log_id", nullable = false)
    private PetlogEntity log_id;

    @Column(name = "log_time", nullable = false)
    private LocalTime log_time;

    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private Mealtype meal_type;

    @Column(name = "place")
    private String place;

    @Column(name = "price")
    private Integer price;

    @Column(name = "take_time")
    private Integer take_time;

    @Column(name = "meno")
    private String memo;
}
