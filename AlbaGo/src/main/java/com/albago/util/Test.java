package com.albago.util;

import java.util.Arrays;
import java.util.HashSet;

public class Test {

	public static void main(String[] args) {

		String[] rDayListNew = { "1", "3", "4" };
		String[] rDayListEx = { "1", "5", "6" };

		HashSet<String> rDaySet = new HashSet<>();
		HashSet<String> rDaySetEx = new HashSet<>();

		rDaySet.addAll(Arrays.asList(rDayListNew));
		rDaySetEx.addAll(Arrays.asList(rDayListEx));

		System.out.println("retainAll: " + rDaySet.retainAll(rDaySetEx));
		System.out.println(rDaySet.toString());

		if (!rDaySet.isEmpty()) {
			System.out.println("checkDuplicateDay End/return true......................");
		} else {
			System.out.println("checkDuplicateDay End/return false......................");
		}

		System.out.print("getInter :");
		printAll(getInterSet(rDayListNew, rDayListEx));
		System.out.print("getDiffNew :");
		printAll(getDiffNewSet(rDayListNew, rDayListEx));
		System.out.print("getDiffOld :");
		printAll(getDiffOldSet(rDayListNew, rDayListEx));

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
}
