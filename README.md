# Auto Surveillance Mobile Robot
## Objective
The project was aimed to build a robot that is capable of patrolling homes, detecting intruders, and pushing notifications to the user’s phones, along with a picture of the intruder. The user can choose to see a live feed from the bot’s perspective, and drive the bot to manually. This could, potentially, replace an array of CCTV cameras, and function as a portable security system, that can be deployed wherever it is required.

## Approach:
A RaspberryPi Zero W, that is connected to the home’s wifi powers the bot, as it moves through the area, using an ultrasound sensor to detect, and avoid obstacles. A PIR sensor mounted on the bot detects intruders, and activates the camera, that then clicks a picture of the intruder, and uploads it to firebase. This triggers a notification on the owner's android phone, which, upon opening, displays the picture of the intruder.

## Future Scope
Further, the robot can be made capable of mapping areas, and communicating with another bot of the same type, to cover even larger spaces. This wll require knowledge in the domains of Mobile Robotics, and Swarm Communication.
