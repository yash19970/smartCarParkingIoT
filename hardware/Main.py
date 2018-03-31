import serial
import time
import pyrebase
from time import gmtime, strftime
from multiprocessing import Process
import urllib.request as urllib2
import http.cookiejar as cookielib
from getpass import getpass
import sys
import os
from stat import *
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
ard = serial.Serial("/dev/ttyACM0",9600)
def arduinoData():
    while (1):
        data = db.child("Location").child("Phoenix MarketCity, Velacherry").child("Sensor").child("1").get().val()
        #print(data["status"])
        #print(data["booked"])
        t_end = time.time() + 10
        flag=0;
        if(data["status"] ==  "no" and data["booked"] == "no" ):
            print("in")
            print(time.time())
            #print(t_end)
            while time.time() < t_end:
                print(t_end)
                try:
                    if ard.readline().decode('ascii'):
                        x =ard.readline().decode('ascii')
                        a = x.split(" ")
                        sensorNo = a[0]
                        flag=1; 
                except Exception as e:
                    print("error occured:")
            if(flag ==1):
                sendMesg()
                print("send mg")
            print("out")
                        #print(data.child("1").child("booked").get().val())
                # break     
                #print(db.child("parking").get(user['idToken']).val())




def sendMesg():
    message = "loda lag gaya bc"
    number = "7010529344"    
    username = "7010529344"
    passwd = "0317"

    message = "+".join(message.split(' '))

 #LOGIN INTO WAY2SMS.
    url ='http://site24.way2sms.com/Login1.action?'
    data = 'username='+username+'&password='+passwd+'&Submit=Sign+in'

 #COOKIES

    cj= cookielib.CookieJar()
    opener = urllib2.build_opener(urllib2.HTTPCookieProcessor(cj))

 #ADDING DETAILS
    opener.addheaders=[('User-Agent','Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.120')]
    try:
        usock =opener.open(url, data.encode())
    except IOError:
        print("error")
        #return()

    jession_id =str(cj).split('~')[1].split(' ')[0]
    send_sms_url = 'http://site24.way2sms.com/smstoss.action?'
    send_sms_data = 'ssaction=ss&Token='+jession_id+'&mobile='+number+'&message='+message+'&msgLen=136'
    opener.addheaders=[('Referer', 'http://site25.way2sms.com/sendSMS?Token='+jession_id)]
    try:
        sms_sent_page = opener.open(send_sms_url,send_sms_data.encode())
    except IOError:
        print("error")

    print("success") 






if __name__ == '__main__':
  arduinoData()


#auth=OAuthHandler(cKey,cSecret)
#auth.set_access_token(aToken,aSecret)
#twitterStream=Stream(auth,listener())
#twitterStream.filter(track=["car"])


