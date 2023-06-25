#include "ir_mmd_linux_utility_font_n_FontConfig.h"
#include <fontconfig/fontconfig.h>

[[maybe_unused]]
JNIEXPORT jobject JNICALL Java_ir_mmd_linux_utility_font_n_FontConfig_getAllFontPaths(JNIEnv *env, jclass) {
	FcInit();
	auto pattern = FcPatternCreate();
	auto objectSet = FcObjectSetBuild(FC_FILE, nullptr);
	auto fontSet = FcFontList(nullptr, pattern, objectSet);
	
	auto ArrayList = env->FindClass("java/util/ArrayList");
	auto ArrayList_init_int = env->GetMethodID(ArrayList, "<init>", "(I)V");
	auto boolean_ArrayList_add_T = env->GetMethodID(ArrayList, "add", "(Ljava/lang/Object;)Z");
	auto myList = env->NewObject(ArrayList, ArrayList_init_int, fontSet->nfont);
	
	for (int i = 0; i < fontSet->nfont; ++i) {
		auto font = fontSet->fonts[i];
		FcChar8 *fileName;
		
		if (FcPatternGetString(font, FC_FILE, 0, &fileName) == FcResultMatch) {
			auto fileName_javaString = env->NewStringUTF(reinterpret_cast<char *>(fileName));
			env->CallBooleanMethod(myList, boolean_ArrayList_add_T, fileName_javaString);
			env->DeleteLocalRef(fileName_javaString);
		}
	}
	
	FcFontSetDestroy(fontSet);
	FcObjectSetDestroy(objectSet);
	FcPatternDestroy(pattern);
	FcFini();
	
	return myList;
}
