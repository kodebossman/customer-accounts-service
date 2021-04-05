package com.wonderlabz.customeraccountsservice.transaction.processors;


import com.wonderlabz.customeraccountsservice.account.repository.AccountRepository;
import com.wonderlabz.customeraccountsservice.model.Account;
import com.wonderlabz.customeraccountsservice.model.Transaction;
import com.wonderlabz.customeraccountsservice.transaction.dto.CreateTransactionDto;
import com.wonderlabz.customeraccountsservice.transaction.dto.ViewSendMoneyDto;
import com.wonderlabz.customeraccountsservice.transaction.dto.ViewTransactionDto;
import com.wonderlabz.customeraccountsservice.transaction.repository.TransactionRepository;
import com.wonderlabz.customeraccountsservice.transaction.service.TransactionStrategy;
import com.wonderlabz.customeraccountsservice.util.CheckBankDetailsUtil;

public class SendMoneyProcessor implements TransactionStrategy {

 private AccountRepository accountRepository;
 private TransactionRepository transactionRepository;

  public SendMoneyProcessor(){

  }
  public SendMoneyProcessor(AccountRepository accountRepository,TransactionRepository transactionRepository) {
    this.accountRepository = accountRepository;
    this.transactionRepository = transactionRepository;

  }

  @Override
  public ViewTransactionDto createTransaction(CreateTransactionDto transactionDto) {
    //check if account exists else throw exception
    Account sourceAccount = CheckBankDetailsUtil.checkAccountInfo(transactionDto.getAccountNumber());
    Account destinationAccount = CheckBankDetailsUtil.checkAccountInfo(transactionDto.getDestinationAccountNumber());

    Transaction transaction = CheckBankDetailsUtil.getTransaction(transactionDto, sourceAccount);

    //debit in source account if the requirements are met else throw exception
    CheckBankDetailsUtil.checkWithdrawalLimit(sourceAccount.getAccountNumber(), transactionDto.getAmount());
    double sourceAccountBalance = sourceAccount.getAccountBalance();
    double debitedAccountBalance = sourceAccountBalance - transactionDto.getAmount();

    //we make it here we are ready to commit debit in the source account
    sourceAccount.setAccountBalance(debitedAccountBalance);
    CheckBankDetailsUtil.updateAccount(sourceAccount);

    //credit account being sent to
    destinationAccount.setAccountBalance(destinationAccount.getAccountBalance() + transactionDto.getAmount());
    CheckBankDetailsUtil.updateAccount(destinationAccount);
    //save transaction
    CheckBankDetailsUtil.saveTransaction(transaction);

    return getViewTransactionDto(sourceAccount, destinationAccount, transaction);
  }

  private ViewTransactionDto getViewTransactionDto(Account sourceAccount, Account destinationAccount, Transaction transaction) {
    ViewSendMoneyDto viewSendMoneyDto = new ViewSendMoneyDto();
    viewSendMoneyDto.setDestinationAccount(destinationAccount);
    viewSendMoneyDto.setFromAccount(sourceAccount);
    viewSendMoneyDto.setCustomerId(transaction.getCustomerId());
    viewSendMoneyDto.setTransactionType(transaction.getTransactionType());
    viewSendMoneyDto.setTransactionDateTime(transaction.getTransactionDate());
    viewSendMoneyDto.setAccountNumber(transaction.getAccountNumber());
    viewSendMoneyDto.setAmount(transaction.getAmount());
    viewSendMoneyDto.setDestinationAccountNumber(destinationAccount.getAccountNumber());

    return viewSendMoneyDto;
  }


}
