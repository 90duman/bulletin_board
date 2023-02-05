package kz.duman.bulletin_board.repository;

import kz.duman.bulletin_board.model.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {
    @Query("SELECT bb FROM BulletinBoard bb WHERE bb.name LIKE %:name%")
    List<BulletinBoard> findByNameContaining(@Param("name") String name);

    @Query("SELECT bb FROM BulletinBoard bb WHERE bb.name = :name")
    List<BulletinBoard> findByName(@Param("name") String name);

    @Query("SELECT b FROM BulletinBoard b WHERE b.closedDateTime < :currentDateTime AND b.status='ACTIVE'")
    List<BulletinBoard> closedDateTimeExpiredBulletinBoards(LocalDateTime currentDateTime);

    Optional<BulletinBoard> findByIdAndStatus(Long id, BulletinBoard.Status status);
}
