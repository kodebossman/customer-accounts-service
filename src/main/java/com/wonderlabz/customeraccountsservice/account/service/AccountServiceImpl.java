package com.wonderlabz.customeraccountsservice.account.service;


import com.wonderlabz.customeraccountsservice.account.dto.CreateAccountDto;
import com.wonderlabz.customeraccountsservice.account.dto.ViewAccountDto;
import com.wonderlabz.customeraccountsservice.account.repository.AccountRepository;
import com.wonderlabz.customeraccountsservice.customer.repository.CustomerServiceRepository;
import com.wonderlabz.customeraccountsservice.exceptions.BadRequestDataException;
import com.wonderlabz.customeraccountsservice.model.Account;
import com.wonderlabz.customeraccountsservice.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(propagation = Propagation.REQUIRED)
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final CustomerServiceRepository customerServiceRepository;

  public AccountServiceImpl(AccountRepository accountRepository, CustomerServiceRepository customerServiceRepository) {

    this.accountRepository = accountRepository;
    this.customerServiceRepository = customerServiceRepository;
  }

  @Override
  public ViewAccountDto openAccount(CreateAccountDto createAccountDto) {

    log.info("Register Account :{}", createAccountDto);
    Customer customer = checkCustomerByIdNumber(createAccountDto.getCustomerIdNumber());
     if(customer==null){
      throw new BadRequestDataException("Customer with this ID"+createAccountDto.getCustomerIdNumber() +" not found");
    }

    Account account = createAcccount(createAccountDto);
    accountRepository.save(account);

    //create savings account if current account >1000

    return getViewAccountDto(account);
  }

  @Override
  public List<Account> checkAccount(Customer customer) {
    return accountRepository.findByCustomer(customer);
  }

  private ViewAccountDto getViewAccountDto(Account account) {
    ViewAccountDto viewAccountDto = new ViewAccountDto();
    viewAccountDto.setAccountBalance(account.getAccountBalance());
    viewAccountDto.setAccountNumber(account.getAccountNumber());
    viewAccountDto.setAccountType(account.getAccountType());
    viewAccountDto.setCreationDate(account.getCreationDate());
    viewAccountDto.setCustomerIdNumber(account.getCustomerIdNumber());
    viewAccountDto.setCustomerName(checkCustomerByIdNumber(account.getCustomerIdNumber()).getCustomerName());
    viewAccountDto.setSurname(checkCustomerByIdNumber(account.getCustomerIdNumber()).getSurname());
    viewAccountDto.setCurrency(account.getCurrency());
    return viewAccountDto;
  }

  @Override
  public ViewAccountDto findAccountById(Long id) {

    log.info("Getting account :{}", id);
    Optional<Account> account = accountRepository.findById(id);

    if (account.isPresent()) {
      return getViewAccountDto(account.get());
    }else {
      throw new IllegalArgumentException("Account is not found with ID "+ id);
    }
  }

  @Override
  public ViewAccountDto findByAccountNumber(String accountNumber) {

    Account account = accountRepository.findByAccountNumber(accountNumber);
    if(!Objects.isNull(account)){
      return getViewAccountDto(account);
    }else {
      throw new IllegalArgumentException("Account is not found with Id"+ accountNumber);
    }
  }

  @Override
  public List<ViewAccountDto> findAllAccountsByNationalId(String nationalId) {
    //TODO Implement this method
    return null;
  }

  @Override
  public List<ViewAccountDto> findAllAccounts() {

    List<Account> accountList=  accountRepository.findAll();

    return createViewAccountDtoList(accountList);

  }


  protected Account createAcccount(CreateAccountDto createAccountDto) {
    //dry principle for create/update
    //TODO to user mapper or gson
    Account account = new Account();
    account.setAccountNumber(createAccountDto.getAccountNumber());
    account.setAccountBalance(createAccountDto.getAccountBalance());
    account.setAccountType(createAccountDto.getAccountType());
    account.setCreationDate(LocalDate.now());
    account.setCustomerIdNumber(createAccountDto.getCustomerIdNumber());
    account.setCurrency(createAccountDto.getCurrency());

    return account;

  }

  protected Customer checkCustomerByIdNumber(String nationalId){

    return customerServiceRepository.findCustomerByCustomerIdNumber(nationalId);
  }

  public List<ViewAccountDto> createViewAccountDtoList(List<Account> accountList){

    if(accountList.isEmpty()){
      return  new ArrayList<>();
    }
    return accountList.stream().map(this::getViewAccountDto).collect(Collectors.toList());
  }
}
