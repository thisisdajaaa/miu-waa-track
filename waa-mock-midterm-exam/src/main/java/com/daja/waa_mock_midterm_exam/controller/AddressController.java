package com.daja.waa_mock_midterm_exam.controller;

import com.daja.waa_mock_midterm_exam.entity.dto.request.AddressDto;
import com.daja.waa_mock_midterm_exam.entity.dto.response.AddressDetailDto;
import com.daja.waa_mock_midterm_exam.helper.QueryParamHelper;
import com.daja.waa_mock_midterm_exam.helper.ResponseHelper;
import com.daja.waa_mock_midterm_exam.service.spec.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {
    private final IAddressService addressService;

    @Autowired
    public AddressController(IAddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public ResponseEntity<ResponseHelper.CustomResponse<List<AddressDetailDto>>> getAddresses(
            @RequestParam(required = false) String filter
    ) {
        Map<String, String> transformedFilter = QueryParamHelper.transformedFilter(filter);

        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved addresses!", addressService.findAll(transformedFilter)),
                HttpStatus.OK);
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<ResponseHelper.CustomResponse<AddressDetailDto>> getAddress(
            @PathVariable Long addressId
    ) {
        AddressDetailDto address = addressService.findById(addressId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully retrieved address!", address),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseHelper.CustomResponse<AddressDetailDto>> createAddress(
            @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully created an address!", addressService.add(addressDto)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<ResponseHelper.CustomResponse<AddressDetailDto>> deleteAddress(
            @PathVariable Long addressId
    ) {
        AddressDetailDto address = addressService.delete(addressId);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully deleted an address!", address),
                HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{addressId}")
    public ResponseEntity<ResponseHelper.CustomResponse<AddressDetailDto>> updateAddress(
            @PathVariable Long addressId,
            @RequestBody AddressDto addressDto
    ) {
        AddressDetailDto updatedAddress = addressService.update(addressId, addressDto);
        return new ResponseEntity<>(
                new ResponseHelper.CustomResponse<>(true, "Successfully updated an address!", updatedAddress),
                HttpStatus.OK);
    }
}
