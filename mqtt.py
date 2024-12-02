import paho.mqtt.client as mqtt

def on_message(client, userdata, msg):
    print(f"Mensaje recibido: {msg.payload.decode()} en el topic {msg.topic}")

client = mqtt.Client()
client.connect("broker.emqx.io", 1883, 60) ## este es nuestro broker
client.subscribe("lab/redes/android") ## este es nuestro topico
client.on_message = on_message
client.loop_forever()

## instalar antes la libreria de paho en la terminal con el comando pip install paho-mqtt (recordar haber instalado python con la opcion marcada add to path para evitar errores)
