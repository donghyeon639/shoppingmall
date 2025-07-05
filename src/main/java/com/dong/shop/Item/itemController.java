package com.dong.shop.Item;


import com.dong.shop.comment.Comment;
import com.dong.shop.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
public class itemController {
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;
    @GetMapping("/list")
    String list(Model model){
        itemService.listItem(model);
        return "list.html";
    }
    @GetMapping("/write")
    String write(Model model){
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(@RequestParam String title, @RequestParam Integer price
            , @RequestParam String imgUrl) {
        itemService.saveItem(title, price, imgUrl);
        return "redirect:/list/page/1";
    }
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model){
        Optional<Item> result = itemRepository.findById(id);
        List<Comment> comments = commentRepository.findAllByParentId(id);
        if(result.isPresent()) {
            model.addAttribute("data",result.get());
            model.addAttribute("comments", comments);
            return "detail.html";
        }else {
            return "redirect:/list";
        }
    }
    @GetMapping("/edit/{id}")
    String edit(Model model,@PathVariable Long id){
        Optional<Item> result = itemRepository.findById(id);
        if(result.isPresent()) {
            model.addAttribute("data",result.get());
            return "edit.html";
        }else {
            return "redirect:/list";
        }
    }
    @PostMapping("/update")
    String editItem(String title, Integer price,Long id) {
        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
        return "redirect:/list";
    }
    @DeleteMapping("/item")
    ResponseEntity<String> deleteItem(@RequestParam Long id) {
        itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }
    @GetMapping("/list/page/{page}")
    public String getPage(Model model, @PathVariable Integer page) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(page - 1, 5));
        int totalPages = result.getTotalPages();
        // 페이지 번호 리스트 생성 (더 간단한 방식)
        List<Integer> pageNumbers = new ArrayList<>();
        for (int i = 1; i <= totalPages; i++) {
            pageNumbers.add(i);
        }
        model.addAttribute("items", result.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("pageNumbers", pageNumbers);
        return "list";
    }
    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPresignedUrl("test/" + filename);
        return result;
    }
    @PostMapping("/search")
    String postSearch(@RequestParam String searchText, Model model){
        var result = itemRepository.rawQuery1(searchText);
        System.out.println(result);
        model.addAttribute("items",result);
        return "search.html";
    }

}
