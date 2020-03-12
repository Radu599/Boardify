package boardify.game.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Game {

    private int id;
    private String name;
    private int minimumNumberOfPlayers;
    private int maximumNumberOfPlayers;
    private int suggestedAge;
    private int averagePlayingTime;
    private String description;
    private String imageLink;
}
