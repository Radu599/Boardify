package boardify.user.dao.jpaRepository;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@javax.persistence.Entity
@Table(name = "USERS")
class UserPersistance {

    @Id
    @Column(name = "USER_ID")
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "ROLE_ID")
    private String roleId = "2";

    @Column(name = "AVATAR_PATH")
    private String avatarPath;
}
