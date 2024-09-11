package com.intbyte.wizbuddy.weeklyschedule.command.application.service;

import com.intbyte.wizbuddy.common.exception.CommonException;
import com.intbyte.wizbuddy.common.exception.StatusEnum;
import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.entity.WeeklySchedule;
import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.vo.response.ResponseRegistWeeklyScheduleVO;
import com.intbyte.wizbuddy.weeklyschedule.command.domain.repository.WeeklyScheduleRepository;
import com.intbyte.wizbuddy.weeklyschedule.query.repository.WeeklyScheduleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class WeeklyScheduleServiceImpl implements WeeklyScheduleService {

    private final WeeklyScheduleMapper weeklyScheduleMapper;
    private final WeeklyScheduleRepository weeklyScheduleRepository;

    @Override
    @Transactional
    public ResponseRegistWeeklyScheduleVO registSchedule
            (ResponseRegistWeeklyScheduleVO responseRegistWeeklyScheduleVO)  {

        WeeklySchedule insertWeeklySchedule =
                new WeeklySchedule(
                          responseRegistWeeklyScheduleVO.getScheduleCode()
                        , responseRegistWeeklyScheduleVO.isScheduleFlag()
                        , responseRegistWeeklyScheduleVO.getScheduleStartDate()
                        , responseRegistWeeklyScheduleVO.getCreatedAt()
                        , responseRegistWeeklyScheduleVO.getUpdatedAt());

        if (weeklyScheduleMapper.findScheduleByStartDate(responseRegistWeeklyScheduleVO.getScheduleStartDate()) != null)
            throw new CommonException(StatusEnum.SCHEDULE_CODE_DUPLICATE);

        weeklyScheduleRepository.save(insertWeeklySchedule);

        return responseRegistWeeklyScheduleVO;
    }
}
