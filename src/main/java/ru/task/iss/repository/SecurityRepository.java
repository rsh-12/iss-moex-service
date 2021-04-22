package ru.task.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.task.iss.entity.Security;

import java.util.Optional;

public interface SecurityRepository extends JpaRepository<Security, Integer> {

    @Query("from Security s where s.id=:id")
    @Override
    Optional<Security> findById(@Param("id") Integer id);

    void deleteBySecId(String secId);

    @Override
    @Modifying
    @Query(nativeQuery = true, value = "delete from securities s where s.id = :id")
    void deleteById(@Param("id") Integer id);

    Optional<Security> findBySecId(String secId);
}
