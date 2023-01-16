package com.patzgn.foodieapp;


import com.patzgn.foodieapp.item.Item;
import com.patzgn.foodieapp.item.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomeController {

    private final ItemRepository itemRepository;

    @GetMapping("/")
    String home(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "index";
    }

}
