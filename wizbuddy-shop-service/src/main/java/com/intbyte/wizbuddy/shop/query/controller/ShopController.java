package com.intbyte.wizbuddy.shop.query.controller;

import com.intbyte.wizbuddy.shop.query.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.query.service.ShopService;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("shopQueryController")
@RequestMapping("shop")
public class ShopController {

    private Environment env;
    private ModelMapper modelMapper;
    private ShopService shopService;

    public ShopController(Environment env, ModelMapper modelMapper, ShopService shopService) {
        this.env = env;
        this.modelMapper = modelMapper;
        this.shopService = shopService;
    }

    @Operation(summary = "관리자 - 매장 전체 조회")
    @GetMapping("/shops")
    public ResponseEntity<List<ShopDTO>> getShops() {
        List<ShopDTO> shopList = shopService.getAllShop();

        return ResponseEntity.status(HttpStatus.OK).body(shopList);
    }

    @Operation(summary = "매장 단 건 조회")
    @GetMapping("/{shopCode}")
    public ResponseEntity<ShopDTO> getShop(@PathVariable int shopCode) {
        ShopDTO shopDTO = shopService.getShop(shopCode);

        return ResponseEntity.status(HttpStatus.OK).body(shopDTO);
    }
}
