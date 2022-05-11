package com.example.springboot.service;

import com.example.springboot.repository.ItemRepository;
import com.example.springboot.model.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    // 아이템 저장
    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    // 리스트 조회
    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    // 단건 조회
    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
