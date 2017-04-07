/*
 *  Copyright 2017 jomeslu (luzhensheng72@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include <jni.h>
#include <string>
#include "sand.h"
#include "log.h"

extern "C"
JNIEXPORT jintArray JNICALL
Java_jomeslu_com_sandpic_SandPic_generate(JNIEnv *env, jclass type, jintArray pixels_, jint width,
                                          jint heigth, jint threshold, jint ponitNum) {
    jint *pixels = env->GetIntArrayElements(pixels_, NULL);
    jint size = env->GetArrayLength(pixels_);
    jintArray  temp = env->NewIntArray(size);
    jint *result_temp = env->GetIntArrayElements(temp, NULL);
    jint total_pots = start_pro(pixels,result_temp,size,width,heigth,threshold,ponitNum, true);

    jintArray result = env->NewIntArray(total_pots);
    env->SetIntArrayRegion(result, 0, total_pots, result_temp);
    env->ReleaseIntArrayElements(pixels_, pixels, 0);
    return result;
}




