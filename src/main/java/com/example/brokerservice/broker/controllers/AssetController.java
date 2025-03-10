package com.example.brokerservice.broker.controllers;

import com.example.brokerservice.auth.service.UserService;
import com.example.brokerservice.broker.dto.AssetDto;
import com.example.brokerservice.broker.service.AssetService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/assets")
public class AssetController {
    private final AssetService assetService;
    private final UserService userService;

    public AssetController(AssetService assetService, UserService userService) {
        this.assetService = assetService;
        this.userService = userService;
    }

    @GetMapping
    public List<AssetDto> getAssets(Principal principal) {
        var user = userService.getByUserName(principal.getName());
        return assetService
                .getAssets(user.getId())
                .stream()
                .map(AssetDto::new)
                .toList();
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<AssetDto> getAssets(@PathVariable("customerId") Long customerId) {
        return assetService
                .getAssets(customerId)
                .stream()
                .map(AssetDto::new)
                .toList();
    }
}
