package com.chieyoun.board.service;


import com.chieyoun.board.domain.adm.Adm;
import com.chieyoun.board.domain.mate.Mate;
import com.chieyoun.board.domain.user.User;
import com.chieyoun.board.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ClsService cartService;
//    private final OrderService orderService;
//    private final SaleService saleService;

    @Transactional
    public User signup(User user) {

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setRole(user.getRole());

        User userEntity = userRepository.save(user);

        if (Objects.equals(userEntity.getRole(), "ROLE_SELLER")) {
            Adm.createSale(user);
        } else if (Objects.equals(user.getRole(), "ROLE_USER")){
            cartService.createCart(user);
            Mate.createOrder(user);
        }

        return userEntity;
    }
}
