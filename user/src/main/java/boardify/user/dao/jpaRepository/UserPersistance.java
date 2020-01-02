package boardify.user.dao.jpaRepository;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
}
