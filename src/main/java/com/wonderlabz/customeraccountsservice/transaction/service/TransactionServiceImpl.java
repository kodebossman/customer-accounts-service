package com.wonderlabz.customeraccountsservice.transaction.service;

import com.wonderlabz.customeraccountsservice.common.TransactionType;
import com.wonderlabz.customeraccountsservice.model.Transaction;
import com.wonderlabz.customeraccountsservice.transaction.dto.ViewTransactionDto;
import com.wonderlabz.customeraccountsservice.transaction.repository.TransactionRepository;
import com.wonderlabz.customeraccountsservice.util.CheckBankDetailsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class TransactionServiceImpl implements TransactionService{

  private final TransactionRepository transactionRepository;

  public TransactionServiceImpl(TransactionRepository transactionRepository) {

    this.transactionRepository=transactionRepository;
  }

  @Override
  public List<ViewTransactionDto> getTransactionHistoryByType(TransactionType transactionType, Long customerId) {

    log.info("Get Transaction  history by transaction type :{}");

    List<Transaction> transactionList=  transactionRepository.findByTransactionTypeAndCustomerId( transactionType, customerId );

    return createViewTransactionDto(transactionList);
  }

  @Override
  public List<ViewTransactionDto> getAllTransactions(Long customerId) {

    log.info("Get Transaction  history for customer Id:{} " +customerId);

    List<Transaction> transactionList=  transactionRepository.findByCustomerId(customerId );

    return createViewTransactionDto(transactionList);
  }

  public List<ViewTransactionDto> createViewTransactionDto(List<Transaction> transactionList){

    if(transactionList.isEmpty()){
      return  new ArrayList<>();
    }
      return transactionList.stream().map(this::getViewTransactionDto).collect(Collectors.toList());
  }

  private ViewTransactionDto getViewTransactionDto( Transaction transaction) {

   return CheckBankDetailsUtil.getViewTransactionDto(transaction,
          CheckBankDetailsUtil.checkAccountInfo(transaction.getAccountNumber()));

  }

}
