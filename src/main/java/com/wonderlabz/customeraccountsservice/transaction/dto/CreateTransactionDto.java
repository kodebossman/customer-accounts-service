package com.wonderlabz.customeraccountsservice.transaction.dto;


import com.wonderlabz.customeraccountsservice.common.BaseDto;
import com.wonderlabz.customeraccountsservice.common.TransactionType;
import lombok.Data;

import java.io.Serializable;

@Data
public class CreateTransactionDto extends BaseDto implements Serializable {

  private TransactionType transactionType;
  private double amount;
  private String accountNumber;
  private Long customerId;
  private String destinationAccountNumber;

}
