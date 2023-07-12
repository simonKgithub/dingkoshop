package com.dingkoshop.repository;

import com.dingkoshop.constant.ItemSellStatus;
import com.dingkoshop.entity.Item;
import com.dingkoshop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest //모든 빈 등록
@TestPropertySource(locations = "classpath:application-test.properties")
class ItemRepositoryTest {

    @Autowired ItemRepository itemRepository; // 빈 주입

    @PersistenceContext EntityManager em;

    @Test
    @DisplayName("Querydsl 조회 테스트2")
    public void querydslTest2(){
        this.createItemList2();

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem qItem = QItem.item;

        String itemDetail = "상세";
        int price = 10004;
        String itemSellStat = "SOLD_OUT";

        booleanBuilder.and(qItem.itemDetail.like("%" + itemDetail + "%"));
        booleanBuilder.and(qItem.price.gt(price));

        if (StringUtils.equals(itemSellStat, ItemSellStatus.SOLD_OUT)) {
            booleanBuilder.and(qItem.itemSellStatus.eq(ItemSellStatus.SOLD_OUT));
        }

        Pageable pageable = PageRequest.of(0, 5);
        Page<Item> itemPageResult = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("itemPageResult.getTotalElements() = " + itemPageResult.getTotalElements());

        List<Item> resultItemList = itemPageResult.getContent();
        for (Item item : resultItemList) {
            System.out.println("item.toString() = " + item.toString());
        }

        assertEquals(resultItemList.size(), 5);
    }

    @Test
    @DisplayName("Querydsl 조회 테스트1")
    public void querydslTest(){
        this.createItemList();
        JPAQueryFactory queryFactory = new JPAQueryFactory(em);
        QItem qItem = QItem.item;
        JPAQuery<Item> query = queryFactory.selectFrom(qItem)
                .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
                .where(qItem.itemDetail.like("%" + "6" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = query.fetch();

        assertEquals(itemList.size(), 1);
    }

    @Test
    @DisplayName("@Query(native) 상품 조회 테스트")
    public void findByItemDetailByNative(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("상세");

        assertEquals(itemList.size(), 10);
    }

    @Test
    @DisplayName("@Query 상품 조회 테스트")
    public void findByItemDetail(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("상세");

        assertEquals(itemList.size(), 10);
    }

    @Test
    @DisplayName("가격 LessThan 테스트")
    public void findByPriceLessThan(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceLessThan(10009);

        assertEquals(itemList.size(), 8);
    }

    @Test
    @DisplayName("상품명, 상품상세설명 or 조회 테스트")
    public void findByItemNmOrItemDetailTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설멍5");

        assertEquals(itemList.size(), 2);
    }

    @Test
    @DisplayName("상품명 조회 테스트")
    public void findByItemNmTest(){
        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
        for (Item item : itemList) {
            System.out.println("item = " + item.toString());
        }
    }

    @Test
    @DisplayName("상품 저장 테스트")
    public void createItemTest(){
        Item item = new Item();
        item.setItemNm("테스트 상품");
        item.setPrice(10000);
        item.setItemDetail("테스트 상품 상세 설멍");
        item.setItemSellStatus(ItemSellStatus.SELL);
        item.setStockNumber(100);
        item.setRegTime(LocalDateTime.now());
        item.setUpdateTime(LocalDateTime.now());
        Item savedItem = itemRepository.save(item);
        System.out.println("savedItem = " + savedItem.toString());
    }

    //아이템 리스트 생성
    public void createItemList(){
        for (int i = 1; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설멍" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }
    //아이템 리스트 생성2
    public void createItemList2(){
        for (int i = 1; i <= 5; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설멍" + i);
            item.setItemSellStatus(ItemSellStatus.SELL);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
        for (int i = 6; i <= 10; i++) {
            Item item = new Item();
            item.setItemNm("테스트 상품" + i);
            item.setPrice(10000 + i);
            item.setItemDetail("테스트 상품 상세 설멍" + i);
            item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
            item.setStockNumber(100);
            item.setRegTime(LocalDateTime.now());
            item.setUpdateTime(LocalDateTime.now());
            Item savedItem = itemRepository.save(item);
        }
    }



}