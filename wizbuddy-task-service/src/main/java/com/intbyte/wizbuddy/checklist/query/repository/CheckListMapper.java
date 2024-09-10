package com.intbyte.wizbuddy.checklist.query.repository;

import com.intbyte.wizbuddy.checklist.query.dto.CheckListDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckListMapper {

    CheckListDTO findCheckListById(int checkListCode);

    List<CheckListDTO> findAllCheckListByShopId(int shopCode);
}
