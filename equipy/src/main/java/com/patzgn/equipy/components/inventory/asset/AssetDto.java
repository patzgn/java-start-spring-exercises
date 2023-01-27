package com.patzgn.equipy.components.inventory.asset;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class AssetDto {

    private Long id;
    private String name;
    private String description;
    private String serialNumber;
    private String category;

}
