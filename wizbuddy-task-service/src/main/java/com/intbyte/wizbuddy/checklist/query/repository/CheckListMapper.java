package com.intbyte.wizbuddy.checklist.query.repository;

import com.intbyte.wizbuddy.checklist.query.dto.CheckListQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckListMapper {

    CheckListQueryDTO findCheckListById(int checkListCode);

    List<CheckListQueryDTO> findAllCheckListByShopId(int shopCode);
}
