package com.daja.waa_server_lab.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "exception_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionLogger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_id", nullable = false)
    private String transactionId;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "principle", nullable = false)
    private String principle;

    @Column(name = "operation", nullable = false)
    private String operation;

    @Column(name = "exception_type", nullable = false)
    private String exceptionType;
}
