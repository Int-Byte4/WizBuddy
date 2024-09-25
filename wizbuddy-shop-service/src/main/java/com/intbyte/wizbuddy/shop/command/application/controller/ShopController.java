package com.intbyte.wizbuddy.shop.command.application.controller;

import com.intbyte.wizbuddy.infrastructure.client.UserServiceClient;
import com.intbyte.wizbuddy.shop.command.application.service.ShopService;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestDeleteShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestEditShopDTO;
import com.intbyte.wizbuddy.shop.command.application.dto.RequestRegisterShopDTO;
import com.intbyte.wizbuddy.shop.command.domain.ShopRepository;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.request.RequestDeleteShopVO;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.request.RequestEditShopVO;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.request.RequestRegisterShopVO;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.response.ResponseEditShopVO;
import com.intbyte.wizbuddy.shop.command.domain.entity.vo.response.ResponseRegisterShopVO;
import com.intbyte.wizbuddy.shop.query.repository.ShopMapper;
import io.swagger.v3.oas.annotations.Operation;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("shopCommandController")
@RequestMapping("shop")
public class ShopController {

    private final Environment env;
    private final ModelMapper modelMapper;
    private final ShopService shopService;
    private final ShopRepository shopRepository;
    private final ShopMapper shopMapper;

    public ShopController(Environment env, ModelMapper modelMapper, ShopService shopService, UserServiceClient userServiceClient, ShopRepository shopRepository, ShopMapper shopMapper) {
        this.env = env;
        this.modelMapper = modelMapper;
        this.shopService = shopService;
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    @Operation(summary = "사장 - 매장 등록")
    @PostMapping("/register/{employerCode}")
    public ResponseEntity<ResponseRegisterShopVO> registerShop(@PathVariable String employerCode, @RequestBody RequestRegisterShopVO request) {
        RequestRegisterShopDTO requestRegisterShopDTO = modelMapper.map(request, RequestRegisterShopDTO.class);

        ResponseRegisterShopVO response = shopService.registerShop(employerCode, requestRegisterShopDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "사장 - 매장 정보 수정")
    @PatchMapping("/edit/{employerCode}")
    public ResponseEntity<ResponseEditShopVO> modifyShop(@PathVariable String employerCode, @RequestBody RequestEditShopVO request) {
        RequestEditShopDTO requestEditShopDTO = modelMapper.map(request, RequestEditShopDTO.class);

        ResponseEditShopVO response = shopService.modifyShop(employerCode, requestEditShopDTO);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "사장 - 매장 삭제")
    @PatchMapping("/delete/{employerCode}")
    public ResponseEntity<Void> deleteShop(@PathVariable String employerCode, @RequestBody RequestDeleteShopVO request) {
        RequestDeleteShopDTO requestDeleteShopDTO = modelMapper.map(request, RequestDeleteShopDTO.class);

        shopService.deleteShop(employerCode, requestDeleteShopDTO);

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
