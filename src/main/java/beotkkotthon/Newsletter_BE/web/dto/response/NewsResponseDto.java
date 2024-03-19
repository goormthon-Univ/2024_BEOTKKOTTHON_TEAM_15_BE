package beotkkotthon.Newsletter_BE.web.dto.response;

import beotkkotthon.Newsletter_BE.domain.News;
import beotkkotthon.Newsletter_BE.domain.Team;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NewsResponseDto {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime limitTime;
    private String imageUrl1;
    private String imageUrl2;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;

    public NewsResponseDto(News entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.limitTime = entity.getLimitTime();
        this.imageUrl1 = entity.getImageUrl1();
        this.imageUrl2 = entity.getImageUrl2();
        this.createdTime = entity.getCreatedTime();
        this.modifiedTime = entity.getModifiedTime();
    }
}
