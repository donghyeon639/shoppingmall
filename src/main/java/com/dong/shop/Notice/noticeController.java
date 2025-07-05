package com.dong.shop.Notice;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class noticeController {
    private final NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public String list(Model model) {
        List<Notice> notices = noticeRepository.findAll();
        System.out.println(notices.get(0).title);// 모든 공지사항 조회
        model.addAttribute("notices", notices);

        return "notice.html";  // 뷰 파일 반환
    }
}

