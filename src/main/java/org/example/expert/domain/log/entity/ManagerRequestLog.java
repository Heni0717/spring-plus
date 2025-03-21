package org.example.expert.domain.log.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;

@Entity
@Table(name = "log")
@Getter
@NoArgsConstructor
public class ManagerRequestLog extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String errorMessage;

    public ManagerRequestLog(Long userId, String errorMessage) {
        this.userId = userId;
        this.errorMessage = errorMessage;
    }

}
