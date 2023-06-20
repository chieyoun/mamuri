package com.chieyoun.board.domain.mateitem;


import com.chieyoun.board.domain.clsitem.ClsItem;
import com.chieyoun.board.domain.clsitem.ClsItem;
import com.chieyoun.board.domain.item.Item;
import com.chieyoun.board.domain.mate.Mate;
import com.chieyoun.board.domain.mate.Mate;
import com.chieyoun.board.domain.admitem.AdmItem;
import com.chieyoun.board.domain.user.User;
import lombok.*;

import javax.persistence.*;



@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class MateItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="mate_id")
    private Mate mate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user; // 구매자

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="item_id")
//    private Item item;

    private int itemId; // 주문 상품 번호
    private String itemName; // 주문 상품 이름
    private int itemPrice; // 주문 상품 가격
    private int itemCount; // 주문 상품 수량
    private int itemTotalPrice; // 가격*수량

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="admItem_id")
    private AdmItem admItem; // 주문상품에 매핑되는 판매상품

    private int isCancel; // 주문 취소 여부 (0:주문완료 / 1:주문취소)

    // 장바구니 전체 주문
    public static MateItem createOrderItem(int itemId, User user, ClsItem clsItem, AdmItem admItem) {
        MateItem orderItem = new MateItem();
        orderItem.setItemId(itemId);
        orderItem.setUser(user);
        orderItem.setItemName(clsItem.getItem().getName());
        orderItem.setItemPrice(clsItem.getItem().getPrice());
        orderItem.setItemCount(clsItem.getCount());
        orderItem.setItemTotalPrice(clsItem.getItem().getPrice()*clsItem.getCount());
        orderItem.setAdmItem(admItem);
        return orderItem;
    }

    // 상품 개별 주문
    public static MateItem createOrderItem(int itemId, User user, Item item, int count, Mate mate, AdmItem admItem) {
        MateItem orderItem = new MateItem();
        orderItem.setItemId(itemId);
        orderItem.setUser(user);
        orderItem.setMate(mate);
        orderItem.setItemName(item.getName());
        orderItem.setItemPrice(item.getPrice());
        orderItem.setItemCount(count);
        orderItem.setItemTotalPrice(item.getPrice()*count);
        orderItem.setAdmItem(admItem);
        return orderItem;
    }

}
