import numpy as np
import os
import sys

def main (argv):
    result = []
    labels = []
    print(os.listdir(argv[1]))
    for i, shape in enumerate(os.listdir(argv[1])):
        for file in os.listdir(argv[1] + '/' + shape):
            filename = argv[1] + '/' + shape + '/' + file
            print('Processing File: ' + filename)
            
            result.append(np.load(filename))
            labels.append(i)

    
    np.save(argv[2], np.array(result))
    np.save(argv[3], np.array(labels))


if __name__ == '__main__':
    main(sys.argv)
