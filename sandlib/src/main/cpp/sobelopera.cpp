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

#include "sobelopera.h"
#include "log.h"
#include <math.h>


int const SOBEL_X[3][3] = {{-1, 0, 1},
                           {-2, 0, 2},
                           {-1, 0, 1}};

int const SOBEL_Y[3][3] = {{-1, -2, -1},
                           {0,  0,  0},
                           {1,  2,  1}};


void
sobel(const int *pixel, int *total_pot, Dot *dot, int w, int h, int threshold, int point_count) {
    *total_pot = 0;
    int i = 0;
    for (int y = 1; y < h; ++y) {
        for (int x = 1; x < w; ++x) {
            int pixelX = get_x(pixel, w, h, x, y);
            int pixelY = get_y(pixel, w, h, x, y);
            int boundary_gray = (int) sqrt(pixelX * pixelX + pixelY * pixelY);

            if (boundary_gray > threshold) {
                i++;
                if (i < point_count) {
                    *total_pot = i;
                    Dot *d = &(dot[i]);
                    d->x = x;
                    d->y = y;
                }
            }
        }
    }
}

int get_x(const int *pixel, int w, int h, int x, int y) {
    int pixel_x = (
            (SOBEL_X[0][0] * get_color(pixel, w, h, x - 1, y - 1)) +
            (SOBEL_X[0][1] * get_color(pixel, w, h, x, y - 1)) +
            (SOBEL_X[0][2] * get_color(pixel, w, h, x + 1, y - 1)) +

            (SOBEL_X[1][0] * get_color(pixel, w, h, x - 1, y)) +
            (SOBEL_X[1][1] * get_color(pixel, w, h, x, y)) +
            (SOBEL_X[1][2] * get_color(pixel, w, h, x + 1, y)) +

            (SOBEL_X[2][0] * get_color(pixel, w, h, x - 1, y + 1)) +
            (SOBEL_X[2][1] * get_color(pixel, w, h, x, y + 1)) +
            (SOBEL_X[2][2] * get_color(pixel, w, h, x + 1, y + 1))
    );
    return pixel_x;
}

int get_y(const int *pixel, int w, int h, int x, int y) {
    int pixel_Y = (
            (SOBEL_Y[0][0] * get_color(pixel, w, h, x - 1, y - 1)) +
            (SOBEL_Y[0][1] * get_color(pixel, w, h, x, y - 1)) +
            (SOBEL_Y[0][2] * get_color(pixel, w, h, x + 1, y - 1)) +

            (SOBEL_Y[1][0] * get_color(pixel, w, h, x - 1, y)) +
            (SOBEL_Y[1][1] * get_color(pixel, w, h, x, y)) +
            (SOBEL_Y[1][2] * get_color(pixel, w, h, x + 1, y)) +

            (SOBEL_Y[2][0] * get_color(pixel, w, h, x - 1, y + 1)) +
            (SOBEL_Y[2][1] * get_color(pixel, w, h, x, y + 1)) +
            (SOBEL_Y[2][2] * get_color(pixel, w, h, x + 1, y + 1))
    );
    return pixel_Y;
}

int get_color(const int *pixel, int w, int h, int x, int y) {
    if (x >= w || y >= h) {
        return 0;
    }
    int color = pixel[w * y + x];
    int blue = color & 0xFF;
    int green = (color >> 8) & 0xFF;
    int red = (color >> 16) & 0xFF;
    return (blue + green + red) / 3;
}
