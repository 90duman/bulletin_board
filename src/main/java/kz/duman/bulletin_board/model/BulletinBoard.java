package kz.duman.bulletin_board.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "bulletin_boards")
@Getter
@Setter
public class BulletinBoard extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "min_price", nullable = false)
    private Long minPrice;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        ACTIVE, REMOVED_PUBLICATION
    }
}
