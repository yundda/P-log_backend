package com.example.plog.repository.detaillog;

import java.time.LocalDateTime;
import com.example.plog.repository.BaseEntity;
import com.example.plog.repository.Enum.Mealtype;
import com.example.plog.repository.petlog.PetlogEntity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString
@Table(name = "DetailLog")
public class DetaillogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "log_id", nullable = false)
    private PetlogEntity log;

    @Column(name = "log_time", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime log_time;

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
