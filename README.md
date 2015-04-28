# QRLang-GUI
The QRLangGUI is a wrapper for the QRLang Compressor. It build a swing GUI around the compressor for ease of use and automation QR code generation.

## What is QRLang
QRLang is a Game Development language and platform built upon the use of QR codes. A QR Code is a 2 dimensional barcode with an extremely limited amount of space available for storing data. The QRLang language is a very high level, highly compressible scripting language designed to fit an entire game onto a single QR code. There are two forms of users for the app, the development user who writes the games and the end user who plays them.

##Using the GUI
The GUI works as follows :

![Alt text](http://i.imgur.com/uxF104T.png)

1.	The input area for QRLang code. This is a standard text area which the user pastes QRLang Code.
2.	The output area for the compressed text.
3.	The copy text button. This copies the output compressed code to the clipboard.
4.	The copy qrcode button. This copies the output qr code to the clipboard.
5.	The save qrcode button. This gives a save dialog window allowing the user to save the QR code on their machine.
6.	The compress button activates the compression method on the users code. The compressed code is then displayed as text in the output and as a QRcode.
7.	The display section for the QR code.


## Requires
QRLang-Compressor https://github.com/Qoramas/QRLang-Compressor
Zxing-Core https://github.com/zxing/zxing/
