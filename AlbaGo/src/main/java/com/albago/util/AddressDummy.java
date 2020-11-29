package com.albago.util;

import java.util.Scanner;

public class AddressDummy {

	public static void main(String args[]) {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNextLine()) {
			String address = sc.nextLine();
			String arr[] = address.split("\\|");
			System.out.print(arr[3] + " " + arr[4] + " " + arr[5] + " " + arr[6] + " " + arr[8]);
			if (!arr[9].equals("0"))
				System.out.print("-" + arr[9]);
			System.out.println("번지");
		}

		sc.close();
	}
}
