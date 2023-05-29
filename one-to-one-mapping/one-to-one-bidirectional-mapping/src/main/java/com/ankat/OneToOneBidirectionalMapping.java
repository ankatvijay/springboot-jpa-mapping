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

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class OneToOneBidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    public static void main(String[] args) {
        SpringApplication.run(OneToOneBidirectionalMapping.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            /*
            ShippingAddress shippingAddress1 = ShippingAddress.builder()
                    .street("Renolds Colony 1")
                    .city("Mumbai 1")
                    .province("Wadala 1")
                    .zip("400037")
                    .country("India")
                    .build();

            Customer customer1 = Customer.builder()
                    .firstname("Vijay")
                    .lastname("Ankat")
                    .email("test@gmail.com")
                    .phone("9876543210")
                    .shippingAddress(shippingAddress1)
                    .build();
            */
            //Customer savedCustomer = customerService.insertCustomer(customer1);
            //log.info(String.valueOf(savedCustomer));

            Customer customer2 = new Customer();
            customer2.setFirstname("Sushma");
            customer2.setLastname("Ankat");
            customer2.setEmail("test@gmail.com");
            customer2.setPhone("9876543210");


            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setProvince("Renolds Colony 2");
            shippingAddress2.setCity("Mumbai 2");
            shippingAddress2.setStreet("Wadala 2");
            shippingAddress2.setZip("400037");
            shippingAddress2.setCountry("India");

            customer2.setShippingAddress(shippingAddress2);
            shippingAddress2.setCustomer(customer2);

            ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress2);
            log.info(String.valueOf(savedShippingAddress));
        };
    }
}