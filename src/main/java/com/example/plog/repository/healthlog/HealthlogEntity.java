package com.example.plog.repository.healthlog;

import java.time.LocalTime;

import com.example.plog.repository.BaseEntity;
import com.example.plog.repository.petlog.PetlogEntity;

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
@Table(name = "HealthLog")
public class HealthlogEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
