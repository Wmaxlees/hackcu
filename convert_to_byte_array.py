import os
import math
import numpy as np
import sys
from bresenham import bresenham

def load_xy_values (filename):
    file = open(filename, 'r')
    count = int(file.readline())

    x_values = []
    for i in range(count):
        x_values.append(int(file.readline()))

    y_values = []
    for i in range(count):
        y_values.append(int(file.readline()))

    return (x_values, y_values,)

def generate_matrix_values (x_values, y_values):
    values = np.zeros((28, 28), dtype=np.int8)

    for i in range(len(x_values)-1):
        # print(len(x_values))
        points = list(bresenham(x_values[i], y_values[i], x_values[i+1], y_values[i+1]))

        for point in points:
            values[point[0]][point[1]] = 1
            
    return values.flatten()


def main (argv):
    for shape in os.listdir(argv[1]):
        for file in os.listdir(argv[1] + '/' + shape):
            filename = argv[1] + '/' + shape + '/' + file;
            x_values, y_values = load_xy_values(filename)

            final = generate_matrix_values(x_values, y_values)

            target = argv[2] + '/' + shape + '/' + file;
            np.save(target, final)


if __name__ == '__main__':
    main(sys.argv)
