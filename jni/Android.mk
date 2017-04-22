LOCAL_PATH := $(call my-dir)

#include $(CLEAR_VARS)
#LOCAL_MODULE := substrate
#LOCAL_SRC_FILES := libsubstrate.so
#include $(PREBUILT_SHARED_LIBRARY)
#
#include $(CLEAR_VARS)
#LOCAL_MODULE := substrate-dvm
#LOCAL_SRC_FILES := libsubstrate-dvm.so
#include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE := volleyball.score
LOCAL_SRC_FILES := com_ndk_test_JniClient.cpp
#LOCAL_LDLIBS := -llog
#LOCAL_ARM_MODE := arm
#TARGET_CPU_API := armeabi
#APP_ABI := armeabi
#TARGET_ARCH := armeabi
#TARGET_ABI := armeabi
#LOCAL_LDLIBS += -L$(LOCAL_PATH) -lsubstrate-dvm -lsubstrate
include $(BUILD_SHARED_LIBRARY)
