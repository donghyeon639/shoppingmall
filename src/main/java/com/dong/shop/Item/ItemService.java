package com.dong.shop.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;


    public void saveItem(String title, Integer price,String imgUrl) {
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setImageUrl(imgUrl);
        itemRepository.save(item);
    }
    public void listItem(Model model){
        List<Item> result = itemRepository.findAll();
        model.addAttribute("items",result);
    }
}
