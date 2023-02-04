package kz.duman.bulletin_board.service.impl;

import kz.duman.bulletin_board.model.BulletinBoard;
import kz.duman.bulletin_board.model.Status;
import kz.duman.bulletin_board.payload.NewBoardRequest;
import kz.duman.bulletin_board.repository.BulletinBoardRepository;
import kz.duman.bulletin_board.repository.UserRepository;
import kz.duman.bulletin_board.service.BulletinBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BulletinBoardServiceIml implements BulletinBoardService {
    private final BulletinBoardRepository bulletinBoardRepository;
    private final UserRepository userRepository;


    @Override
    public void addNewBoard(NewBoardRequest request, Long userId) {
        var user = userRepository.findById(userId).get();
        var board = new BulletinBoard();
        board.setName(request.getName());
        board.setMinPrice(request.getMinPrice());
        board.setDescription(request.getDescription());
        board.setImage(request.getImage());
        board.setUser(user);
        board.setStatus(Status.ACTIVE);
        bulletinBoardRepository.save(board);
    }
}
