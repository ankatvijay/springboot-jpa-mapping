package com.ankat.service;

import com.ankat.entity.ShippingAddress;
import com.ankat.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service(value = "shippingAddressService")
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Transactional
    public ShippingAddress insertShippingAddress(final ShippingAddress shippingAddress) {
        return shippingAddressRepository.saveAndFlush(shippingAddress);
    }
}
