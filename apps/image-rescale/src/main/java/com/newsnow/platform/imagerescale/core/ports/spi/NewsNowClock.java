package com.newsnow.platform.imagerescale.core.ports.spi;

import java.time.LocalDateTime;

public interface NewsNowClock {

    LocalDateTime currentTimestamp();
}
