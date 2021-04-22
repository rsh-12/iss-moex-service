package ru.task.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.task.iss.entity.History;

public interface HistoryRepository extends JpaRepository<History, Long> {

}
