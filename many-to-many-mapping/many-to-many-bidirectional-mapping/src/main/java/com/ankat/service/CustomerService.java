package com.ankat.service;

import com.ankat.entity.Customer;
import com.ankat.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service(value = "customerService")
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Customer insertCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    @Transactional
    public List<Customer> insertCustomers(Set<Customer> customers){
        return customerRepository.saveAll(customers);
    }
}
