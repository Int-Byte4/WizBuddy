package com.intbyte.wizbuddy.shop.controller;

import com.intbyte.wizbuddy.shop.domain.DeleteShopInfo;
import com.intbyte.wizbuddy.shop.domain.EditShopInfo;
import com.intbyte.wizbuddy.shop.domain.RegisterShopInfo;
import com.intbyte.wizbuddy.shop.dto.ShopDTO;
import com.intbyte.wizbuddy.shop.service.ShopService;
import com.intbyte.wizbuddy.shop.vo.request.RequestDeleteShopVO;
import com.intbyte.wizbuddy.shop.vo.request.RequestEditShopVO;
import com.intbyte.wizbuddy.shop.vo.request.RequestRegisterShopVO;
import com.intbyte.wizbuddy.shop.vo.response.ResponseDeleteShopVO;
import com.intbyte.wizbuddy.shop.vo.response.ResponseEditShopVO;
import com.intbyte.wizbuddy.shop.vo.response.ResponseRegisterShopVO;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping("/register/{employerCode}")
    public ResponseEntity<ResponseRegisterShopVO> registerShop(@PathVariable String employerCode, @RequestBody RequestRegisterShopVO request) {
        RegisterShopInfo registerShopInfo = modelMapper.map(request, RegisterShopInfo.class);

        ResponseRegisterShopVO response = shopService.registerShop(employerCode, registerShopInfo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/shops")
    public ResponseEntity<List<ShopDTO>> getShops() {
        List<ShopDTO> shopList = shopService.getAllShop();

        return ResponseEntity.status(HttpStatus.OK).body(shopList);
    }

    @GetMapping("/{shopCode}")
    public ResponseEntity<ShopDTO> getShop(@PathVariable int shopCode) {
        ShopDTO shopDTO = shopService.getShop(shopCode);

        return ResponseEntity.status(HttpStatus.OK).body(shopDTO);
    }

    @PatchMapping("/edit/{employerCode}")
    public ResponseEntity<ResponseEditShopVO> modifyShop(@PathVariable String employerCode, @RequestBody RequestEditShopVO request) {
        EditShopInfo editShopInfo = modelMapper.map(request, EditShopInfo.class);

        ResponseEditShopVO response = shopService.modifyShop(employerCode, editShopInfo);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/delete/{employerCode}")
    public ResponseEntity<ResponseDeleteShopVO> deleteShop(@PathVariable String employerCode, @RequestBody RequestDeleteShopVO request) {
        DeleteShopInfo deleteShopInfo = modelMapper.map(request, DeleteShopInfo.class);

        ResponseDeleteShopVO response = shopService.deleteShop(employerCode, deleteShopInfo);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
