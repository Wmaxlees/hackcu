# http://flask.pocoo.org/docs/0.12/installation/#installation
# $ sudo pip install virtualenv
# $ virtualenv venv
# . venv/bin/activate
# $ export FLASK_APP=server-flask.py
# $ flask run
# $ deactivate

from flask import Flask
from flask import request

app = Flask(__name__)

@app.route('/classify', methods=['POST'])
def classify():
  error = None
  payload = None
  if request.is_json:
    payload = request.get_json()
    if 'x' in payload and type(payload['x']) == list and 'y' in payload and type(payload['y']) == list:
      foo = 'bar'
      # call classifier    
  return "label prediction"

@app.route('/upload', methods=['POST'])
def upload():
  error = None
  payload = None
  shape = None
  if request.is_json:
    payload = request.get_json()
    shape = payload['shape']
    if 'x' in payload and type(payload['x']) == list and 'y' in payload and type(payload['y']) == list and 'shape' in payload:
      foo = 'bar'
      # call trainer    
  return shape