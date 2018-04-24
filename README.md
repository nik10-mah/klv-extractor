# klv-ddf

To extract and decod KLV data from a UDP stream url of MPEG-TS stream files.

### Prerequisites

Install FFMPEG
Install VLC version 2.2.8 from http://download.videolan.org/pub/videolan/vlc/2.2.8/

```
Give examples
```

### Installing

- After cloning the project Import eth project in eclispe as Existing Maven Project.
- Open MainApp.java and change the VLC_LIB_PATH variable to point to you installation
- Run the project as Java Application
- Create  UDP stream from existing mpeg-ts file or .mpg file as shown
```
ffmpeg -re -i "ThreeLightTruckActivities.ts" -map 0 -codec copy  -f mpegts udp://127.0.0.1:2001  -map 0 -codec copy  -f mpegts udp://127.0.0.1:2000
```
 - After that enter UDP url and click start button.


### Thanks
