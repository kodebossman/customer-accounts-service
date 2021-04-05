package com.wonderlabz.customeraccountsservice.transaction.processors;


import com.wonderlabz.customeraccountsservice.account.repository.AccountRepository;
import com.wonderlabz.customeraccountsservice.exceptions.BadRequestDataException;
import com.wonderlabz.customeraccountsservice.model.Account;
import com.wonderlabz.customeraccountsservice.model.Transaction;
import com.wonderlabz.customeraccountsservice.transaction.dto.CreateTransactionDto;
import com.wonderlabz.customeraccountsservice.transaction.dto.ViewTransactionDto;
import com.wonderlabz.customeraccountsservice.transaction.repository.TransactionRepository;
import com.wonderlabz.customeraccountsservice.transaction.service.TransactionStrategy;
import com.wonderlabz.customeraccountsservice.util.CheckBankDetailsUtil;

import java.util.Objects;

public class DepositProcessor implements TransactionStrategy {

  private static TransactionRepository transactionRepository;
  private static AccountRepository accountRepository;


  public DepositProcessor(){

  }
   public DepositProcessor(TransactionRepository transactionRepository, AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;
  }

  @Override
  public ViewTransactionDto createTransaction(CreateTransactionDto transactionDto) {
    Account account = CheckBankDetailsUtil.checkAccountInfo(transactionDto.getAccountNumber());
    Transaction transaction = null;
    if (!Objects.isNull(account)) {
      transaction = CheckBankDetailsUtil.getTransaction(transactionDto, account);
      transaction= CheckBankDetailsUtil.saveTransaction(transaction);

    } else {
      throw new BadRequestDataException("The Account provided is not valid:" + transactionDto.getAccountNumber() + " we can not complete the transaction");
    }
    //update/credit deposit account
    account.setAccountBalance(transactionDto.getAmount()+ account.getAccountBalance());
    CheckBankDetailsUtil.updateAccount(account);
    return CheckBankDetailsUtil.getViewTransactionDto(transaction, account);

  }

}