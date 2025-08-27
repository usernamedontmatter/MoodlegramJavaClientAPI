# Moodlegram server
## Project
This project is client part lib of Moodlegram

API version: 1.0.0

Server API version: 1.0.0

ClientAPI version: 1.1.0


## Moodlegram

Moodlegram is an opensource messanger specializing on security, privacy and fault tolerance

[Tmaou27](https://github.com/Tmaou27) is author of original Moodlegram

[MoodlegramModel](https://github.com/Tmaou27/MoodlgramModel) is original Moodlegram project

## Structure

### IOStream

IOStream class control input-output operations

IOStreamInterface - interface of iostream

IOStream types:
* TCPStream. IO by pure tcp

### IOStreamCreator

IOStreamCreator class generate iostreams

IOStreamCreatorInterface - interface of class that generate streams

IOStreamCreator types:
* TCPStreamCreator. Create TCPStream

### EncryptingLayout

EncryptingLayout is iostream class which is based on other iostream and encrypts traffic

EncryptingLayoutInterface - interface of layout

EncryptingLayout types:
* NoEncryptingLayout. No encrypting

### Auth

Auth class control user authentication

AuthInterface - interface of Auth class

Auth types:
* NoAuth. No auth

### Client

Server class is base library class that realize moodlegram client api