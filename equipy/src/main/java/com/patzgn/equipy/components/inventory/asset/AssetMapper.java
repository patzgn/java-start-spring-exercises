package com.patzgn.equipy.components.inventory.asset;

import com.patzgn.equipy.components.inventory.category.Category;
import com.patzgn.equipy.components.inventory.category.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
class AssetMapper {

    private CategoryRepository categoryRepository;

    AssetDto toDto(Asset asset) {
        AssetDto dto = new AssetDto();
        dto.setId(asset.getId());
        dto.setName(asset.getName());
        dto.setDescription(asset.getDescription());
        dto.setSerialNumber(asset.getSerialNumber());
        if (asset.getCategory() != null) {
            dto.setCategory(asset.getCategory().getName());
        }
        return dto;
    }

    Asset toEntity(AssetDto asset) {
        Asset entity = new Asset();
        entity.setId(asset.getId());
        entity.setName(asset.getName());
        entity.setDescription(asset.getDescription());
        entity.setSerialNumber(asset.getSerialNumber());
        Optional<Category> category = categoryRepository.findByName(asset.getName());
        category.ifPresent(entity::setCategory);
        return entity;
    }

}
