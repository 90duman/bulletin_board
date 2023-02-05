package kz.duman.bulletin_board.repository;

import kz.duman.bulletin_board.model.BulletinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BulletinBoardRepository extends JpaRepository<BulletinBoard, Long> {
    @Query("SELECT bb FROM BulletinBoard bb WHERE bb.name LIKE %:name%")
    List<BulletinBoard> findByNameContaining(@Param("name") String name);

    @Query("SELECT bb FROM BulletinBoard bb WHERE bb.name = :name")
    List<BulletinBoard> findByName(@Param("name") String name);

}
