package com.patzgn.foodieapp.item;

import com.patzgn.foodieapp.item.Item;
import com.patzgn.foodieapp.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping("/danie/{name}")
    String item(@PathVariable String name,
                Model model) {
        Optional<Item> item = itemRepository.findByNameIgnoreCase(name.replaceAll("-", " "));
        item.ifPresent(it -> model.addAttribute("item", it));
        return item.map(it -> "item").orElse("redirect:/");
    }

}
