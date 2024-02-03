package ru.projects.restaurant_voting.util;


import jakarta.validation.constraints.NotNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.Clock;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@UtilityClass
public final class ClockHolder {

    private static final AtomicReference<Clock> CLOCK_REFERENCE = new AtomicReference<>(Clock.systemDefaultZone());

    @NotNull
    public static Clock getClock() {
        return CLOCK_REFERENCE.get();
    }

    /**
     * Atomically sets the value to {@code newClock} and returns the old value.
     *
     * @param newClock the new value
     */
    public static void setClock(@NotNull final Clock newClock) {
        final Clock oldClock = CLOCK_REFERENCE.getAndSet(newClock);
        log.info("Set new clock {}. Old clock is {}", newClock, oldClock);
    }
}