package com.albago.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) {

		String[] day = { "1", "5", "6" };

		System.out.println(getDate(day, "22:00", "HH:mm"));

	}

	public static String[] getInterSet(String[] rDayListNew, String[] rDayListEx) {

		HashSet<String> rDaySetNew = new HashSet<>(Arrays.asList(rDayListNew));
		HashSet<String> rDaySetEx = new HashSet<>(Arrays.asList(rDayListEx));

		rDaySetNew.retainAll(rDaySetEx);

		return (String[]) rDaySetNew.toArray(new String[0]);
	}

	public static String[] getDiffNewSet(String[] rDayListNew, String[] rDayListEx) {

		HashSet<String> rDaySetNew = new HashSet<>(Arrays.asList(rDayListNew));
		HashSet<String> rDaySetEx = new HashSet<>(Arrays.asList(rDayListEx));

		rDaySetNew.removeAll(rDaySetEx);

		return (String[]) rDaySetNew.toArray(new String[0]);
	}

	public static String[] getDiffOldSet(String[] rDayListNew, String[] rDayListEx) {

		HashSet<String> rDaySetNew = new HashSet<>(Arrays.asList(rDayListNew));
		HashSet<String> rDaySetEx = new HashSet<>(Arrays.asList(rDayListEx));

		rDaySetEx.removeAll(rDaySetNew);

		return (String[]) rDaySetEx.toArray(new String[0]);
	}

	public static void printAll(String[] string) {
		for (String i : string) {
			System.out.print(" " + i);
		}
		System.out.println();
	}

	public static String getDate(String[] day, String startTime, String format) {

		Calendar now = Calendar.getInstance();

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		SimpleDateFormat sdf_return = new SimpleDateFormat("yyyy-MM-dd HH:mm");

		Calendar time = Calendar.getInstance();

		try {
			time.setTime(sdf.parse(startTime));
			System.out.println(time);
			System.out.println(time.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK);

		Calendar clone = (Calendar) now.clone();
		clone.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
		clone.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
		clone.set(Calendar.SECOND, time.get(Calendar.SECOND));
		clone.add(Calendar.MILLISECOND, time.get(Calendar.MILLISECOND));

		for (String i : day) {

			int parseInt = Integer.parseInt(i);

			Calendar cal = (Calendar) clone.clone();
			cal.clear(Calendar.HOUR_OF_DAY);
			cal.clear(Calendar.MINUTE);
			cal.clear(Calendar.SECOND);
			cal.add(Calendar.MILLISECOND, (int) time.get(Calendar.MILLISECOND));
			System.out.println(cal.getTime());

			if (parseInt == dayOfWeek) {
				if (cal.after(now)) {
					return getTime(cal, parseInt, sdf_return);
				}
			} else if (Integer.parseInt(i) > dayOfWeek) {
				return getTime(cal, parseInt, sdf_return);
			}

		}

		clone.add(Calendar.WEEK_OF_YEAR, 1);
		clone.set(Calendar.DAY_OF_WEEK, Integer.parseInt(day[0]));

		return getTime(now, Integer.parseInt(day[0]), sdf_return);

	}

	public static String getTime(Calendar cal, int parseInt, SimpleDateFormat sdf_return) {
		cal.set(Calendar.DAY_OF_WEEK, parseInt);
		return sdf_return.format(cal.getTime());
	}
}
