package com.wonderlabz.customeraccountsservice.transaction.service;



import com.wonderlabz.customeraccountsservice.common.TransactionType;
import com.wonderlabz.customeraccountsservice.transaction.dto.ViewTransactionDto;

import java.util.List;

public interface TransactionService {

  List<ViewTransactionDto> getTransactionHistoryByType(TransactionType transactionType, Long customerId);
  List<ViewTransactionDto> getAllTransactions(Long customerId );

}
