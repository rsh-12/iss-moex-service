package ru.task.iss.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.task.iss.dto.SecurityHistoryDto;
import ru.task.iss.entity.Security;

import java.time.LocalDate;
import java.util.Optional;

/* Because of the name of the ID field, I had to override the query methods */
public interface SecurityRepository extends JpaRepository<Security, Integer> {

    @Query("from Security s where s.id=:id")
    @Override
    Optional<Security> findById(@Param("id") Integer id);

    void deleteBySecId(String secId);

    @Override
    @Modifying
    @Query(nativeQuery = true, value = "delete from securities s where s.id = :id")
    void deleteById(@Param("id") Integer id);

    @Override
    @Query("select case when count(s) > 0 then true else false end from Security s where s.id = :id")
    boolean existsById(@Param("id") Integer id);

    Optional<Security> findBySecId(String secId);

    @Query("from Security s where lower(s.emitentTitle) like lower(concat('%', :emitentTitle, '%'))")
    Page<Security> findAll(Pageable pageable, @Param("emitentTitle") String emitentTitle);

    boolean existsBySecId(String secId);

    @Query("select NEW ru.task.iss.dto.SecurityHistoryDto(s.secId, s.regnumber, s.name, s.emitentTitle, " +
            "h.tradeDate, h.numTrades, h.open, h.close) " +
            "from Security s left join History h on s.secId=h.secId " +
            "where lower(s.emitentTitle) like lower(concat('%', :title, '%')) or h.tradeDate = :date")
    Page<SecurityHistoryDto> findFields(Pageable pageable);

    @Query("select NEW ru.task.iss.dto.SecurityHistoryDto(s.secId, s.regnumber, s.name, s.emitentTitle, " +
            "h.tradeDate, h.numTrades, h.open, h.close) " +
            "from Security s left join History h on s.secId=h.secId " +
            "where lower(s.emitentTitle) like lower(concat('%', :title, '%')) or h.tradeDate = :date")
    Page<SecurityHistoryDto> findFields(Pageable pageable,
                                                @Param("title") String emitentTitle,
                                                @Param("date") LocalDate tradeDate);
}
