package com.albago.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {
	public static void main(String args[]) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());
		String[] nArr = br.readLine().split(" ");
		int[] nArrInt = stringToInt(nArr);
		int M = Integer.parseInt(br.readLine());
		String[] mArr = br.readLine().split(" ");
		int[] mArrInt = stringToInt(mArr);
		br.close();

		Arrays.sort(nArr);

		int left = 0;
		int right = nArr.length - 1;
		int mid = 0;

		for (int m : mArrInt) {
			mid = (left + right) / 2;
			while (left >= right) {

			}
		}

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
	}

	public static int[] stringToInt(String[] arr) {
		int[] arrInt = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			arrInt[i] = Integer.parseInt(arr[i]);
		}
		return arrInt;
	}
}