package com.dingkoshop.service;

import com.dingkoshop.constant.ItemSellStatus;
import com.dingkoshop.dto.CartItemDto;
import com.dingkoshop.entity.CartItem;
import com.dingkoshop.entity.Item;
import com.dingkoshop.entity.Member;
import com.dingkoshop.repository.CartItemRepository;
import com.dingkoshop.repository.ItemRepository;
import com.dingkoshop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional //테스트에서 선언할 경우 테스트 종료 후 rollback 됨
@TestPropertySource(locations = "classpath:application-test.properties")
class CartServiceTest {
    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemRepository cartItemRepository;

    @Test
    @DisplayName("장바구니 담기 테스트")
    public void addCart(){
        Item item = saveItem();
        Member member = saveMember();

        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setCount(5);
        cartItemDto.setItemId(item.getId());

        Long cartItemId = cartService.addCart(cartItemDto, member.getEmail());

        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(item.getId(), cartItem.getItem().getId());
        assertEquals(cartItemDto.getCount(), cartItem.getCount());
    }

    public Item saveItem(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설명");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        return itemRepository.save(item);
    }

    public Member saveMember(){
        Member member = new Member();
        member.setEmail("test@test.com");
        return memberRepository.save(member);
    }
}