package com.patzgn.equipy.components.assignment;

import com.patzgn.equipy.components.inventory.asset.Asset;
import com.patzgn.equipy.components.user.User;

class AssignmentMapper {

    static AssignmentDto toDto(Assignment assignment) {
        AssignmentDto dto = new AssignmentDto();
        dto.setId(assignment.getId());
        dto.setStart(assignment.getStart());
        dto.setEnd(assignment.getEnds());
        User user = assignment.getUser();
        dto.setUserId(user.getId());
        Asset asset = assignment.getAsset();
        dto.setAssetId(asset.getId());
        return dto;
    }

}
