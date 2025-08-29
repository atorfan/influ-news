package com.newsnow.platform.imagerescale.adapters.driven;

import com.newsnow.platform.imagerescale.core.ports.spi.NewsNowClock;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SystemClock implements NewsNowClock {

    @Override
    public LocalDateTime currentTimestamp() {
        return LocalDateTime.now();
    }
}
