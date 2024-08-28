package com.intbyte.wizbuddy.board.service;

import com.intbyte.wizbuddy.mapper.NoticeBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticeBoardService {

    private NoticeBoardMapper noticeBoardMapper;

    @Autowired
    public NoticeBoardService(NoticeBoardMapper noticeBoardMapper) {
        this.noticeBoardMapper = noticeBoardMapper;
    }
}
