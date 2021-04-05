package com.wonderlabz.customeraccountsservice.transaction.repository;


import com.wonderlabz.customeraccountsservice.common.TransactionType;
import com.wonderlabz.customeraccountsservice.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {

  List<Transaction> findByTransactionTypeAndCustomerId(TransactionType transactionType, Long customerId);
  List<Transaction> findByCustomerId(Long customerId);

}
