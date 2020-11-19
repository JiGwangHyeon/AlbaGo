package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.mapper.ScheduleRepeatMapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
public class Test {

	@Setter(onMethod_ = @Autowired)
	private static ScheduleRepeatMapper mapper;

	public static void main(String args[]) throws ParseException {

		ScheduleRepeatVO scheduleRepeat = new ScheduleRepeatVO();

		scheduleRepeat.setC_code(1234123521);
		scheduleRepeat.setU_id("a");
		scheduleRepeat.setSr_start("13:00:00");
		scheduleRepeat.setSr_end("15:00:00");
		scheduleRepeat.setSr_repeat("7");

		checkDuplicateRepeat(scheduleRepeat);

	}

	public static int checkDuplicateRepeat(ScheduleRepeatVO scheduleRepeat) {
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

	public static boolean checkDuplicateDay(String[] rDayList, String[] rDayListEx) {
		for (String i : rDayList) {
			for (String j : rDayListEx) {
				if (i == j) {
					return true;
				}
			}
		}

		return false;
	}

	public static boolean checkTime(String srStart, String srStartEx, String srEnd, String srEndEx) {
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
