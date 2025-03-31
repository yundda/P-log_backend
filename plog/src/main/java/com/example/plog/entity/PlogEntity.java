package com.example.plog.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class PlogEntity extends BaseEntity {

    @Id
    @GeneratedValue(Strategy = GenetationType.IDENTITY)
    private Long gno;

    @Column()

}
