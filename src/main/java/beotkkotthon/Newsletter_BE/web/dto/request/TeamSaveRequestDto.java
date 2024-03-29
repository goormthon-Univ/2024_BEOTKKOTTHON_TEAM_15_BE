package beotkkotthon.Newsletter_BE.web.dto.request;

import beotkkotthon.Newsletter_BE.domain.Team;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
public class TeamSaveRequestDto {

    private String name;
    private String description;

    @Builder
    public TeamSaveRequestDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Team toEntity(String imageUrl, String link) {
        return Team.TeamSaveBuilder()
                .name(name)
                .description(description)
                .teamSize(1)
                .imageUrl(imageUrl)
                .link(link)
                .build();
    }
}
