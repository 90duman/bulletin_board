package kz.duman.bulletin_board.service;

import kz.duman.bulletin_board.model.BulletinBoard;
import kz.duman.bulletin_board.payload.NewBoardRequest;

public interface BulletinBoardService {
    void addNewBoard (NewBoardRequest request, Long userId);
}
