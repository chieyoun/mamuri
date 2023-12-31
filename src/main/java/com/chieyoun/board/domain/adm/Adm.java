package com.chieyoun.board.domain.adm;


import com.chieyoun.board.domain.admitem.AdmItem;
import com.chieyoun.board.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Adm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="seller_id")
    private User seller; // 판매자

    @OneToMany(mappedBy = "sale")
    private List<AdmItem> saleItems = new ArrayList<>();

    private int totalCount; // 총 판매 개수

    public static Adm createSale(User seller) {
        Adm sale = new Adm();
        sale.setSeller(seller);
        sale.setTotalCount(0);
        return sale;
    }

}