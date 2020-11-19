package com.albago.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.albago.domain.ScheduleRepeatVO;
import com.albago.domain.ScheduleVO;
import com.albago.mapper.ScheduleMapper;
import com.albago.mapper.ScheduleRepeatMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Service
@AllArgsConstructor
@Log4j
public class ScheduleRepeatServiceImpl implements ScheduleRepeatService {

	private ScheduleRepeatMapper srMapper;
	private ScheduleMapper sMapper;

	@Override
	public int insertRepeat(ScheduleRepeatVO scheduleRepeat) {
		log.info("insertRepeat..............");

		int dupCnt = checkDuplicateRepeat(scheduleRepeat);

		if (dupCnt > 0) {
			log.info("Duplicated Repeat Count: " + dupCnt);
			return 0;
		} else if (dupCnt < 0) {
			log.info("Duplicated Schedule Exist.");
			return -1;
		}

		return srMapper.insertRepeat(scheduleRepeat);
	}

	@Override
	public int checkDuplicateRepeat(ScheduleRepeatVO scheduleRepeat) {
		log.info("checkDuplicateRepeat........................");

		String[] rDayList = scheduleRepeat.getSr_repeat().split("");
		String srStart = scheduleRepeat.getSr_start();
		String srEnd = scheduleRepeat.getSr_end();

		Calendar sst = Calendar.getInstance();
		Calendar set = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");

		try {
			sst.setTime(sdf.parse(srStart));
			set.setTime(sdf.parse(srEnd));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (sst.after(set))
			set.add(Calendar.DATE, 1);
		long millis = set.getTimeInMillis() - sst.getTimeInMillis();

		boolean dup = false;

		for (String i : rDayList) {
			ScheduleVO schedule = new ScheduleVO();
			schedule.setC_code(scheduleRepeat.getC_code());
			schedule.setU_id(scheduleRepeat.getU_id());
			schedule.setSr_code(Integer.parseInt(i));
			List<ScheduleVO> list = sMapper.checkDuplicateForRepeat(schedule);
			for (ScheduleVO l : list) {
				Calendar sse = Calendar.getInstance();
				Calendar see = Calendar.getInstance();

				try {
					sse.setTime(sdf2.parse(l.getS_start()));
					see.setTime(sdf2.parse(l.getS_end()));
				} catch (ParseException e) {
					e.printStackTrace();
				}

				Calendar ss = (Calendar) sse.clone();
				ss.set(Calendar.HOUR_OF_DAY, sst.get(Calendar.HOUR_OF_DAY));
				ss.set(Calendar.MINUTE, sst.get(Calendar.MINUTE));
				ss.set(Calendar.SECOND, sst.get(Calendar.SECOND));

				Calendar se = (Calendar) ss.clone();
				se.add(Calendar.MILLISECOND, (int) millis);

				if (see.after(ss) && (ss.after(sse) || ss.equals(sse))) {
					log.trace(sse.getTime() + "(sse) <=" + ss.getTime() + "(ss) <" + see.getTime() + "(see)");
					log.trace("s_code: " + l.getS_code());
					dup = true;
					break;
				}
				if ((see.after(se) || see.equals(se)) && se.after(sse)) {
					log.trace(sse.getTime() + "(sse) <" + se.getTime() + "(se) <=" + see.getTime() + "(see)");
					log.trace("s_code: " + l.getS_code());
					dup = true;
					break;

				}
				if ((se.after(see) || se.equals(see)) && (sse.after(ss) || sse.equals(ss))) {
					log.trace(ss.getTime() + "(ss) <=" + sse.getTime() + "(sse) and " + see.getTime() + "(see) <="
							+ se.getTime() + "(se)");
					log.trace("s_code: " + l.getS_code());
					dup = true;
					break;
				}
			}
			if (dup) {
				break;
			}
		}
		if (dup) {
			return -1;
		}

		int cntDup = 0;

		List<ScheduleRepeatVO> scheduleRepeatExist = srMapper.getListForCheckDuplicate(scheduleRepeat);

		for (ScheduleRepeatVO exist : scheduleRepeatExist) {
			String[] rDayListEx = exist.getSr_repeat().split("");

			String srStartEx = exist.getSr_start();
			String srEndEx = exist.getSr_end();

			if (checkDuplicateDay(rDayList, rDayListEx) && checkTime(srStart, srStartEx, srEnd, srEndEx)) {
				cntDup += 1;
			}
		}

		if (cntDup == 0) {
			log.info("There's No Duplicated.");
		} else {
			log.info("Duplicated Count: " + cntDup);
		}

		return cntDup;
	}

	public boolean checkDuplicateDay(String[] rDayList, String[] rDayListEx) {
		log.info("checkDuplicateDay Start.................................");
		for (String i : rDayList) {
			for (String j : rDayListEx) {
				log.info("checkDuplicate: " + i + ", " + j);
				if (i.equals(j)) {
					log.info("checkDuplicateDay End/return true......................");
					return true;
				}
			}
		}

		log.info("checkDuplicateDay End/return false......................");
		return false;
	}

	public boolean checkTime(String srStart, String srStartEx, String srEnd, String srEndEx) {
		log.info("checkTime Start......................");
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

		log.trace("ss: " + ss.getTime());
		log.trace("sse: " + sse.getTime());
		log.trace("se: " + se.getTime());
		log.trace("see: " + see.getTime());

		if (ss.after(se)) {
			se.add(Calendar.DATE, 1);
			log.trace("se_added: " + se.getTime());
		}

		if (sse.after(see)) {
			see.add(Calendar.DATE, 1);
			log.trace("see_added: " + see.getTime());
		}

		if (see.after(ss) && (ss.after(sse) || ss.equals(sse))) {
			log.trace(sse.getTime() + "(sse) <=" + ss.getTime() + "(ss) <" + see.getTime() + "(see)");
			log.info("checkTime End/return true......................");
			return true;
		}
		if ((see.after(se) || see.equals(se)) && se.after(sse)) {
			log.trace(sse.getTime() + "(sse) <" + se.getTime() + "(se) <=" + see.getTime() + "(see)");
			log.info("checkTime End/return true......................");
			return true;
		}
		if ((se.after(see) || se.equals(see)) && (sse.after(ss) || sse.equals(ss))) {
			log.trace(ss.getTime() + "(ss) <=" + sse.getTime() + "(sse) and " + see.getTime() + "(see) <="
					+ se.getTime() + "(se)");
			log.info("checkTime End/return true......................");
			return true;
		}

		log.info("checkTime End/return false......................");
		return false;
	}
}
