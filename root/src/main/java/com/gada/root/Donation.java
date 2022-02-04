package com.gada.root;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.CreditCardNumber;
import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Min(value = 5, message = "minimum allowed donation is 5 ETB")
    @NotNull
    private float amount;

    private String donatorName;
    @NotBlank(message = "You haven't entered account")
    // @CreditCardNumber(message = "Enter valid Credit Card Number")
    private String donatorAccount;
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.ALL)
    private Posts post;
    
    //optional
    @ManyToOne
    private User user;
   
}