package com.wonderlabz.customeraccountsservice.transaction.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SendMoneyDto extends CreateTransactionDto implements Serializable {
  private String destinationAccount;
}
