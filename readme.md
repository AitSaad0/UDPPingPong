UDP Ping-Pong Project
Overview

This project was created to practice UDP connections between a client and a server in Java. It simulates a simple “ping-pong” communication:

The client sends a message (“Ping”) to the server.

The server receives the message, prints it, and sends a response (“Pong”) back to the client.

Project Structure

The project contains three main classes:

Server

Uses a thread pool to handle multiple packets concurrently.

Each time a packet is received, a task is submitted to a thread:

Print the received message.

Send a response back to the client.

Client

Sends a message to the server.

Receives responses from the server and prints them.

Uses a dedicated thread for receiving packets to ensure continuous listening.

PingPong (Main class)

Starts both the server and client.

Handles user input for sending messages or quitting.

Features

Demonstrates UDP socket programming in Java.

Uses multithreading and thread pools on the server side.

Separates sending and receiving logic to avoid conflicts and ensure predictability.

Provides a simple interactive interface for the client.

How to Run

Compile all classes:

javac UDPPingPong/*.java


Start the server:

java UDPPingPong.PingPongServer


Start the client in a separate terminal:

java UDPPingPong.PingPongClient


In the client, type commands:

send → sends a “Ping” message to the server.

quit → exits the client.

Notes

UDP is connectionless, so packets may arrive out of order or be lost.

The server thread pool ensures multiple clients can be served concurrently.

Buffers for sending and receiving are kept separate to avoid race condition