/*
ATMEGA328P -> 16MHz
62.5 ns por instrução

Pinos I/O
0 a 13

0 ou LOW ou false ---> 0V
1 ou HIGH ou true ---> +5V

*/

void setup() { // tudo que é executado ao iniciar o circuito
  // configura pino 0 como saída
  pinMode(0, OUTPUT);
  for (int i = 0; i < 10; i++) {
    digitalWrite(0, HIGH);
    delay(150);
    digitalWrite(0, LOW);
    delay(150);
  }
}

void loop() { // repetição quando o circuito está iniciado
  digitalWrite(0, HIGH);
  delay(250); // intervalo em ms
  digitalWrite(0, LOW);
  delay(250);

}
