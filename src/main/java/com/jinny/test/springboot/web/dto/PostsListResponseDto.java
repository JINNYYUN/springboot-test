package com.jinny.test.springboot.web.dto;

import com.jinny.test.springboot.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PostsListResponseDto {
    private Long id;
    private String title;
    private String author;
    private LocalDateTime modifiedDate;

    public PostsListResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.author = entity.getAuthor();
        this.modifiedDate = entity.getModifiedDate();
    }
}

