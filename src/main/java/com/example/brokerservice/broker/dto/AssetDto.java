package com.example.brokerservice.broker.dto;

import com.example.brokerservice.broker.entity.Asset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssetDto {
    private Long id;

    private Long customerId;

    private String assetName;

    private Integer size;

    private Integer usableSize;

    public AssetDto(Asset asset) {
        this.id = asset.getId();
        this.customerId = asset.getCustomerId();
        this.assetName = asset.getAssetName();
        this.size = asset.getSize();
        this.usableSize = asset.getUsableSize();
    }

    public Asset toAsset() {
        Asset asset = new Asset();
        asset.setId(id);
        asset.setCustomerId(customerId);
        asset.setAssetName(assetName);
        asset.setSize(size);
        asset.setUsableSize(usableSize);
        return asset;
    }
}
