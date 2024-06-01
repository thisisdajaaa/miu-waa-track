package com.daja.waa_mock_midterm_exam.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressDto {
    private String city;
    private String state;
    private Integer zipcode;
}
