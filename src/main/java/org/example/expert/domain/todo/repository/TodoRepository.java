package org.example.expert.domain.todo.repository;

import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryQuery {

    @Query("SELECT t FROM Todo t LEFT JOIN FETCH t.user u " +
            "WHERE (:weather IS NULL OR t.weather = :weather) " +
            "AND (:startAt IS NULL OR t.modifiedAt >= :startAt) " +
            "AND (:endAt IS NULL OR t.modifiedAt <= :endAt) " +
            "ORDER BY t.modifiedAt DESC")
    // JPQL을 사용했기에 좀더 간결한 메소드명으로 변경할수 있긴 함(추후에 고민)
    Page<Todo> findAllByWeatherAndModifiedAtRange(
            // 바인딩할 값 명시(매핑 오류 방지?)
            @Param("weather") String weather,
            @Param("startAt") LocalDateTime startAt,
            @Param("endAt") LocalDateTime endAt,
            Pageable pageable);

}
