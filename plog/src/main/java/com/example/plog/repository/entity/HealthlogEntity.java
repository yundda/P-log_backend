package com.example.plog.repository.entity;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "HealthLog")
public class HealthlogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    @OneToOne
    @JoinColumn(name = "log_id", nullable = false)
    private PetlogEntity log_id;

    @Column(name = "vaccination")
    private String vaccination;

    @Column(name = "vaccination_log")
    private Boolean vaccination_log;

    @Column(name = "hospital")
    private String hospital;

    @Column(name = "hospital_log")
    private LocalTime hospital_log;

}
