package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.checklist.domain.CheckListMybatis;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckListMapper {

    CheckListMybatis findCheckListById(int checkListCode);

    List<CheckListMybatis> findAllCheckList();

    List<CheckListMybatis> findAllCheckListByShopId(int shopCode);
}
