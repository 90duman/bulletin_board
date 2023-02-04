package kz.duman.bulletin_board.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}
