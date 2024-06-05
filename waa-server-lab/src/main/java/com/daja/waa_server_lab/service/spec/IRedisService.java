package com.daja.waa_server_lab.service.spec;

import java.time.Duration;

public interface IRedisService {
    Object get(String key);

    void add(String key, Object value, Duration expirationTime);

    void delete(String key);

}
