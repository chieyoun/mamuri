package com.chieyoun.board.domain.mate;


import com.chieyoun.board.domain.mateitem.MateItem;
import com.chieyoun.board.domain.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "mates")
public class Mate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // 구매자
    @OneToMany(mappedBy = "mate")
    private List<MateItem> mateItems = new ArrayList<>();


    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 구매 날짜

    @PrePersist
    public void createDate() {
        this.createDate = LocalDate.now();
    }



    public void addMateItem(MateItem mateItem) {
        mateItems.add(mateItem);
        mateItem.setMate(this);
    }

    public static Mate createOrder(User user, List<MateItem> mateItemList) {
        Mate order = new Mate();
        order.setUser(user);
        for (MateItem orderItem : mateItemList) {
            order.addMateItem(orderItem);
        }
        order.setCreateDate(order.createDate);
        return order;
    }


    public static com.chieyoun.board.domain.mate.Mate createOrder(User user) {
        com.chieyoun.board.domain.mate.Mate order = new com.chieyoun.board.domain.mate.Mate();
        order.setUser(user);
        order.setCreateDate(order.createDate);
        return order;
    }

}