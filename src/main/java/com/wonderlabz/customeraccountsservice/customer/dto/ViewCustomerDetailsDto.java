package com.wonderlabz.customeraccountsservice.customer.dto;


import com.wonderlabz.customeraccountsservice.common.BaseDto;
import com.wonderlabz.customeraccountsservice.common.Gender;
import com.wonderlabz.customeraccountsservice.model.Account;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
public class ViewCustomerDetailsDto extends BaseDto implements Serializable {

  private String customerName;
  private String customerIdNumber;
  private String surname;
  private String address;
  private String phoneNumber;
  private Gender gender;
  private String email;
  private LocalDate dateOfBirth;
  private List<Account> accountList;
}
