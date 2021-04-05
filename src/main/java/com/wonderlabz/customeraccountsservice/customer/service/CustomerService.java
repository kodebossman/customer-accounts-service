package com.wonderlabz.customeraccountsservice.customer.service;


import com.wonderlabz.customeraccountsservice.customer.dto.CreateCustomerDto;
import com.wonderlabz.customeraccountsservice.model.Customer;

public interface CustomerService {
  Customer getCustomerByIdNumber(String customerIdNumber);
  Customer createCustomer(CreateCustomerDto customerDto);
  Customer getCustomerById(Long Id);

}
