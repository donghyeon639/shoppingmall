package com.dong.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    String signup() {
        return "signup.html";
    }
    @PostMapping("/member")
    public String addMember(
            String username, String password, String displayName) {
        Member member = new Member();
        member.setUsername(username);
        var hash =  passwordEncoder.encode(password);
        member.setPassword(hash);
        member.setDisplayName(displayName);
        memberRepository.save(member);

        return "redirect:/list";
    }
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
    public String myPage(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }
        return "main.html";
    }
    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        var data = new MemberDto(result.getUsername(),result.getDisplayName());
        return data;
    }
    class MemberDto{
        public String username;
        public String displayName;
        MemberDto(String a, String b){
            this.username =a;
            this.displayName = b;
        }
    }

}
