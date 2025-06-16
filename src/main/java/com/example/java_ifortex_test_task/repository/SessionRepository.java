package com.example.java_ifortex_test_task.repository;

import com.example.java_ifortex_test_task.entity.DeviceType;
import com.example.java_ifortex_test_task.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {
    @Query(value = """
                    SELECT s.id,
                           s.device_type - 1 AS device_type,
                           s.ended_at_utc,
                           s.started_at_utc,
                           s.user_id
                    FROM sessions s
                    INNER JOIN users u ON s.user_id = u.id
                    WHERE started_at_utc = ( SELECT MIN(started_at_utc)
                            FROM sessions
                            WHERE device_type =:#{#deviceType.getCode()}
                            ) AND device_type =:#{#deviceType.getCode()}
        """
            , nativeQuery = true)
    Session getFirstDesktopSession(DeviceType deviceType);

    @Query(value = """
    SELECT s.id,
           s.device_type - 1 AS device_type,
           s.ended_at_utc,
           s.started_at_utc,
           s.user_id
           FROM sessions s
           JOIN users u ON u.id = s.user_id
           WHERE u.deleted = false AND s.ended_at_utc < :endDate
           ORDER BY s.started_at_utc DESC
    """, nativeQuery = true)
    List<Session> getSessionsFromActiveUsersEndedBefore2025(LocalDateTime endDate);
}