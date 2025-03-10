package com.example.brokerservice.broker.service;

import com.example.brokerservice.broker.entity.Asset;

import java.util.List;

public interface AssetService {
    List<Asset> getAssets(Long customerId);
}
