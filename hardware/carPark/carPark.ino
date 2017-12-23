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

void loop() {
// Prints the distance on the Serial Monitor
//Serial.println("Distance: ");
//Serial.println(distance);
distance1 = SonarSensor(trigPin,echoPin);
if(distance1 <=9){
Serial.print("1 ");
Serial.println(distance1);
digitalWrite(ledPin,HIGH);
delayMicroseconds(100000);
}
else
{
  digitalWrite(ledPin,LOW);
}
distance2 = SonarSensor(trigPin2,echoPin2);
if(distance2 <=4){
Serial.print("2 ");
Serial.println(distance2);
digitalWrite(ledPin2,HIGH);
delayMicroseconds(100000);
}
else
{
  digitalWrite(ledPin2,LOW);
}
/*else if(distance >17 && distance< 20){
Serial.println(distance);
digitalWrite(ledPin,HIGH);
delayMicroseconds(100000);
}*/

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
