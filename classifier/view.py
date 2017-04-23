import numpy as np
import sys

np.set_printoptions(threshold=np.nan, linewidth=500)

matrix = np.load(sys.argv[1])
print(np.reshape(matrix, (28, 28)))
