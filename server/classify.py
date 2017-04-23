import numpy as np
import os
import sys

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
        x0 = x_values[i]
        x1 = x_values[i+1]

        y0 = y_values[i]
        y1 = y_values[i+1]

        delta_x = x1 - x0
        delta_y = y1 - y0
        delta_err = abs(delta_y / delta_x)
        error = delta_err - 0.5
        y = y0
        for x in range(x0, x1): 
            values[x][y] = 255
            error = error + delta_err
            if error >= 0.5:
                y = y + 1
                error = error - 1.0

    return values.flatten()


def get_class_name (index):
    if index == 0:
        return 'circle'
    else if index == 1:
        return 'line'
    else if index == 2:
        return 'square'


def main (argv):
    filename = argv[1]
    x_values, y_values = load_xy_values(filename)
    final = generate_matrix_values(x_values, y_values)

    clf = joblib.load('hmm.pkl')

    result = clf.predict(final)

    os.remove(filename)

    print(get_class_name(result))


if __name__ == '__main__':
    main(sys.argv)