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

//
// Created by jomeslu on 17-3-28.
//

#ifndef SANDPIC_SAND_H
#define SANDPIC_SAND_H

#include "dot.h"

int start_pro(const int *pixels, int *result,int size, int with, int heigth, int threshold, int point_count,
               bool random_dot);

float random_index();

void generate_radom_dot (const Dot *dot_order,Dot *dot_random,int pot_num ) ;
void transform_result_arr(const Dot *dot, int *result,int count) ;



#endif //SANDPIC_SAND_H
