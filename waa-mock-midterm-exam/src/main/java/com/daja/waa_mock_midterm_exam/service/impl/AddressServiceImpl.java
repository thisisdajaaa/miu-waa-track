package com.daja.waa_mock_midterm_exam.service.impl;

import com.daja.waa_mock_midterm_exam.configuration.MapperConfiguration;
import com.daja.waa_mock_midterm_exam.entity.Address;
import com.daja.waa_mock_midterm_exam.entity.dto.request.AddressDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.AddressDetailDto;
import com.daja.waa_mock_midterm_exam.exception.AddressException;
import com.daja.waa_mock_midterm_exam.repository.IAddressRepository;
import com.daja.waa_mock_midterm_exam.service.spec.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl implements IAddressService {
    private final IAddressRepository addressRepository;
    private final MapperConfiguration mapperConfiguration;

    @Autowired
    public AddressServiceImpl(IAddressRepository addressRepository, MapperConfiguration mapperConfiguration) {
        this.addressRepository = addressRepository;
        this.mapperConfiguration = mapperConfiguration;
    }

    @Override
    public List<AddressDetailDto> findAll(Map<String, String> filters) {
        return addressRepository.findAll().stream()
                .map(i -> mapperConfiguration.convert(i, AddressDetailDto.class))
                .toList();
    }

    @Override
    public AddressDetailDto findById(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(AddressException.NotFoundException::new);
        return mapperConfiguration.convert(address, AddressDetailDto.class);
    }

    @Override
    public AddressDetailDto add(AddressDto addressDto) {
        Address address = mapperConfiguration.convert(addressDto, Address.class);
        addressRepository.save(address);
        return mapperConfiguration.convert(address, AddressDetailDto.class);
    }

    @Override
    public AddressDetailDto delete(Long id) {
        AddressDetailDto address = findById(id);
        addressRepository.deleteById(id);
        return address;
    }

    @Override
    public AddressDetailDto update(Long id, AddressDto updatedDto) {
        Address address = addressRepository.findById(id).orElseThrow(AddressException.NotFoundException::new);

        if (updatedDto.getCity() != null)
            address.setCity(updatedDto.getCity());
        if (updatedDto.getState() != null)
            address.setState(updatedDto.getState());
        if (updatedDto.getState() != null)
            address.setZipcode(updatedDto.getZipcode());

        Address updatedAddress =  addressRepository.save(address);;

        return mapperConfiguration.convert(updatedAddress, AddressDetailDto.class);
    }
}
