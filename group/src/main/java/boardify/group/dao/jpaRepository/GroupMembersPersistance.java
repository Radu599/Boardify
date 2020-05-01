package boardify.group.dao.jpaRepository;

import boardify.group.model.GroupMember;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GROUP_MEMBERS")
@IdClass(GroupMember.class)
public class GroupMembersPersistance implements Serializable {

    @Id
    @Column(name = "USER_EMAIL")
    private String userEmail;
    @Id
    @Column(name = "GAME_GROUP_ID")
    private int gameGroupID;
}
