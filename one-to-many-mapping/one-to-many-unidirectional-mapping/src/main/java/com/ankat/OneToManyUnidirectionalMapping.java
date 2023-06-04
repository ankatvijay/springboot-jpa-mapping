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
            // Payload Customer to ShippingAddress
            Customer customer = new Customer();
            customer.setFirstname("Tom");
            customer.setLastname("Hendry");
            customer.setEmail("test@gmail.com");
            customer.setPhone("9876543210");

            ShippingAddress shippingAddress1 = new ShippingAddress();
            shippingAddress1.setProvince("Washington Province");
            shippingAddress1.setCity("Washington");
            shippingAddress1.setStreet("Washington Street");
            shippingAddress1.setZip("115475");
            shippingAddress1.setCountry("US");

            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setProvince("LA Province");
            shippingAddress2.setCity("LA");
            shippingAddress2.setStreet("LA Street");
            shippingAddress2.setZip("1154873");
            shippingAddress2.setCountry("US");

            Set<ShippingAddress> shippingAddresses = new HashSet<>();
            shippingAddresses.add(shippingAddress1);
            shippingAddresses.add(shippingAddress2);

            customer.setShippingAddress(shippingAddresses);

            Customer savedCustomer = customerService.insertCustomer(customer);
            log.info("Customer Saved : " + savedCustomer);
        };
    }
}