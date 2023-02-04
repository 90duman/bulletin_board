package kz.duman.bulletin_board.service;

import kz.duman.bulletin_board.dto.AuthRequestDto;
import kz.duman.bulletin_board.model.User;
import kz.duman.bulletin_board.payload.NewUserRequest;

import java.util.List;
import java.util.Map;

public interface UserService {
    User registry(NewUserRequest request);

    User findByEmail(String email);

    void delete(Long id);
}
