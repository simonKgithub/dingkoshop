package com.dingkoshop.repository;

import com.dingkoshop.dto.ItemSearchDto;
import com.dingkoshop.dto.MainItemDto;
import com.dingkoshop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
