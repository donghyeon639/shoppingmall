package com.dong.shop.sales;

import com.dong.shop.Member.CustomUser;
import com.dong.shop.Member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;

    public void saveSales(String itemName, Integer price,
                          Integer count, Authentication auth) {
        Sales sales = new Sales();
        sales.setItemName(itemName);
        sales.setPrice(price);
        sales.setCount(count);

        // 로그인한 사용자 정보에서 Member 추출
        CustomUser user = (CustomUser) auth.getPrincipal();
        Member member = new Member();
        member.setId(user.id); // 비영속 상태이지만 ID는 담겨 있음

        sales.setMember(member); // Member 설정
        salesRepository.save(sales);
    }


    public void listItem(Model model) {
        List<Sales> result = salesRepository.findAll();
        model.addAttribute("sales", result);
    }
}
