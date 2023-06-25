package ir.mmd.linux.utility.font.vm;

import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeJNIAccess;

import java.util.ArrayList;

public class JniClassesFeature implements Feature {
	@Override
	public void beforeAnalysis(BeforeAnalysisAccess access) {
		try {
			RuntimeJNIAccess.register(ArrayList.class);
			RuntimeJNIAccess.register(ArrayList.class.getConstructor(int.class));
			RuntimeJNIAccess.register(ArrayList.class.getMethod("add", Object.class));
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
}
