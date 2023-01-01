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
	}
}
