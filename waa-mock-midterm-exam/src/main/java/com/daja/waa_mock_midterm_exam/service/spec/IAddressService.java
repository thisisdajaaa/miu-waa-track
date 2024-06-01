package com.daja.waa_mock_midterm_exam.service.spec;

import com.daja.waa_mock_midterm_exam.entity.dto.request.AddressDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.AddressDetailDto;

import java.util.List;
import java.util.Map;

public interface IAddressService {
    List<AddressDetailDto> findAll(Map<String, String> filters);

    AddressDetailDto findById(Long id);

    AddressDetailDto add(AddressDto addressDto);

    AddressDetailDto delete(Long id);

    AddressDetailDto update(Long id, AddressDto updatedDto);
}
