package kz.duman.bulletin_board.service;

import kz.duman.bulletin_board.model.BulletinBoard;
import kz.duman.bulletin_board.payload.NewBoardRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BulletinBoardService {
    void addNewBoard(NewBoardRequest request, Long userId);
    void addImagesByAbsId(MultipartFile multipartFile, Long id);

    List<BulletinBoard> getAllAbs();

    List<BulletinBoard> findByNameContaining(String name);

    BulletinBoard requestPurchase(Long boardId, Long userId, Long minPrice);
}
