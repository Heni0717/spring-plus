package org.example.expert.domain.todo.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.todo.dto.request.TodoSearchRequest;
import org.example.expert.domain.todo.dto.response.TodoSearchResponse;
import org.example.expert.domain.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.example.expert.domain.todo.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Todo> findByIdWithUser(Long todoId) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(todo)
                .where(todo.id.eq(todoId))
                .leftJoin(todo.user)
                .fetchJoin()
                .fetchOne());
    }

    @Override
    public Page<TodoSearchResponse> searchTodos(TodoSearchRequest request, Pageable pageable) {
        List<TodoSearchResponse> todos = jpaQueryFactory
                .select(Projections.constructor(
                        TodoSearchResponse.class,
                        todo.title,
                        todo.managers.size(),
                        todo.comments.size()
                        )
                )
                .from(todo)
                .leftJoin(todo.managers)
                .leftJoin(todo.comments)
                .where(containsTitle(request.getTitle()),
                        betweenCreatedAt(request.getStartAt(), request.getEndAt()),
                        containsNickname(request.getManagerNickname())
                ).orderBy(todo.createdAt.desc())
                .fetch();

        long counts = Optional.ofNullable(jpaQueryFactory
                .select(todo.count())
                .from(todo)
                .where(
                        containsTitle(request.getTitle()),
                        betweenCreatedAt(request.getStartAt(), request.getEndAt()),
                        containsNickname(request.getManagerNickname())
                )
                .fetchOne()
        ).orElse(0L);
        return new PageImpl<>(todos, pageable, counts);
    }

    private BooleanExpression containsTitle(String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }
        return todo.title.containsIgnoreCase(title);
    }

    private BooleanExpression betweenCreatedAt(LocalDateTime startAt, LocalDateTime endAt) {
        if (startAt == null && endAt == null) {
            return null;
        }
        LocalDateTime from = (startAt != null) ? startAt : LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime to = (endAt != null) ? endAt : LocalDateTime.now();
        return todo.createdAt.between(from, to);
    }

    private BooleanExpression containsNickname(String nickname) {
        if (nickname == null || nickname.isEmpty()) {
            return null;
        }
        return todo.user.nickname.containsIgnoreCase(nickname);
    }

}
