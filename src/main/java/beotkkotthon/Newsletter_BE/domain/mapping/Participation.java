package beotkkotthon.Newsletter_BE.domain.mapping;

import beotkkotthon.Newsletter_BE.domain.Member;
import beotkkotthon.Newsletter_BE.domain.Team;
import beotkkotthon.Newsletter_BE.domain.common.BaseEntity;
import beotkkotthon.Newsletter_BE.domain.enums.RequestRole;
import beotkkotthon.Newsletter_BE.domain.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor

@Table(name = "participation")
@Entity
public class Participation extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_role")
    @Enumerated(EnumType.STRING)
    private RequestRole requestRole;

    @ManyToOne(fetch = FetchType.LAZY)  // Member-Participation 단방향매핑 (Member에서 Participation를 조회할 경우가 딱히 없기 때문.)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)  // Team-Participation 양방향매핑 (Team에서 Participation를 조회하기 때문.)
    @JoinColumn(name = "team_id")
    private Team team;


    @Builder(builderClassName = "ParticipationSaveBuilder", builderMethodName = "ParticipationSaveBuilder")
    public Participation(RequestRole requestRole, Member member, Team team) {
        // 이 빌더는 참여신청 생성때만 사용할 용도
        this.requestRole = requestRole;
        this.member = member;
        this.team = team;
    }
}
