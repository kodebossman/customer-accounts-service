package com.wonderlabz.customeraccountsservice.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wonderlabz.customeraccountsservice.common.AccountType;
import com.wonderlabz.customeraccountsservice.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "accounts", indexes = {@Index(name = "indx_account", columnList = "id", unique = true)})
@Getter
@Setter
@ToString
public class Account extends BaseEntity {

  @Column(name = "acc_type", nullable = false, length = 45)
  private AccountType accountType;

  @Column(name = "accountNumber", nullable = false, unique = true, length = 45)
  private String accountNumber;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "customer_id", nullable = false)
  @JsonIgnoreProperties
  @Transient
  private Customer customer;

  @Column(name = "customeraccountnumber", nullable = false, length = 45)
  private String customerIdNumber;

  @Column(name = "currency", length = 5, unique = false, nullable = false)
  private String currency;

  @Column(name = "shares",  unique = false, nullable = false)
  private double  accountBalance;

  @Column(name = "creationDate", nullable = false)
  private LocalDate creationDate;

}
