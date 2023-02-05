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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BulletinBoardServiceIml implements BulletinBoardService {
    private final BulletinBoardRepository bulletinBoardRepository;
    private final UserRepository userRepository;


    @Transactional
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
        log.info("Created new board: {}", board);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BulletinBoard> getAllAbs() {
        log.info("Get a list of ads");
        return bulletinBoardRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<BulletinBoard> findByNameContaining(String name) {
        return bulletinBoardRepository.findByNameContaining(name);
    }
}
