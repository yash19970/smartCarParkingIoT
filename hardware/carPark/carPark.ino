const int echoPin = 10;
// defining variables
long duration;
int distance;
int trigPin2 = 3;
int echoPin2 = 2;
int trigPin = 9;
int ledPin = 13;
int ledPin2 = 8;
void setup() {
pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin, INPUT); // Sets the echoPin as an Input
pinMode(ledPin,OUTPUT);
pinMode(ledPin2, OUTPUT);
pinMode(trigPin2, OUTPUT);
pinMode(echoPin2, INPUT);
Serial.begin(9600); // Starts the serial communication
}
int distance2 , distance1;
int flag1=0, flag2=0;
void loop() {
distance1 = SonarSensor(trigPin,echoPin);
if(distance1 <=2){
//Serial.print("1 ");
//Serial.println(distance1);
digitalWrite(ledPin,HIGH);
flag1=1;
delayMicroseconds(100000);
}
else
{
  digitalWrite(ledPin,LOW);
}
distance2 = SonarSensor(trigPin2,echoPin2);
if(distance2 <=2){
//Serial.print("2 ");
//Serial.println(distance2);
digitalWrite(ledPin2,HIGH);
delayMicroseconds(100000);
}
else
{
  digitalWrite(ledPin2,LOW);
}
//here
String readString= "";
while (Serial.available()) {
    delay(3);  
    char c = Serial.read();
    readString += c; 
}

readString.trim();
  if (readString.length() >0) {
    if (readString == "1"){
      Serial.println("switching on");
      digitalWrite(ledPin, HIGH);
      delay(2000);
    }
    if (readString == "2")
    {
      Serial.println("switching off");
      digitalWrite(ledPin2, HIGH);
      delay(2000);
    }

    readString="";
}  


/*APPLY logic: if trigpin is 1 and distance is less than 4 then use sensor 1 else use sensor 2 */

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
