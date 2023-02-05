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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
        board.setUser(user);
        board.setStatus(BulletinBoard.Status.ACTIVE);
        bulletinBoardRepository.save(board);
        log.info("Created new board: {}", board);
    }

    @Override
    public void addImagesByAbsId(MultipartFile multipartFile, Long id) {
        try {
            var bulletinBoard = bulletinBoardRepository.findByIdAndStatus(id, BulletinBoard.Status.ACTIVE).orElseThrow(() ->
                    new NotFoundException("Abs Id: %d not found".formatted(id)));
            bulletinBoard.setImage(multipartFile.getBytes());
            bulletinBoardRepository.save(bulletinBoard);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        var bulletinBoard = bulletinBoardRepository.findByIdAndStatus(boardId, BulletinBoard.Status.ACTIVE).orElseThrow(() -> new NotFoundException("Bulletin board not found"));
        if (bulletinBoard.getMinPrice() >= minPrice) {
            throw new BadRequestException("The offered price cannot be lower or equal to the minimum price");
        }
        var board = bulletinBoardRepository.findById(boardId).orElse(null);
        sendingNotificationToEmail(minPrice, board);
        bulletinBoard.setClientId(userId);
        bulletinBoard.setClosedDateTime(closedDateTime);
        bulletinBoard.setMinPrice(minPrice);
        return bulletinBoardRepository.save(bulletinBoard);
    }

    private static void sendingNotificationToEmail(Long minPrice, BulletinBoard board) {
        if (board != null && board.getClosedDateTime() != null) {
            log.warn("Отправка email на эл.адрес: {}", board.getUser().getEmail());
            log.warn("Уважемый клиент, ваше ценовае предлажение была перебита, на сумму: {}", minPrice);
        }
    }
}
