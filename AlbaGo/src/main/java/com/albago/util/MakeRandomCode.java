package com.albago.util;

public class MakeRandomCode {

	public int makeRandomNumber() {
		int random;
		while (true) {
			random = (int) (Math.random() * 10000);
			if (random >= 1000) {
				break;
			}
		}
		return random;
	}
}
