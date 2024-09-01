package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.checklist.domain.CheckListMybatis;
import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckListMapper {

    CheckListMybatis findCheckListById(int checkListCode);

    List<CheckListMybatis> findAllCheckList();

    List<CheckListMybatis> findAllCheckListsByFlag();
}
