package com.ankat.service;

import com.ankat.entity.Customer;
import com.ankat.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service(value = "customerService")
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer insertCustomer(Customer customer){
        return customerRepository.save(customer);
    }


}
