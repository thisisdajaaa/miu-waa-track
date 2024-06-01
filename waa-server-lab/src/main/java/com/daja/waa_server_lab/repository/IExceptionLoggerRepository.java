package com.daja.waa_server_lab.repository;

import com.daja.waa_server_lab.entity.ExceptionLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExceptionLoggerRepository extends JpaRepository<ExceptionLogger, Long> {
}
