package boardify.group.dao.jpaRepository;

import boardify.group.model.Stats;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "STATS")
@IdClass(Stats.class)
public class StatsPersistance implements Serializable {

    @Id
    @Column(name = "email")
    private String email;

    @Column(name = "group_id")
    private int groupId;

    @Column(name = "last_message")
    private long lastMessage;

    @Column(name = "message_count")
    private long messageCount;
}
