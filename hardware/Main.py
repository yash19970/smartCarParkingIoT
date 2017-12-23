import serial
import time
import pyrebase
from time import gmtime, strftime
while (1):
     ard = serial.Serial("/dev/ttyACM0",9600)
     if(ard.readline()):
        if ard.readline().decode('ascii'):
            x =ard.readline().decode('ascii')
            a = x.split(" ")
            sensorNo = a[0]
            # if(int(x)==4):
            #     print(ard.readline().decode('ascii'))
            print ("carparked")
            break     
  
config = {
  "apiKey": "AIzaSyAOS1FZNefinLfsyhOi5wo8E9O9DRClJqY",
  "authDomain": "carpark-c131a.firebaseapp.com",
  "databaseURL": "https://carpark-c131a.firebaseio.com",
  "storageBucket": "carpark-c131a.appspot.com",
  "serviceAccount": "/home/yash/Desktop/sketch_dec10a/CarPark-3db86d57bbee.json"
}
firebase = pyrebase.initialize_app(config)

auth = firebase.auth()
inTime = strftime("%Y-%m-%d %H:%M:%S", gmtime())
#authenticate a user
user = auth.sign_in_with_email_and_password("yash19970@gmail.com", "yash123")

archer = {"sensorNo": sensorNo, "carNo": "122", "status":"1", "inTime":inTime}
db = firebase.database()
db.child("agents").push(archer, user['idToken'])



#auth=OAuthHandler(cKey,cSecret)
#auth.set_access_token(aToken,aSecret)
#twitterStream=Stream(auth,listener())
#twitterStream.filter(track=["car"])



