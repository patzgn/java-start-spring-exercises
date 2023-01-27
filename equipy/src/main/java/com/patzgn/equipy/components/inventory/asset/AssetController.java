package com.patzgn.equipy.components.inventory.asset;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/assets")
@AllArgsConstructor
class AssetController {

    private AssetService assetService;

    @GetMapping
    List<AssetDto> findAllAssets(@RequestParam(required = false) String text) {
        if (text != null) {
            return assetService.findAllAssetsByNameOrSerialNumber(text);
        } else {
            return assetService.findAllAssets();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<AssetDto> findAssetById(@PathVariable Long id) {
        return assetService.findAssetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    ResponseEntity<AssetDto> saveAsset(@RequestBody AssetDto asset) {
        if (asset.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Zapisywany obiekt nie może mieć ustawionego id");
        }
        AssetDto savedAsset = assetService.saveAsset(asset);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedAsset.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedAsset);
    }

    @PutMapping("/{id}")
    ResponseEntity<AssetDto> updateAsset(@PathVariable Long id, @RequestBody AssetDto asset) {
        if (!id.equals(asset.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aktualizowany obiekt powinien mieć id zgodne z id ścieżki zasobu");
        }
        AssetDto updatedAsset = assetService.updateAsset(asset);
        return ResponseEntity.ok(updatedAsset);
    }

    @GetMapping("/{id}/assignments")
    List<AssetAssignmentDto> getAssetAssignments(@PathVariable Long id) {
        return assetService.getAssetAssignments(id);
    }

}
