package kz.duman.bulletin_board.dto;

import kz.duman.bulletin_board.model.BulletinBoard;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BulletinBoardDTO {
    private String name;
    private long minPrice;
    private String description;
    private String image;
    private UserDto user;

    public static BulletinBoardDTO from(BulletinBoard bulletinBoard) {
        return BulletinBoardDTO.builder()
                .name(bulletinBoard.getName())
                .minPrice(bulletinBoard.getMinPrice())
                .description(bulletinBoard.getDescription())
                .image(bulletinBoard.getImage())
                .user(UserDto.from(bulletinBoard.getUser()))
                .build();
    }
}
