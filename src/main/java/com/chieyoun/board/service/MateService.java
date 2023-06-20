package com.chieyoun.board.service;


import com.chieyoun.board.domain.clsitem.ClsItem;
import com.chieyoun.board.domain.item.Item;
import com.chieyoun.board.domain.mate.Mate;
import com.chieyoun.board.domain.mate.MateRepository;
import com.chieyoun.board.domain.mateitem.MateItem;
import com.chieyoun.board.domain.mateitem.MateItemRepository;
import com.chieyoun.board.domain.admitem.AdmItem;
import com.chieyoun.board.domain.admitem.AdmItemRepository;
import com.chieyoun.board.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MateService {

    private final MateRepository orderRepository;
    private final MateItemRepository orderItemRepository;
    private final UserPageService userPageService;
    private final AdmItemRepository saleItemRepository;
    private final ItemService itemService;

    // 회원가입 하면 회원 당 주문 하나 생성
    public void createOrder(User user){

        Mate mate = Mate.createOrder(user);

        orderRepository.save(mate);
    }

    // id에 해당하는 주문아이템 찾기
    public List<MateItem> findUserMateItems(int userId) {
        return orderItemRepository.findOrderItemsByUserId(userId);
    }

    // OrderItem 하나 찾기
    public MateItem findMateitem(int orderItemId) {return orderItemRepository.findOrderItemById(orderItemId);}

    // 장바구니상품주문
    @Transactional
    public MateItem addCartOrder(int itemId, int userId, ClsItem clsItem, AdmItem saleItem) {

        User user = userPageService.findUser(userId);

        MateItem orderItem = MateItem.createOrderItem(itemId, user, clsItem, saleItem);

        orderItemRepository.save(orderItem);

        return orderItem;
    }

    // 주문하면 Order 만들기
    @Transactional
    public void addOrder(User user, List<MateItem> orderItemList) {

        Mate userOrder = Mate.createOrder(user, orderItemList);

        orderRepository.save(userOrder);
    }

    // 단일 상품 주문
    @Transactional
    public void addOneItemOrder(int userId, Item item, int count, AdmItem saleItem) {

        User user = userPageService.findUser(userId);

        Mate userOrder = Mate.createOrder(user);

        MateItem orderItem = MateItem.createOrderItem(item.getId(), user, item, count, userOrder, saleItem);

        orderItemRepository.save(orderItem);
        orderRepository.save(userOrder);
    }

    // 주문 취소 기능
    @Transactional
    public void orderCancel(User user, MateItem cancelItem) {

        Item item = itemService.itemView(cancelItem.getItemId());

        // 판매자의 판매내역 totalCount 감소
        cancelItem.getAdmItem().getAdm().setTotalCount(cancelItem.getAdmItem().getAdm().getTotalCount()-cancelItem.getItemCount());

        // 해당 item 재고 다시 증가
        item.setStock(item.getStock()+ cancelItem.getItemCount());

        // 해당 item의 판매량 감소
        item.setCount(item.getCount()-cancelItem.getItemCount());

        // 판매자 돈 감소
        cancelItem.getAdmItem().getSeller().setCoin(cancelItem.getAdmItem().getSeller().getCoin()- cancelItem.getItemTotalPrice());

        // 구매자 돈 증가
        cancelItem.getUser().setCoin(cancelItem.getUser().getCoin()+ cancelItem.getItemTotalPrice());

        // 해당 orderItem의 주문 상태 1로 변경 -> 주문 취소를 의미
        cancelItem.setIsCancel(cancelItem.getIsCancel()+1);

        // 해당 orderItem.getsaleItemId 로 saleItem 찾아서 판매 상태 1로 변경 -> 판매 취소를 의미
        cancelItem.getAdmItem().setIsCancel(cancelItem.getAdmItem().getIsCancel()+1);

        orderItemRepository.save(cancelItem);
        saleItemRepository.save(cancelItem.getAdmItem());
    }


}