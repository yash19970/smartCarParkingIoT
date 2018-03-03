import serial
import time
import pyrebase
from time import gmtime, strftime
from multiprocessing import Process
config = {
      # "apiKey": "AIzaSyAOS1FZNefinLfsyhOi5wo8E9O9DRClJqY",
      # "authDomain": "carpark-c131a.firebaseapp.com",
      # "databaseURL": "https://carpark-c131a.firebaseio.com",
      # "storageBucket": "carpark-c131a.appspot.com",
      # "serviceAccount": "/home/yash/Desktop/GithubRepo/smartCarParkIOT/hardware/CarPark-3db86d57bbee.json"
        "apiKey": "AIzaSyD01mwCYWJ4x-grwrAhZh2fSesfToH_QVA",
        "authDomain": "carpark-9c5d5.firebaseapp.com",
        "databaseURL": "https://carpark-9c5d5.firebaseio.com",
        "projectId": "carpark-9c5d5",
        "storageBucket": "carpark-9c5d5.appspot.com",
        "serviceAccount": "/home/yash/Downloads/CarPark-1c0acc79bed3.json"
}
firebase = pyrebase.initialize_app(config)
auth = firebase.auth()
user = auth.sign_in_with_email_and_password("divyang.duhan07@gmail.com", "divyang001")  
db = firebase.database()
ard = serial.Serial("/dev/ttyACM1",9600)
def arduinoData():
    while (1):
        if(ard.readline()):
            if ard.readline().decode('ascii'):
                x =ard.readline().decode('ascii')
                a = x.split(" ")
                sensorNo = a[0]
                print(sensorNo)
                print ("carparked")
                # break     
        inTime = strftime("%Y-%m-%d %H:%M:%S", gmtime())
        archer = {"sensorNo": sensorNo, "carNo": "122", "status":"1", "inTime":inTime}
        db.child("agents").push(archer, user['idToken'])
        print(db.child("parking").get(user['idToken']).val())

def firebaseData():
    def stream_handler(message):
        # print(message["event"]) # put
        #print(message["path"]) # /-K7yGTTEp7O549EzTYtI
        #print(message["data"]) # {'title': 'Pyrebase', "body": "etc..."}
        x = message["path"].split("/")
        if(x[1] != ""):
            #print(type(x[1]))
            ard.write(x[1].encode())  
    my_stream = db.child("sensor").stream(stream_handler)


if __name__ == '__main__':
  p1 = Process(target=arduinoData)
  p1.start()
  p2 = Process(target=firebaseData)
  p2.start()
  p1.join()
  p2.join()



#auth=OAuthHandler(cKey,cSecret)
#auth.set_access_token(aToken,aSecret)
#twitterStream=Stream(auth,listener())
#twitterStream.filter(track=["car"])



