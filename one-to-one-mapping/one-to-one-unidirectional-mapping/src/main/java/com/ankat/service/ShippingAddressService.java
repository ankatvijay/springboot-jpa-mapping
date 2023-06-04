package com.ankat.service;

import com.ankat.entity.ShippingAddress;
import com.ankat.exception.MissMatchRecordException;
import com.ankat.exception.RecordNotFoundException;
import com.ankat.repository.ShippingAddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@RequiredArgsConstructor
@Service(value = "shippingAddressService")
public class ShippingAddressService {

    private final ShippingAddressRepository shippingAddressRepository;

    public List<ShippingAddress> selectAllShippingAddresses() {
        return shippingAddressRepository.findAll();
    }

    public ShippingAddress selectByIdShippingAddress(Long id) {
        return shippingAddressRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("No record found with id " + id));
    }

    public boolean existByIdShippingAddress(Long id) {
        return shippingAddressRepository.existsById(id);
    }

    @Transactional
    public ShippingAddress insertShippingAddress(ShippingAddress shippingAddress) {
        return shippingAddressRepository.save(shippingAddress);
    }

    @Transactional
    public List<ShippingAddress> insertShippingAddresses(Set<ShippingAddress> shippingAddresses) {
        return shippingAddressRepository.saveAll(shippingAddresses);
    }

    @Transactional
    public ShippingAddress updateShippingAddress(Long id, ShippingAddress shippingAddress) {
        if (id > 0 && Objects.nonNull(shippingAddress) && Objects.nonNull(shippingAddress.getId())) {
            if (id == shippingAddress.getId().intValue()) {
                if (shippingAddressRepository.existsById(id)) {
                    return shippingAddressRepository.save(shippingAddress);
                }
                throw new RecordNotFoundException("No record found with id " + id);
            } else {
                throw new MissMatchRecordException("Update record id: " + id + " not equal to payload record id: " + shippingAddress.getId());
            }
        } else {
            throw new NullPointerException("Payload record id is null");
        }
    }

    @Transactional
    public boolean deleteByIdShippingAddress(Long id) {
        shippingAddressRepository.delete(selectByIdShippingAddress(id));
        return true;
    }

    @Transactional
    public void deleteAllShippingAddresses() {
        shippingAddressRepository.deleteAll();
    }
}
