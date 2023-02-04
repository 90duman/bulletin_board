package kz.duman.bulletin_board.payload;

import lombok.Data;

@Data
public class NewBoardRequest {
    private String name;
    private Long minPrice;
    private String description;
    private String image;
}
