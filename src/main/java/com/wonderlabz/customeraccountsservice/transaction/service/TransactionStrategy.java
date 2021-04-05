package com.wonderlabz.customeraccountsservice.transaction.service;


import com.wonderlabz.customeraccountsservice.transaction.dto.CreateTransactionDto;
import com.wonderlabz.customeraccountsservice.transaction.dto.ViewTransactionDto;

public interface TransactionStrategy {

  ViewTransactionDto createTransaction(CreateTransactionDto transactionDto);
}
