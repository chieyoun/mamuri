package com.chieyoun.board.domain.clsitem;


import com.chieyoun.board.domain.joiningclass.JoiningClass;
import com.chieyoun.board.domain.item.Item;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ClsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cart_id")
    private JoiningClass cart;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="item_id")
    private Item item;

    private int count; // 상품 개수

    public static ClsItem createCartItem(JoiningClass cart, Item item, int amount) {
        ClsItem cartItem = new ClsItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(amount);
        return cartItem;
    }

    // 이미 담겨있는 물건 또 담을 경우 수량 증가
    public void addCount(int count) {
        this.count += count;
    }

}
