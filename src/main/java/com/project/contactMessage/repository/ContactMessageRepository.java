package com.project.contactMessage.repository;

import com.project.contactMessage.dto.ContactMessageResponse;
import com.project.contactMessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage, Long> {

    Page<ContactMessage> findByEmailEquals(String email, Pageable pageable);

    Page<ContactMessage> findBySubjectEquals(String subject, Pageable pageable);

    @Query("SELECT c FROM ContactMessage c WHERE FUNCTION('DATE', c.dateTime) BETWEEN ?1 AND ?2" )
    List<ContactMessage> findMessagesBetweenDates(LocalDate begindate, LocalDate endDate);

    @Query(" SELECT c FROM ContactMessage c WHERE " +
            "(EXTRACT (HOUR FROM c.dateTime) BETWEEN :startHour AND : endHour ) AND " +
            "(EXTRACT (HOUR FROM c.dateTime) != :startHour OR EXTRACT(MINUTE FROM c.dateTime) >= :startMinute) AND " +
            "(EXTRACT (HOUR FROM c.dateTime) != :endHour OR EXTRACT(MINUTE FROM c.dateTime) <= :endMinute)")
    List<ContactMessage> findMessagesBetweenTimes(@Param("startHour") int startHour,
                                                  @Param("startMinute") int startMinute,
                                                  @Param("endHour") int endHour,
                                                  @Param("endMinute") int endMinute);

}
