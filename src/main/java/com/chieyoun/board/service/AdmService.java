package com.chieyoun.board.service;


import com.chieyoun.board.domain.adm.Adm;
import com.chieyoun.board.domain.clsitem.ClsItem;
import com.chieyoun.board.domain.item.Item;
import com.chieyoun.board.domain.item.ItemRepository;
import com.chieyoun.board.domain.adm.AdmRepository;
import com.chieyoun.board.domain.admitem.AdmItem;
import com.chieyoun.board.domain.admitem.AdmItemRepository;
import com.chieyoun.board.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdmService {

    private final AdmRepository admRepository;
    private final UserPageService userPageService;
    private final AdmItemRepository saleItemRepository;
    private final ItemRepository itemRepository;

    // 회원가입 하면 판매자 당 판매내역 하나 생성
    public void createSale (User user){

        Adm adm = Adm.createSale(user);

        admRepository.save(adm);
    }

    // id에 해당하는 판매아이템 찾기
    public List<AdmItem> findSellerSaleItems (int sellerId) {

        return saleItemRepository.findSaleItemsBySellerId(sellerId);
    }

    // 판매자 id에 해당하는 Sale 찾기
    public Adm findSaleById (int sellerId) { return admRepository.findBySellerId(sellerId); }

    // 판매내역에 저장 (장바구니 전체 주문)
    @Transactional
    public AdmItem addSale (int itemId, int sellerId, ClsItem clsItem) {

        User seller = userPageService.findUser(sellerId);
        Adm adm = admRepository.findBySellerId(sellerId);
        adm.setTotalCount(adm.getTotalCount()+clsItem.getCount());
        admRepository.save(adm);
        AdmItem saleItem = AdmItem.createSaleItem(itemId, adm, seller, clsItem);
        saleItemRepository.save(saleItem);

        return saleItem;
    }

    // 판매내역에 저장 (상품 개별 주문)
    @Transactional
    public AdmItem addSale (int sellerId, Item item, int count) {

        User seller = userPageService.findUser(sellerId);
        Adm sale = admRepository.findBySellerId(sellerId);
        sale.setTotalCount(sale.getTotalCount()+count);
        admRepository.save(sale);
        AdmItem saleItem = AdmItem.createSaleItem(item.getId(), sale, seller, item, count);
        saleItemRepository.save(saleItem);

        return saleItem;
    }

}