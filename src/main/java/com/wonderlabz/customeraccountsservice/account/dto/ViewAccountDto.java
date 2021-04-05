package com.wonderlabz.customeraccountsservice.account.dto;


import com.wonderlabz.customeraccountsservice.common.AccountType;
import com.wonderlabz.customeraccountsservice.common.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Data
public class ViewAccountDto extends BaseDto implements Serializable {

  private AccountType accountType;
  private String accountNumber;
  private String  customerIdNumber;
  private String customerName;
  private String surname;
  private String currency;
  private double  accountBalance;
  private LocalDate creationDate;
}
