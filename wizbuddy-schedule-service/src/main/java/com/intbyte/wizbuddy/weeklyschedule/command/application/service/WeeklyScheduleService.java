package com.intbyte.wizbuddy.weeklyschedule.command.application.service;

import com.intbyte.wizbuddy.weeklyschedule.command.domain.aggregate.vo.response.ResponseRegistWeeklyScheduleVO;

public interface WeeklyScheduleService {
    ResponseRegistWeeklyScheduleVO registSchedule(ResponseRegistWeeklyScheduleVO responseRegistWeeklyScheduleVO);
}
