package ru.task.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.task.iss.entity.Security;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Integer> {

    @Query("from Security s where s.id=:id")
    @Override
    Optional<Security> findById(@Param("id") Integer id);

    void deleteBySecId(String secId);

    Optional<Security> findBySecId(String secId);
}
