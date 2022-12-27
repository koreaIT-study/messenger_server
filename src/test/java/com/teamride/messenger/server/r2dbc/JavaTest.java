package com.teamride.messenger.server.r2dbc;

import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

public class JavaTest {
	@Test
	void test() {
		String time = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
		System.out.println(time);
	}
}
