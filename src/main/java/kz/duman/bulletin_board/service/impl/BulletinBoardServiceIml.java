package kz.duman.bulletin_board.service.impl;

import kz.duman.bulletin_board.exception.BadRequestException;
import kz.duman.bulletin_board.exception.NotFoundException;
import kz.duman.bulletin_board.model.BulletinBoard;
import kz.duman.bulletin_board.payload.NewBoardRequest;
import kz.duman.bulletin_board.repository.BulletinBoardRepository;
import kz.duman.bulletin_board.repository.UserRepository;
import kz.duman.bulletin_board.service.BulletinBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        var user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException("User: %d not found".formatted(userId)));
        var board = new BulletinBoard();
        board.setName(request.getName());
        board.setMinPrice(request.getMinPrice());
        board.setDescription(request.getDescription());
        board.setImage(request.getImage());
        board.setUser(user);
        board.setStatus(BulletinBoard.Status.ACTIVE);
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

    @Transactional
    @Override
    public BulletinBoard requestPurchase(Long boardId, Long userId, Long minPrice) {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime closedDateTime = dateTime.plusDays(5);
        var bulletinBoard = bulletinBoardRepository.findById(boardId).orElseThrow(() -> new NotFoundException("Bulletin board not found"));
        if (bulletinBoard.getMinPrice() >= minPrice) {
            throw new BadRequestException("The offered price cannot be lower or equal to the minimum price");
        }
        var user = userRepository.findById(bulletinBoard.getClientId()).orElse(null);
        if (user != null) {
            log.warn("Отправка email на эл.адрес: {}", user.getEmail());
            log.warn("Уважемый клиент, ваше ценовае предлажение была перебита, на сумму: {}", minPrice);
        }
        bulletinBoard.setClientId(userId);
        bulletinBoard.setClosedDateTime(closedDateTime);
        bulletinBoard.setMinPrice(minPrice);
        return bulletinBoardRepository.save(bulletinBoard);
    }
}
