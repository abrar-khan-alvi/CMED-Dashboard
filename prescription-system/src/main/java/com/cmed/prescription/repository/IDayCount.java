package com.cmed.prescription.repository;

import java.time.LocalDate;

public interface IDayCount {
    LocalDate getDate();

    Long getCount();
}