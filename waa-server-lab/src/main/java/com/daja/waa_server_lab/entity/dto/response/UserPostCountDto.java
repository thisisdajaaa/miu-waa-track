package com.daja.waa_server_lab.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPostCountDto {
    private Long id;
    private String name;
    private Long count;
}
