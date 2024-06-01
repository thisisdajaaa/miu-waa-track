package com.daja.waa_server_lab.entity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoggerDetailDto {
    private Long id;

    private String transactionId;

    private LocalDateTime dateTime;

    private String principle;

    private String operation;
}
