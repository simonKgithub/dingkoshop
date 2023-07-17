package com.dingkoshop.controller;

import com.dingkoshop.dto.MemberFormDto;
import com.dingkoshop.entity.Member;
import com.dingkoshop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/new")
    public String memberForm(@Valid MemberFormDto memberFormDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(memberFormDto, passwordEncoder);
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    @GetMapping("/new")
    public String memberForm(Model model) {
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }
}
