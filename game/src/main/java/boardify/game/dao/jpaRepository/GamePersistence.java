package boardify.game.dao.jpaRepository;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "game")
class GamePersistence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "minimum_number_of_players")
    private int minimumNumberOfPlayers;
    @Column(name = "maximum_number_of_players")
    private int maximumNumberOfPlayers;
    @Column(name = "suggested_age")
    private int suggestedAge;
    @Column(name = "average_playing_time")
    private int averagePlayingTime;
    @Column(name = "description")
    private String description;
}
