package com.rachid.Crud.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    private String confirmationToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    @OneToOne(targetEntity = Users.class)
    @JoinColumn(nullable = false)
    private Users user;


    public ConfirmationToken(Users user){
        this.createdDate = new Date();
        this.user = user;
        this.confirmationToken = UUID.randomUUID().toString();
    }
}
