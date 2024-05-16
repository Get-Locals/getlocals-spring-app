package com.getlocals.getlocals.utils;

import com.getlocals.getlocals.business.entities.BusinessTiming;
import com.getlocals.getlocals.business.repositories.BusinessTimingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class Schedulers {

    @Autowired
    private BusinessTimingRepo businessTimingRepo;

    @Scheduled(cron = "@midnight")
    @Retryable(retryFor = { Exception.class }, maxAttempts = 2, backoff = @Backoff(delay = 300000))
    public void refreshDailyBusinessStatus() {
        Optional<DayOfWeek> dayOfWeekOptional = Optional.ofNullable(LocalDate.now().plusDays(1).getDayOfWeek());
        String tomorrow = dayOfWeekOptional.map(DayOfWeek::toString).map(String::toLowerCase).orElseThrow();

        List<BusinessTiming> allTimings = businessTimingRepo.findAll();
        allTimings.forEach(timings -> {
            timings.setToday(timings.getTomorrow());
            String tomorrowStatus = switch (tomorrow) {
                case "monday" -> timings.getMonday();
                case "tuesday" -> timings.getTuesday();
                case "wednesday" -> timings.getWednesday();
                case "thursday" -> timings.getThursday();
                case "friday" -> timings.getFriday();
                case "saturday" -> timings.getSaturday();
                case "sunday" -> timings.getSunday();
                default -> {
                    log.info("Invalid Day... This would be weird if occurred.");
                    yield null;
                }
            };

            if ("CLOSED".equalsIgnoreCase(tomorrowStatus)) {
                timings.setTomorrow("CLOSED");
            } else {
                timings.setTomorrow("OPEN");
            }
        });

        businessTimingRepo.saveAll(allTimings);
        log.info("Updated All business's timings for today.");
    }
}
