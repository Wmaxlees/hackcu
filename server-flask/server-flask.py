# http://flask.pocoo.org/docs/0.12/installation/#installation
# $ sudo pip install virtualenv
# $ virtualenv venv
# . venv/bin/activate
# $ export FLASK_APP=server-flask.py
# $ flask run
# $ deactivate

from flask import Flask
from flask import request
import numpy as np
import os
import sys
from bresenham import bresenham
from sklearn.externals import joblib

app = Flask(__name__)

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


def get_class_name (index):
    if index == 1:
        return 'circle'
    elif index == 0:
        return 'line'
    elif index == 2:
        return 'square'


def classify (x_values, y_values):
    final = np.array([generate_matrix_values(x_values, y_values)])

    #clf = joblib.load('hmm.pkl')
    clf = joblib.load('svm.pkl')

    result = clf.predict(final)

    os.remove(filename)

    return get_class_name(result)


if __name__ == '__main__':
    main(sys.argv)

@app.route('/classify', methods=['POST'])
def classify():
  error = None
  payload = None
  result = ''
  if request.is_json:
    payload = request.get_json()
    if 'x' in payload and type(payload['x']) == list and 'y' in payload and type(payload['y']) == list:
      result = classify(payload['x'], payload['y'])
      # call classifier    
  return result

@app.route('/upload', methods=['POST'])
def upload():
  error = None
  payload = None
  shape = None
  if request.is_json:
    payload = request.get_json()
    shape = payload['shape']
    if 'x' in payload and type(payload['x']) == list and 'y' in payload and type(payload['y']) == list and 'shape' in payload:
      pass # hard pass
      # call trainer    
  return shape