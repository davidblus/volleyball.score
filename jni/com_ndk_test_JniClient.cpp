#include "com_ndk_test_JniClient.h"
#include <stdlib.h>
#include <stdio.h>
#include <string.h>


/*
 * Class:     com_ndk_test_JniClient
 * Method:    AddStr
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_ndk_test_JniClient_AddStr
	(JNIEnv *env, jclass arg, jstring in_string_a, jstring in_string_b)
{
	const char * local_string_a = (env)->GetStringUTFChars(in_string_a, 0);
	const char * local_string_b = (env)->GetStringUTFChars(in_string_b, 0);

	char result[1024] = {0};
	strcat(result, local_string_a);
	strcat(result, local_string_b);
	// jstring str = (*env)->NewStringUTF(env, "HelloWorld from JNI!");
	jstring str = (env)->NewStringUTF(result);

	(env)->ReleaseStringUTFChars(in_string_a, local_string_a);
	(env)->ReleaseStringUTFChars(in_string_b, local_string_b);

	return str;
}

typedef jstring (AddStrBase)(JNIEnv *env, jclass arg, jstring in_string_a, jstring in_string_b);
AddStrBase *oldAddStr = 0;

jstring AddStrHook(JNIEnv *env, jclass arg, jstring in_string_a, jstring in_string_b)
{
	const char * local_string_a = (env)->GetStringUTFChars(in_string_a, 0);
	const char * local_string_b = (env)->GetStringUTFChars(in_string_b, 0);

//	char result[1024] = {0};
//	strcat(result, local_string_a);
//	strcat(result, local_string_b);
	jstring str = (env)->NewStringUTF("HelloHook from JNI!");
//	jstring str = (*env)->NewStringUTF(env, result);

	(env)->ReleaseStringUTFChars(in_string_a, local_string_a);
	(env)->ReleaseStringUTFChars(in_string_b, local_string_b);

	return str;
}

/*
* Class:     com_ndk_test_JniClient
* Method:    AddInt
* Signature: (II)I
*/
JNIEXPORT jint JNICALL Java_com_ndk_test_JniClient_AddInt
	(JNIEnv *env, jclass arg, jint a, jint b)
{
	return a + b;
}

//MSInitialize
//{
//	MSHookFunction((void *)Java_com_ndk_test_JniClient_AddStr, (void *)AddStrHook, (void **)&oldAddStr);
//}

