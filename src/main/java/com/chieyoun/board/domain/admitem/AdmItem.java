package com.chieyoun.board.domain.admitem;

import com.chieyoun.board.domain.clsitem.ClsItem;
import com.chieyoun.board.domain.item.Item;
import com.chieyoun.board.domain.mateitem.MateItem;
import com.chieyoun.board.domain.adm.Adm;
import com.chieyoun.board.domain.user.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AdmItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="sale_id")
    private Adm adm;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private User seller; // 판매자

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="itemId")
//    private Item item;

    private int itemId; // 주문 상품 번호
    private String itemName; // 주문 상품 이름
    private int itemPrice; // 주문 상품 가격
    private int itemCount; // 주문 상품 수량
    private int itemTotalPrice; // 가격*수량

    @OneToOne(mappedBy = "AdmItem")
    private MateItem MateItem; // 판매 상품에 매핑되는 주문 상품

    private int isCancel; // 판매 취소 여부 (0:판매완료 / 1:판매취소)

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate createDate; // 날짜

    @PrePersist
    public void createDate(){
        this.createDate = LocalDate.now();
    }

    // 장바구니 전체 주문
    public static AdmItem createSaleItem(int itemId, Adm adm, User seller, ClsItem clsItem) {
        AdmItem saleItem = new AdmItem();
        saleItem.setItemId(itemId);
        saleItem.setAdm(adm);
        saleItem.setSeller(seller);
        saleItem.setItemName(clsItem.getItem().getName());
        saleItem.setItemPrice(clsItem.getItem().getPrice());
        saleItem.setItemCount(clsItem.getCount());
        saleItem.setItemTotalPrice(clsItem.getItem().getPrice()*clsItem.getCount());
        return saleItem;
    }

    // 상품 개별 주문
    public static AdmItem createSaleItem(int itemId, Adm adm, User seller, Item item, int count) {
        AdmItem saleItem = new AdmItem();
        saleItem.setItemId(itemId);
        saleItem.setAdm(adm);
        saleItem.setSeller(seller);
        saleItem.setItemName(item.getName());
        saleItem.setItemPrice(item.getPrice());
        saleItem.setItemCount(count);
        saleItem.setItemTotalPrice(item.getPrice()*count);
        return saleItem;
    }
}
