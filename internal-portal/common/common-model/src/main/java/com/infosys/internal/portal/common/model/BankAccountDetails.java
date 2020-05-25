package com.infosys.internal.portal.common.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "bank_account_details")
public class BankAccountDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank
    @Column(name = "account_no")
    private String accountNo;

    @NotBlank
    @Column(name = "account_holder")
    private String accountHolderName;

    @NotBlank
    @Column(name = "bank_name")
    private String bankName;

    @NotBlank
    @Column(name = "bank_address")
    private String bankAddress;

    @NotBlank
    @Column(name = "bic")
    private String BIC;

    @NotBlank
    @Column(name = "iban")
    private String IBAN;
}
