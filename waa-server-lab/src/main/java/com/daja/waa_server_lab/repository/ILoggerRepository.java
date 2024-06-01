package com.daja.waa_server_lab.repository;

import com.daja.waa_server_lab.entity.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ILoggerRepository extends JpaRepository<Logger, Long> {
}
