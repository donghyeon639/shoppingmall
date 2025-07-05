package com.dong.shop.sales;

import com.dong.shop.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class SalesController {

    private final SalesRepository salesRepository;
    private final SalesService salesService;
    private final MemberRepository memberRepository;
    @PostMapping("/order")
    public String postOrder(@RequestParam String title,
                            @RequestParam Integer price,
                            @RequestParam Integer count,
                            Authentication auth) {
        // 로그인 사용자 정보 전달
        salesService.saveSales(title, price, count, auth);
        return "redirect:/order/all"; // 성공 후 목록 페이지로 이동
    }

    @GetMapping("/order/all")
    public String getOrderAll(Model model) {
        List<Sales> result = salesRepository.customFindAll(); // member join 포함된 쿼리라면 OK
        model.addAttribute("sales", result);
        return "sales";
    }
}
