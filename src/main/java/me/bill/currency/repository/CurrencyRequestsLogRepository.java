package me.bill.currency.repository;

import me.bill.currency.domain.CurrencyRequestLogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRequestsLogRepository extends JpaRepository<CurrencyRequestLogEntry, Long> {
}
