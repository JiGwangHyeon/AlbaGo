package com.albago.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.mapper.ScheduleRepeatMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class ScheduleRepeatServiceImpl implements ScheduleRepeatService {

	private ScheduleRepeatMapper mapper;

	@Override
	public int insertRepeat(ScheduleRepeatVO scheduleRepeat) {
		log.info("insertRepeat..............");

		if (checkDuplicateRepeat(scheduleRepeat) != 0) {
			return -1;
		}

		return mapper.insertRepeat(scheduleRepeat);
	}

	@Override
	public int checkDuplicateRepeat(ScheduleRepeatVO scheduleRepeat) {
		log.info("checkDuplicateRepeat........................");

		String[] rDayList = scheduleRepeat.getSr_repeat().split("");
		String srStart = scheduleRepeat.getSr_start();
		String srEnd = scheduleRepeat.getSr_end();

		int cntDup = 0;

		List<ScheduleRepeatVO> scheduleRepeatExist = mapper.getListForCheckDuplicate(scheduleRepeat);
		for (ScheduleRepeatVO exist : scheduleRepeatExist) {
			String[] rDayListEx = exist.getSr_repeat().split("");

			String srStartEx = exist.getSr_start();
			String srEndEx = exist.getSr_end();

			if (checkDuplicateDay(rDayList, rDayListEx) && checkTime(srStart, srStartEx, srEnd, srEndEx))
				cntDup += 1;

		}

		return cntDup;
	}

	public boolean checkDuplicateDay(String[] rDayList, String[] rDayListEx) {
		for (String i : rDayList) {
			for (String j : rDayListEx) {
				if (i == j) {
					return true;
				}
			}
		}

		return false;
	}

	public boolean checkTime(String srStart, String srStartEx, String srEnd, String srEndEx) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Calendar ss = Calendar.getInstance();
		Calendar sse = Calendar.getInstance();
		Calendar se = Calendar.getInstance();
		Calendar see = Calendar.getInstance();

		try {
			ss.setTime(sdf.parse(srStart));
			sse.setTime(sdf.parse(sdf.format(sdf2.parse(srStartEx))));
			se.setTime(sdf.parse(srEnd));
			see.setTime(sdf.parse(sdf.format(sdf2.parse(srEndEx))));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		System.out.println("ss: " + ss.getTime());
		System.out.println("sse: " + sse.getTime());
		System.out.println("se: " + se.getTime());
		System.out.println("see: " + see.getTime());

		if (ss.after(se)) {
			se.add(Calendar.DATE, 1);
		}

		if (sse.after(see)) {
			see.add(Calendar.DATE, 1);
		}

		if (see.after(ss) && (ss.after(sse) || ss.equals(sse))) {
			return true;
		} else if (see.after(se) && se.after(sse)) {
			return true;
		} else if (se.after(see) && sse.after(ss)) {
			return true;
		}

		return false;
	}

}
