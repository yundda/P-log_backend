package com.example.plog.repository.request;

import com.example.plog.repository.BaseEntity;
import com.example.plog.repository.Enum.Status;
import com.example.plog.repository.pet.PetEntity;
import com.example.plog.repository.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "request")
public class RequestEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Requester -> requester 로 변경
    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private UserEntity requester;

    @ManyToOne
    @JoinColumn(name = "pet_id", nullable = false)
    private PetEntity pet;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = true)
    private UserEntity receiver;

    @Column(name = "receiver_email", nullable = true)
    private String receiverEmail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Column(name = "is_requester_owner", nullable = false)
    private Boolean isRequesterOwner;

    public RequestEntity(UserEntity requester, PetEntity pet, UserEntity receiver, Status status, Boolean isRequesterOwner) {
        this.requester = requester;
        this.pet = pet;
        this.receiver = receiver;
        this.status = status;
        this.isRequesterOwner = isRequesterOwner;
    }

    public RequestEntity(UserEntity requester, PetEntity pet, String receiverEmail, Status status, Boolean isRequesterOwner) {
        this.requester = requester;
        this.pet = pet;
        this.receiverEmail = receiverEmail;
        this.status = status;
        this.isRequesterOwner = isRequesterOwner;
    }
    
}
