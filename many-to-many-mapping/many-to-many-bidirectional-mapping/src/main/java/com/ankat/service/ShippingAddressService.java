package com.ankat.service;

import com.ankat.entity.ShippingAddress;
import com.ankat.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service(value = "shippingAddressService")
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    @Transactional
    public ShippingAddress insertShippingAddress(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }

    @Transactional
    public List<ShippingAddress> insertShippingAddresses(Set<ShippingAddress> shippingAddresses) {
        return shippingAddressRepository.saveAll(shippingAddresses);
    }
}
