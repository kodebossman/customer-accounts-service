package com.wonderlabz.customeraccountsservice.account.service;


import com.wonderlabz.customeraccountsservice.account.dto.CreateAccountDto;
import com.wonderlabz.customeraccountsservice.account.dto.ViewAccountDto;
import com.wonderlabz.customeraccountsservice.model.Account;
import com.wonderlabz.customeraccountsservice.model.Customer;

import java.util.List;

public interface AccountService {

  ViewAccountDto openAccount(CreateAccountDto createAccountDto);
  List<Account> checkAccount(Customer customer);
  ViewAccountDto findAccountById(Long id);
  ViewAccountDto findByAccountNumber(String accountNumber);
  List<ViewAccountDto> findAllAccountsByNationalId(String nationalId);
  List<ViewAccountDto> findAllAccounts();
}
