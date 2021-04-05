package com.wonderlabz.customeraccountsservice.transaction.dto;



import com.wonderlabz.customeraccountsservice.model.Account;
import com.wonderlabz.customeraccountsservice.model.Customer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ViewTransactionDto extends CreateTransactionDto implements Serializable {

  private Account account;
  private Account destinationAccount;
  private Customer customer;
  private LocalDateTime transactionDateTime;
  private Long transactionId;

}
