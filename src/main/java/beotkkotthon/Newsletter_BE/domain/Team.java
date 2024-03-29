package beotkkotthon.Newsletter_BE.domain;

import beotkkotthon.Newsletter_BE.domain.common.BaseEntity;
import beotkkotthon.Newsletter_BE.domain.mapping.MemberTeam;
import beotkkotthon.Newsletter_BE.domain.mapping.Participation;
import beotkkotthon.Newsletter_BE.payload.exception.GeneralException;
import beotkkotthon.Newsletter_BE.payload.status.ErrorStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor

@Table(indexes = {@Index(name = "team_name_idx", columnList = "name")})  // 그룹이름 검색의 성능 향상을 위해, name컬럼에 DB Indexing 적용. (인덱싱 관련해서는 나중에 리팩토링 할것.)
@Entity
public class Team extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "team_size", columnDefinition = "INT default 1")
    private Integer teamSize;  // 초기값: 그룹장 포함 1명. 차후 최대 300명 제한시킬것.

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;  // 초기값: __null__

    @Column(length = 200, unique = true)
    private String link;

    // (읽기 전용 필드) mappedBy만 사용으로, 조회 용도로만 가능. JPA는 insert나 update할 때 읽기 전용 필드를 아예 보지 않아서, 값을 넣어도 아무일도 일어나지않음.
    @OneToMany(mappedBy = "team")  // Team-News 양방향매핑
    private List<News> newsList = new ArrayList<>();

    // (읽기 전용 필드)
    @OneToMany(mappedBy = "team")  // Team-Participation 양방향매핑
    private List<Participation> participationList = new ArrayList<>();

    // (읽기 전용 필드)
    @OneToMany(mappedBy = "team")  // Team-MemberTeam 양방향매핑
    private List<MemberTeam> memberTeamList = new ArrayList<>();


    @Builder(builderClassName = "TeamSaveBuilder", builderMethodName = "TeamSaveBuilder")
    public Team(String name, String description, Integer teamSize, String imageUrl, String link) {
        // 이 빌더는 팀 생성때만 사용할 용도
        this.name = name;
        this.description = description;
        this.teamSize = teamSize;
        this.imageUrl = imageUrl;
        this.link = link;
    }


    // 수정(업데이트) 기능
    public void teamSizeUp() {
        if(this.teamSize == 300)  // 최대 300명 제한
            throw new GeneralException(ErrorStatus.BAD_REQUEST, "그룹 탈퇴 ERROR - Team의 인원수가 최대치이므로 추가할 수 없습니다.");
        this.teamSize += 1;
    }
    public void teamSizeDown() {
        if(this.teamSize <= 1)  // CREATOR는 그룹에서 탈퇴가 불가능하며, teamSize가 0이하로 내려가서도 안된다.
            throw new GeneralException(ErrorStatus.BAD_REQUEST, "그룹 탈퇴 ERROR - Team의 인원수가 최소치이므로 탈퇴할 수 없습니다.");
        this.teamSize -= 1;
    }
}
