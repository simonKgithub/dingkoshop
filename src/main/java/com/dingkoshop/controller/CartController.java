package com.dingkoshop.controller;

import com.dingkoshop.dto.CartDetailDto;
import com.dingkoshop.dto.CartItemDto;
import com.dingkoshop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @DeleteMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody
    ResponseEntity deleteCartItem(
            @PathVariable("cartItemId") Long cartItemId, Principal principal) {

        if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.deleteCartItem(cartItemId);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @PatchMapping(value = "/cartItem/{cartItemId}")
    public @ResponseBody
    ResponseEntity updateCartItem(
            @PathVariable("cartItemId") Long cartItemId,
            int count, Principal principal) {

        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요.", HttpStatus.BAD_REQUEST);
        } else if (!cartService.validateCartItem(cartItemId, principal.getName())) {
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartItemCount(cartItemId, count);

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHis(Principal principal, Model model) {
        List<CartDetailDto> cartDetailList = cartService.getCartList(principal.getName());
        model.addAttribute("cartItems", cartDetailList);
        return "cart/cartList";

    }

    @PostMapping(value = "/cart")
    public @ResponseBody
    ResponseEntity order(
            @RequestBody @Valid CartItemDto cartItemDto,
            BindingResult bindingResult,
            Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }

        String email = principal.getName();
        Long cartItemId;

        try {
            cartItemId = cartService.addCart(cartItemDto, email);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(cartItemId, HttpStatus.OK);
    }
}
