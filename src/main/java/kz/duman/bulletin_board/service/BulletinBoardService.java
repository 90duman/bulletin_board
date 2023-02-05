package kz.duman.bulletin_board.service;

import kz.duman.bulletin_board.model.BulletinBoard;
import kz.duman.bulletin_board.payload.NewBoardRequest;

import java.util.List;

public interface BulletinBoardService {
    void addNewBoard(NewBoardRequest request, Long userId);

    List<BulletinBoard> getAllAbs();
}
