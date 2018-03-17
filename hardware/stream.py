import serial
import time
import pyrebase
from time import gmtime, strftime	
ard = serial.Serial("/dev/ttyACM0",9600)
config = {
    "apiKey": "AIzaSyD01mwCYWJ4x-grwrAhZh2fSesfToH_QVA",
    "authDomain": "carpark-9c5d5.firebaseapp.com",
    "databaseURL": "https://carpark-9c5d5.firebaseio.com",
    "projectId": "carpark-9c5d5",
    "storageBucket": "carpark-9c5d5.appspot.com",
    "serviceAccount": "/home/divyang/Desktop/pbl/smartCarParkIOT/hardware/CarPark-1c0acc79bed3.json"
}
firebase = pyrebase.initialize_app(config)
auth = firebase.auth()
user = auth.sign_in_with_email_and_password("divyang.duhan07@gmail.com", "divyang001")  
db = firebase.database()
# print(db.child("sensor").get(user['idToken']).val())

def stream_handler(message):
    # print(message["event"]) # put
    #print(message["path"]) # /-K7yGTTEp7O549EzTYtI
    print(message["data"]) # {'title': 'Pyrebase', "body": "etc..."}
    x = message["path"].split("/")
    if(x[1] != ""):
    	#ard.write(x[1].encode())
    	print(x[1])
    	ard.write(x[1].encode())
    	ard.write((message["data"].encode()))


my_stream = db.child("Location").child("Phoenix MarketCity, Velacherry").child("Sensor").stream(stream_handler)
