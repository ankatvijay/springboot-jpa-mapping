package com.ankat;

import com.ankat.entity.Customer;
import com.ankat.entity.ShippingAddress;
import com.ankat.service.CustomerService;
import com.ankat.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class OneToManyUnidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;
    public static void main(String[] args) {
        SpringApplication.run(OneToManyUnidirectionalMapping.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            Customer customer = new Customer();
            customer.setFirstname("Vijay");
            customer.setLastname("Ankat");
            customer.setEmail("test@gmail.com");
            customer.setPhone("9876543210");

            ShippingAddress shippingAddress1 = new ShippingAddress();
            shippingAddress1.setStreet("Renolds Colony");
            shippingAddress1.setCity("Mumbai");
            shippingAddress1.setProvince("Wadala");
            shippingAddress1.setZip("400037");
            shippingAddress1.setCountry("India");

            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setStreet("Nadkarni Park");
            shippingAddress2.setCity("Mumbai");
            shippingAddress2.setProvince("Wadala");
            shippingAddress2.setZip("400037");
            shippingAddress2.setCountry("India");

            Set<ShippingAddress> shippingAddresses = new HashSet<>();
            shippingAddresses.add(shippingAddress1);
            shippingAddresses.add(shippingAddress2);

            customer.setShippingAddress(shippingAddresses);

            Customer savedCustomer = customerService.insertCustomer(customer);
            log.info(String.valueOf(customer));
        };
    }
}