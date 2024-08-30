package com.intbyte.wizbuddy.mapper;

import com.intbyte.wizbuddy.checklist.domain.entity.CheckList;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CheckListMapper {

    CheckList findCheckListById(int checkListCode);

    List<CheckList> findAllCheckList();
}
