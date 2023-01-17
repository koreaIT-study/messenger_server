package com.teamride.messenger.server.r2dbc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.Test;

public class JavaTest {
	@Test
	void test() {
		String time = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss.SSS").format(System.currentTimeMillis());
		System.out.println(time);
		
		Path path = Paths.get("/");
		String s = path.toAbsolutePath().toString();
	System.out.println(s);	
	
	String msg = "e8a21bd8-d8df-488b-b71b-648b37db961f||reserveSnackPay.jsp";
	String result = msg.substring(msg.lastIndexOf("||"));
	System.out.println(result);
		
	}
}
