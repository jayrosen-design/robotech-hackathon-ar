#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS 5 // The pin for the OneWire bus
#define TEMPERATURE_PRECISION 12 // The number of bits of resolution for the sensor
#define MEASUREMENT_COUNT 60
#define MEASUREMENT_INTERVAL 1000 // Change to at least 750ms
#define IR_LED 4 // Pin for IR LED
#define IR_PIN 16 // Pin for IR sensor
#define BUTTON_PIN 23 // Pin for button
#define JOY_VRX_PIN 32 // Pin for VRx
#define JOY_VRY_PIN 33 // Pin for VRy
#define JOY_SW_PIN 27  // Pin for SW
bool buttonState = LOW;

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);

void setup() {
  Serial.begin(9600);
  pinMode(IR_LED, OUTPUT);
  pinMode(BUTTON_PIN, INPUT_PULLUP);
  pinMode(JOY_VRX_PIN, INPUT);
  pinMode(JOY_VRY_PIN, INPUT);
  pinMode(JOY_SW_PIN, INPUT_PULLUP);
  sensors.begin();
}

void loop() {
  digitalWrite(IR_LED, HIGH);
  buttonState = digitalRead(BUTTON_PIN);
  if (buttonState == LOW) {
    for (int i = 0; i < MEASUREMENT_COUNT; i++) {
      sensors.requestTemperatures();
      float fahrenheit = sensors.getTempFByIndex(0);
      Serial.print(i);
      Serial.print(": Temperature: ");
      Serial.print(fahrenheit);
      Serial.println(" F");
      int irValue = analogRead(IR_PIN);
      Serial.print("IR Value: ");
      Serial.println(irValue);
      delay(MEASUREMENT_INTERVAL);
    }
  }
  digitalWrite(IR_LED, LOW);
  int joy_vrx = analogRead(JOY_VRX_PIN);
  int joy_vry = analogRead(JOY_VRY_PIN);
  int joy_sw = digitalRead(JOY_SW_PIN);
  Serial.print("VRx: ");
  Serial.print(joy_vrx);
  Serial.print(", VRy: ");
  Serial.print(joy_vry);
  Serial.print(", SW: ");
  Serial.println(joy_sw);
  delay(1000);
}