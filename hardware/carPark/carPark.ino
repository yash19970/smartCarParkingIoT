const int echoPin = 10;
// defining variables
long duration;
int distance;
int trigPin2 = 3;
int echoPin2 = 2;
int trigPin = 9;
int ledPin = 13;
int ledPin2 = 8;
int motorPin1 =5;
int motorPin2 =6;
void setup() {
pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin, INPUT); // Sets the echoPin as an Input
pinMode(ledPin,OUTPUT);
pinMode(ledPin2, OUTPUT);
pinMode(trigPin2, OUTPUT);
pinMode(echoPin2, INPUT);
pinMode(motorPin1, OUTPUT); 
pinMode(motorPin2, OUTPUT); 

Serial.begin(9600); // Starts the serial communication
}
int distance2 , distance1;
int flag1=0, flag2=0;
int checkFlag1=0,checkFlag2=0;
void loop() {
distance1 = SonarSensor(trigPin,echoPin);
if(distance1 <=1){
Serial.print("1 ");
Serial.println(distance1);
digitalWrite(ledPin,HIGH);
flag1=1;
delayMicroseconds(100000);
}else if(checkFlag1==1){
    digitalWrite(ledPin,HIGH);
}
else
{
  digitalWrite(ledPin,LOW);
}

distance2 = SonarSensor(trigPin2,echoPin2);
if(distance2 <=1){
Serial.print("2 ");
Serial.println(distance2);
digitalWrite(ledPin2,HIGH);
delayMicroseconds(100000);
}
else if(checkFlag2==1){
    digitalWrite(ledPin2,HIGH);
}
else
{
  digitalWrite(ledPin2,LOW);
}
//here
String readString= "";
while (Serial.available()) {
    //Serial.print(readString);
    delay(10);  
    char c = Serial.read();
    readString += c; 
    
}

readString.trim();
  if (readString.length() >0) {
    Serial.print(readString);
    //for snesor 1
    if (readString == "1yes"){
      Serial.println("switching on");
      String readString1= "";
      digitalWrite(ledPin, HIGH);
      //rotateLeft(250,5000);
      checkFlag1=1;
      delay(2000);
    }else if(readString == "1no" || readString == "1no1no" ){
      digitalWrite(ledPin, LOW);
      rotateLeft(250,5000);
      checkFlag1=0;
      delay(2000);
    }else if(readString == "yes1"){
      Serial.print("inside");
      rotateLeft(250,5000);
    }
    // for sensor2
    if (readString == "2yes")
    {
      Serial.println("switching off");
      digitalWrite(ledPin2, HIGH);
      delay(2000);
      checkFlag2=1;
    }else if(readString == "2no" || readString == "2no2no"){
      digitalWrite(ledPin, LOW);
      checkFlag2=0;
      delay(2000);
    }

    readString="";
}  


/*APPLY logic: if trigpin is 1 and distance is less than 4 then use sensor 1 else use sensor 2 */

}

void rotateLeft(int speedOfRotate, int length){
  analogWrite(motorPin1, speedOfRotate); //rotates motor
  digitalWrite(motorPin2, LOW);    // set the Pin motorPin2 LOW
  delay(length); //waits
  digitalWrite(motorPin1, LOW);    // set the Pin motorPin1 LOW
}


int SonarSensor(int trigPin,int echoPin)
{
digitalWrite(trigPin, LOW);
delayMicroseconds(2);
digitalWrite(trigPin, HIGH);
delayMicroseconds(10);
digitalWrite(trigPin, LOW);
duration = pulseIn(echoPin, HIGH);
distance = (duration/2) / 29.1;
return distance;
}
