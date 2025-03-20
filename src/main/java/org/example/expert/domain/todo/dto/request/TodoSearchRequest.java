package org.example.expert.domain.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoSearchRequest {

    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endAt;

    private String managerNickname;
}
