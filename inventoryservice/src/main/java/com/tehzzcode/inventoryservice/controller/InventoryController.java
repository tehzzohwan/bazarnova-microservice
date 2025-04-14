package com.tehzzcode.inventoryservice.controller;

import com.tehzzcode.inventoryservice.dto.InventoryResponse;
import com.tehzzcode.inventoryservice.service.InventoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryServiceImpl inventoryService;

    @GetMapping("/is-in-stock")
    List<InventoryResponse> isInStock(@RequestParam("skuCode") List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }
}
