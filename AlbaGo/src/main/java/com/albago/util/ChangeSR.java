package com.albago.util;

import java.util.Arrays;
import java.util.HashSet;

public class ChangeSR {

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
}
