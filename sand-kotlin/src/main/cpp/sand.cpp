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

#include "sand.h"
#include "sobelopera.h"
#include "log.h"
#include <stdlib.h>


int start_pro(const int *pixels, int *result,int size, int with, int heigth, int threshold, int point_count,
               bool random_dot) {

    Dot *dot_arr_order = (Dot *) malloc((size / 2 + 1) * sizeof(Dot));
    int total_pot = 0;
    sobel(pixels, &total_pot, dot_arr_order, with, heigth, threshold, point_count);
    Dot *dot_random = (Dot *) malloc((total_pot + 4) * sizeof(Dot));
    if (random_dot) {
        generate_radom_dot(dot_arr_order, dot_random, total_pot);
        transform_result_arr(dot_random,result,total_pot);
    } else {
        transform_result_arr(dot_arr_order,result,total_pot);
    }
    free(dot_arr_order);
    free(dot_random);

    return total_pot;
}

void transform_result_arr(const Dot *dot, int *result,int count) {

    int index =0;
    for(int i=0;i<count;i++) {
        result[index++]=dot[i].x;
        result[index++]=dot[i].y;
    }
}

void generate_radom_dot(const Dot *dot_order, Dot *dot_random, int pot_num) {
    int random;
    const Dot *p_temp_order;
    for (int i = 0; i < pot_num; i++) {
        random = (int) (random_index() * (pot_num - 1));
        p_temp_order = &(dot_order[random]);
        dot_random[i].x = p_temp_order->x;
        dot_random[i].y = p_temp_order->y;
    }

}

float random_index() {
    return random() / (float) RAND_MAX;
}

