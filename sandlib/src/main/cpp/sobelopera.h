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
#include "dot.h"

#ifndef SANDPIC_SOBELOPERA_H
#define SANDPIC_SOBELOPERA_H

void
sobel(const int *pixel, int *total_pot, Dot *dot, int w, int h, int threshold, int point_count);

int get_color(const int *pixel, int w, int h, int x, int y);

int get_x(const int *pixel, int w, int h, int x, int y);

int get_y(const int *pixel, int w, int h, int x, int y);


#endif //SANDPIC_SOBELOPERA_H
