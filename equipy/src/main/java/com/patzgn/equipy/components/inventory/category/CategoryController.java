package com.patzgn.equipy.components.inventory.category;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
class CategoryController {

    private CategoryService categoryService;

    @GetMapping
    List<String> findAllCategoryNames() {
        return categoryService.findAllCategoryNames();
    }

}
