package boardify.group.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class GameGroup {

    int id;
    int gameId;
    String city;

    public GameGroup(int gameId) {
        this.gameId = gameId;
    }

    public GameGroup(int gameId, String city) {
        this.gameId = gameId;
        this.city = city;
    }
}
