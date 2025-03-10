package com.example.brokerservice.broker.service.impl;

import com.example.brokerservice.broker.entity.Asset;
import com.example.brokerservice.broker.repository.AssetRepository;
import com.example.brokerservice.broker.service.AssetService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetServiceImpl implements AssetService {
    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public List<Asset> getAssets(Long customerId) {
        return assetRepository.findByCustomerId(customerId);
    }
}
