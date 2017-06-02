#include "Wire.h"
#include "I2Cdev.h"
#include "MPU6050.h"  
#include <SoftwareSerial.h>
#include <stdlib.h>
 
SoftwareSerial BTSerial(10,11); //Connect HC-06. Use your (TX, RX) settings

MPU6050 accelgyro;
 
int16_t ax, ay, az;
int16_t gx, gy, gz;
 
int accel_reading;
int accel_corrected;
int accel_offset = 200;
float accel_angle;
float accel_scale = 1;
 
int gyro_offset = 151;
double dgy_x,deg;
int angle;
float last_read_time;
float last_x_angle,last_y_angle,last_z_angle;

char buffer[256] = {0};

char char_value;
 
void setup()  
{
  Wire.begin();
  Serial.begin(9600);
  Serial.println("Hello!");
  BTSerial.begin(9600);  // set the data rate for the BT port
  accelgyro.initialize();
}
 
void loop()
{
  // 가속도 및 자이로 x,y,z 축에 대한 데이터 받아오기
  // 가속도 값은 -16800 ~ 16800 범위를 -90 ~ 90으로 바꿔줌
  accelgyro.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);
  accel_reading = ay;
  accel_corrected = accel_reading - accel_offset;
  accel_corrected = map(accel_corrected, -16800, 16800, -90, 90);
  accel_corrected = constrain(accel_corrected, -90, 90);
  accel_angle = (float)(accel_corrected * accel_scale);

  // 가속도만을 이용한 각도 출력
  Serial.print("Acc angle : ");
  Serial.print(accel_angle);
  Serial.print("\t");
 
  deg = atan2(ax, az) * 180 / PI;     // rad to deg
  
  // 자이로+가속도 조합한 각도
  dgy_x = gy / gyro_offset;
  angle = (int)((0.95 * (angle + (dgy_x * 0.001))) + (0.05 * deg));

  // 자이로 가속도 둘 다 이용한 각도 출력
  Serial.print("Filter angle : ");
  Serial.println(angle) ;
  sprintf(buffer,"%d\n",angle);
  Serial.print(buffer);
  
  // Serial –> Data –> BT
  //if (BTSerial.available()) {
    //BTSerial.write(Serial.read());
    //BTSerial.print("Acc angle : ");
    //BTSerial.print(accel_angle);
    //BTSerial.print("\tFilter angle : ");
    //BTSerial.println(angle);
    BTSerial.write(buffer);
  //}
  //delay(30);
}
