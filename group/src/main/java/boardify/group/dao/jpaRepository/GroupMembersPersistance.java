package boardify.group.dao.jpaRepository;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GROUP_MEMBERS")
public
class GroupMembersPersistance {

    @Id
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Column(name = "GAME_GROUP_ID")
    private int gameGroupID;
}
