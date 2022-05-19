package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ResetCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private String id;
    //Foreign Key
    private Integer userid;
    //Optional
    //private long timeGenerated;
}
