package com.chieyoun.board.controller;


import com.chieyoun.board.config.auth.PrincipalDetails;
import com.chieyoun.board.domain.item.Item;
import com.chieyoun.board.service.ItemService;
import com.chieyoun.board.service.UserPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

// 판매자에 해당하는 페이지 관리
// 판매자페이지, 상품관리, 판매내역

@RequiredArgsConstructor
@Controller
public class AdmPageController {

    private final UserPageService userPageService;
    private final ItemService itemService;

    // 판매자 프로필 페이지 접속
    @GetMapping("/seller/{id}")
    public String sellerPage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.getUser().getId() == id) {
            // 로그인이 되어있는 판매자의 id와 판매자 페이지에 접속하는 id가 같아야 함
            model.addAttribute("user", userPageService.findUser(id));

            return "/seller/sellerPage";
        } else {
            return "redirect:/main";
        }

    }

    // 상품 관리 페이지
    @GetMapping("/seller/manage/{id}")
    public String itemManage(@PathVariable("id") Integer id, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {

        if(principalDetails.getUser().getId() == id) {
            // 로그인이 되어있는 판매자의 id와 상품관리 페이지에 접속하는 id가 같아야 함
            List<Item> allItem = itemService.allItemView();
            List<Item> userItem = new ArrayList<>();

            // 자신이 올린 상품만 가져오기
            for(Item item : allItem) {
                if(item.getSeller().getId() == id) {
                    userItem.add(item);
                }
            }

            model.addAttribute("seller", userPageService.findUser(id));
            model.addAttribute("userItem", userItem);

            return "seller/itemManage";
        } else {
            return "redirect:/main";
        }
    }


}
