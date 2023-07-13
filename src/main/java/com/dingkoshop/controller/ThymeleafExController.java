package com.dingkoshop.controller;

import com.dingkoshop.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/thymeleaf")
public class ThymeleafExController {

    @GetMapping("/ex08")
    public String thymeleafExample08() {
        return "thymeleafEx/thymeleafEx08";
    }

    @GetMapping("/ex07")
    public String thymeleafExample07(String param1, String param2, Model model) {
        System.out.println("p1 = " + param1);
        System.out.println("p2 = " + param2);
        model.addAttribute("p1", param1);
        model.addAttribute("p2", param2);
        return "thymeleafEx/thymeleafEx07";
    }

    @GetMapping("/ex06")
    public String thymeleafExample06(Model model) {
        return "thymeleafEx/thymeleafEx06";
    }

    @GetMapping("/ex05")
    public String thymeleafExample05(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setPrice(10000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }


        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx05";
    }

    @GetMapping("/ex04")
    public String thymeleafExample04(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setPrice(10000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }


        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx04";
    }

    @GetMapping("/ex03")
    public String thymeleafExample03(Model model) {

        List<ItemDto> itemDtoList = new ArrayList<>();

        for (int i = 1; i <= 10; i++) {
            ItemDto itemDto = new ItemDto();
            itemDto.setItemNm("테스트 상품" + i);
            itemDto.setItemDetail("상품 상세 설명" + i);
            itemDto.setPrice(10000*i);
            itemDto.setRegTime(LocalDateTime.now());

            itemDtoList.add(itemDto);
        }


        model.addAttribute("itemDtoList", itemDtoList);
        return "thymeleafEx/thymeleafEx03";
    }

    @GetMapping("/ex02")
    public String thymeleafExample02(Model model) {
        ItemDto itemDto = new ItemDto();
        itemDto.setItemNm("테스트 상품1");
        itemDto.setItemDetail("상품 상세 설명");
        itemDto.setPrice(10000);
        itemDto.setRegTime(LocalDateTime.now());

        model.addAttribute("itemDto", itemDto);
        return "thymeleafEx/thymeleafEx02";
    }

    @GetMapping("/ex01")
    public String thymeleafExample01(Model model) {
        model.addAttribute("data", "타임리프 예제입니다.");
        return "thymeleafEx/thymeleafEx01";
    }
}
