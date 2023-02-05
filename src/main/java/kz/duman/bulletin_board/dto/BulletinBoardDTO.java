package kz.duman.bulletin_board.dto;

import kz.duman.bulletin_board.model.BulletinBoard;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BulletinBoardDTO {
    private String name;
    private long minPrice;
    private String description;
    private String image;
    private BulletinBoard.Status status;
    private LocalDateTime closedDateTime;
    private Long clientId;
    private UserDto user;

    public static BulletinBoardDTO from(BulletinBoard bulletinBoard) {
        return BulletinBoardDTO.builder()
                .name(bulletinBoard.getName())
                .minPrice(bulletinBoard.getMinPrice())
                .description(bulletinBoard.getDescription())
                .image(bulletinBoard.getImage())
                .status(bulletinBoard.getStatus())
                .closedDateTime(bulletinBoard.getClosedDateTime())
                .clientId(bulletinBoard.getClientId())
                .user(UserDto.from(bulletinBoard.getUser()))
                .build();
    }
}
