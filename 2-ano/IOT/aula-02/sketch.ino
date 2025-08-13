// map de pinos
// output
#define LED_RED 2
#define LED_YEL 3
#define LED_GRN 4
#define BTN_RED 10
#define BTN_GRN 11

#define MAX_QUEUE_SIZE 10
#define CMD_SEMAFORO 1
#define CMD_AMARELO 2

// variaveis da fila
int commandQueue[MAX_QUEUE_SIZE];
int queueFront = 0;
int queueRear = 0;
int queueCount = 0;

// controle dos botões
bool lastBtnRedState = LOW;
bool lastBtnGrnState = LOW;
bool btnRedPressed = false;
bool btnGrnPressed = false;

// variavel para controle dos comandos
bool executingCommand = false;

void setup() {
  // serial comms
  // baud rate 9600 bits/s
  Serial.begin(9600);

  // output leds
  pinMode(LED_RED, OUTPUT);
  pinMode(LED_YEL, OUTPUT);
  pinMode(LED_GRN, OUTPUT);

  // btn input
  pinMode(BTN_RED, INPUT);
  pinMode(BTN_GRN, INPUT);

  // initial check
  digitalWrite(LED_GRN, HIGH);
  delay(150);
  digitalWrite(LED_GRN, LOW);
  digitalWrite(LED_YEL, HIGH);
  delay(150);
  digitalWrite(LED_YEL, LOW);
  digitalWrite(LED_RED, HIGH);
  delay(150);
  digitalWrite(LED_RED, LOW);
  delay(1000);
}

void loop() {
  checkButtonPress();
  
  if (!executingCommand && queueCount > 0) {
    executeNextCommand();
  }
  
  if (!executingCommand) {
    defaultAnimation();
  }
}

void checkButtonPress() {
  bool currentBtnRedState = digitalRead(BTN_RED);
  bool currentBtnGrnState = digitalRead(BTN_GRN);
  
  if (currentBtnRedState == HIGH && lastBtnRedState == LOW) {
    addToQueue(CMD_SEMAFORO);
    Serial.println("Comando adicionado");
  }
  
  if (currentBtnGrnState == HIGH && lastBtnGrnState == LOW) {
    addToQueue(CMD_AMARELO);
    Serial.println("Comando adicionado");
  }
  
  lastBtnRedState = currentBtnRedState;
  lastBtnGrnState = currentBtnGrnState;
}

void addToQueue(int command) {
  if (queueCount < MAX_QUEUE_SIZE) {
    commandQueue[queueRear] = command;
    queueRear = (queueRear + 1) % MAX_QUEUE_SIZE;
    queueCount++;
    Serial.print("Fila atual: ");
    Serial.println(queueCount);
  } else {
    Serial.println("Fila cheia, comando ignorado");
  }
}

void executeNextCommand() {
  if (queueCount > 0) {
    int command = commandQueue[queueFront];
    queueFront = (queueFront + 1) % MAX_QUEUE_SIZE;
    queueCount--;
    
    executingCommand = true;
    
    switch (command) {
      case CMD_SEMAFORO:
        Serial.println("Executando semaforo");
        semaforo();
        break;
      case CMD_AMARELO:
        Serial.println("Executando amarelo");
        amarelo();
        break;
    }
    
    executingCommand = false;
    Serial.println("Comando concluído");
  }
}

void defaultAnimation() {
  static unsigned long lastAnimationTime = 0;
  static int animationStep = 0;
  
  unsigned long currentTime = millis();
  
  if (currentTime - lastAnimationTime >= 350) {
    digitalWrite(LED_GRN, LOW);
    digitalWrite(LED_YEL, LOW);
    digitalWrite(LED_RED, LOW);
    
    switch (animationStep) {
      case 0:
        digitalWrite(LED_GRN, HIGH);
        break;
      case 1:
        digitalWrite(LED_YEL, HIGH);
        break;
      case 2:
        digitalWrite(LED_RED, HIGH);
        break;
      case 3:
        delay(150);
        break;
    }
    
    animationStep = (animationStep + 1) % 4;
    lastAnimationTime = currentTime;
  }
}

void semaforo() {
  digitalWrite(LED_GRN, LOW);
  digitalWrite(LED_YEL, LOW);
  digitalWrite(LED_RED, LOW);
  
  digitalWrite(LED_GRN, HIGH);
  delay(3000);
  digitalWrite(LED_GRN, LOW);
  digitalWrite(LED_YEL, HIGH);
  delay(1000);
  digitalWrite(LED_YEL, LOW);
  digitalWrite(LED_RED, HIGH);
  delay(3000);
  digitalWrite(LED_RED, LOW);
}

void amarelo() {
  digitalWrite(LED_GRN, LOW);
  digitalWrite(LED_YEL, LOW);
  digitalWrite(LED_RED, LOW);
  
  for (int i = 0; i < 10; i++) {
    digitalWrite(LED_YEL, HIGH);
    delay(1000);
    digitalWrite(LED_YEL, LOW);
    delay(250);
  }
}
