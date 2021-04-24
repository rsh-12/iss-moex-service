package ru.task.iss.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.task.iss.entity.History;

import java.time.LocalDate;

public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("from History h where h.tradeDate = :date")
    Page<History> findAll(Pageable pageable, @Param("date") LocalDate tradeDate);
}
