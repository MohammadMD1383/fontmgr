package ir.mmd.linux.utility.font.n;

import java.util.List;

public class FontConfig{
	private FontConfig() {
	}
	
	static {
		System.loadLibrary("native");
	}
	
	public static native List<String> getAllFontPaths();
}
