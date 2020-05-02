package boardify.auth.dao.impl.jpaRepository;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "USERS")
public class BoardifyUserPersistance {

    @Id
    @Column(name = "USER_ID")
    private String username;

    @Column(name = "PASSWORD")
    private String password;

    @OneToOne
    @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
    private RolePersistence role;
}
