package kz.duman.bulletin_board.service;

import kz.duman.bulletin_board.model.BulletinBoard;
import kz.duman.bulletin_board.repository.BulletinBoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class BulletinBoardCorn {

    private final BulletinBoardRepository bulletinBoardRepository;

    @Scheduled(cron = "0 00 8 ? * *")
    public void runCronClosedDateTimeExpiredBulletinBoards() {
        log.info("[RUN CRON]: runCronClosedDateTimeExpiredBulletinBoards");
        var bulletinBoards = bulletinBoardRepository.closedDateTimeExpiredBulletinBoards(LocalDateTime.now());
        bulletinBoards.forEach(bullet -> {
            bullet.setStatus(BulletinBoard.Status.REMOVED_PUBLICATION);
            bulletinBoardRepository.save(bullet);
            sendingNotificationToEmail(bullet);
        });
        log.info("[END CRON]: runCronClosedDateTimeExpiredBulletinBoards");
    }

    private static void sendingNotificationToEmail(BulletinBoard bullet) {
        log.warn("Отправка уведомление email на эл.адресс: {}", bullet.getUser().getEmail());
        log.warn("Уважемый продовец аукцион на ваше обявление: {} было закрыто по истечению срока", bullet.getName());
    }
}
