@echo on
REM @echo off
REM cls
echo %date% 
title Created by Yashwanth

REM resource Monitor
set CATALINA_HOME = "D:\Yashwanth\D Drive\Apache\apache-tomcat-7.0.37"
echo "SetENV.bat :"
echo %CATALINA_HOME%

REM FOR /F "usebackq tokens=5" %%i IN (`netstat -aon ^| find "9090"`) DO taskkill /F /PID %%i

pause

REM https://stackoverflow.com/questions/27333203/xampp-couldnt-start-apache-windows-10
REM https://www.windows-commandline.com/taskkill-kill-process/
REM https://stackoverflow.com/questions/6204003/kill-a-process-by-looking-up-the-port-being-used-by-it-from-a-bat

REM https://stackoverflow.com/a/48756190/5081877

REM ======================

REM Networking Utilities: 
REM To find out which specific process(PID) is using which port:

REM Displays protocol statistics and current TCP/IP network connections.

REM NETSTAT [-a] [-b] [-e] [-f] [-n] [-o] [-p proto] [-r] [-s] [-x] [-t] [interval]

  REM Proto  Local Address          Foreign Address        State           PID
  
REM D:\xampp>netstat -ano | findstr 12516
  REM TCP    0.0.0.0:80             0.0.0.0:0              LISTENING       12516
  REM TCP    0.0.0.0:443            0.0.0.0:0              LISTENING       12516
  REM TCP    [::]:80                [::]:0                 LISTENING       12516
  REM TCP    [::]:443               [::]:0                 LISTENING       12516

REM D:\xampp>netstat -anon | findstr 13944


REM D:\xampp>netstat -aon |find /i "listening" |find "80"
  REM TCP    0.0.0.0:80             0.0.0.0:0              LISTENING       12516
  REM TCP    0.0.0.0:7680           0.0.0.0:0              LISTENING       5792
  REM TCP    0.0.0.0:8081           0.0.0.0:0              LISTENING       13192
  REM TCP    0.0.0.0:49697          0.0.0.0:0              LISTENING       580
  REM TCP    [::]:80                [::]:0                 LISTENING       12516
  REM TCP    [::]:7680              [::]:0                 LISTENING       5792
  REM TCP    [::]:49697             [::]:0                 LISTENING       580

REM D:\xampp>netstat -aon |find "80"
  REM TCP    0.0.0.0:80             0.0.0.0:0              LISTENING       12516
  REM TCP    0.0.0.0:7680           0.0.0.0:0              LISTENING       5792
  REM TCP    0.0.0.0:8081           0.0.0.0:0              LISTENING       13192
  REM TCP    0.0.0.0:49697          0.0.0.0:0              LISTENING       580
  REM TCP    172.16.20.74:52780     172.16.21.151:13111    TIME_WAIT       0
  REM TCP    172.16.20.74:52786     104.120.173.26:80      ESTABLISHED     11732
  REM TCP    172.16.20.74:52787     104.120.173.26:80      ESTABLISHED     11732
  REM TCP    172.16.20.74:52788     104.120.173.26:80      ESTABLISHED     11732
  REM TCP    172.16.20.74:52789     23.74.248.233:80       ESTABLISHED     11732
  REM TCP    [::]:80                [::]:0                 LISTENING       12516
  REM TCP    [::]:7680              [::]:0                 LISTENING       5792
  REM TCP    [::]:49697             [::]:0                 LISTENING       580
  
REM D:\xampp>netstat -a -n -o | find "80"
  REM TCP    0.0.0.0:80             0.0.0.0:0              LISTENING       6392
  REM TCP    0.0.0.0:5040           0.0.0.0:0              LISTENING       6804
  REM TCP    0.0.0.0:7680           0.0.0.0:0              LISTENING       648
  REM TCP    0.0.0.0:8081           0.0.0.0:0              LISTENING       4784
  REM TCP    0.0.0.0:49697          0.0.0.0:0              LISTENING       580
  REM TCP    172.16.20.74:51639     184.84.60.234:80       ESTABLISHED     1784
  REM TCP    172.16.20.74:51642     23.66.43.34:80         ESTABLISHED     1784
  REM TCP    172.16.20.74:51649     151.101.154.2:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51655     182.18.179.81:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51656     182.18.179.80:80       TIME_WAIT       0
  REM TCP    172.16.20.74:51659     13.228.80.206:443      CLOSE_WAIT      6416
  REM TCP    172.16.20.74:51663     117.18.237.29:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51664     13.35.215.127:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51668     13.35.215.20:80        ESTABLISHED     6416
  REM TCP    172.16.20.74:51674     13.35.215.221:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51679     52.71.140.180:443      CLOSE_WAIT      6416
  REM TCP    172.16.20.74:51688     182.18.179.82:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51694     151.101.154.2:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51695     104.120.171.106:80     ESTABLISHED     6416
  REM TCP    172.16.20.74:51696     52.7.22.121:80         CLOSE_WAIT      6416
  REM TCP    172.16.20.74:51697     182.18.179.80:80       ESTABLISHED     6416
  REM TCP    172.16.20.74:51701     107.178.254.65:80      ESTABLISHED     6416
  REM TCP    172.16.20.74:51702     54.173.13.65:80        CLOSE_WAIT      6416
  REM TCP    [::]:80                [::]:0                 LISTENING       6392
  REM TCP    [::]:7680              [::]:0                 LISTENING       648
  REM TCP    [::]:49697             [::]:0                 LISTENING       580
  REM UDP    0.0.0.0:5050           *:*                                    6804
REM ============================================
REM D:\xampp>netstat -ao |find "80"
  REM TCP    0.0.0.0:80             DEVELOPMENT-2:0        LISTENING       12516
  REM TCP    0.0.0.0:7680           DEVELOPMENT-2:0        LISTENING       5792
  REM TCP    0.0.0.0:8081           DEVELOPMENT-2:0        LISTENING       13192
  REM TCP    0.0.0.0:49697          DEVELOPMENT-2:0        LISTENING       580
  REM TCP    172.16.20.74:52805     maa03s21-in-f14:https  ESTABLISHED     6744
  REM TCP    [::]:80                DEVELOPMENT-2:0        LISTENING       12516
  REM TCP    [::]:7680              DEVELOPMENT-2:0        LISTENING       5792
  REM TCP    [::]:49697             DEVELOPMENT-2:0        LISTENING       580

REM D:\xampp>netstat -an |find "80"
  REM TCP    0.0.0.0:80             0.0.0.0:0              LISTENING
  REM TCP    0.0.0.0:7680           0.0.0.0:0              LISTENING
  REM TCP    0.0.0.0:8081           0.0.0.0:0              LISTENING
  REM TCP    172.16.20.74:52805     216.58.197.78:443      ESTABLISHED
  REM TCP    172.16.20.74:52870     137.117.225.87:80      ESTABLISHED
  REM TCP    172.16.20.74:52871     137.117.225.87:80      ESTABLISHED
  REM TCP    172.16.20.74:52872     137.117.225.87:80      ESTABLISHED
  REM TCP    172.16.20.74:52873     137.117.225.87:80      ESTABLISHED
  REM TCP    172.16.20.74:52880     172.16.21.250:49158    TIME_WAIT
  REM TCP    [::]:80                [::]:0                 LISTENING
  REM TCP    [::]:7680              [::]:0                 LISTENING

REM D:\xampp>netstat -on |find "80"
  REM TCP    172.16.20.74:52805     216.58.197.78:443      ESTABLISHED     6744
  REM TCP    172.16.20.74:52870     137.117.225.87:80      ESTABLISHED     6744
  REM TCP    172.16.20.74:52871     137.117.225.87:80      ESTABLISHED     6744
  REM TCP    172.16.20.74:52872     137.117.225.87:80      ESTABLISHED     6744
  REM TCP    172.16.20.74:52873     137.117.225.87:80      ESTABLISHED     6744
  REM TCP    172.16.20.74:52880     172.16.21.250:49158    TIME_WAIT       0

REM ===========================  
REM C:\Users\yashwanth.m.CLICQA\Desktop>tasklist

REM Image Name                     PID Session Name        Session#    Mem Usage
REM ========================= ======== ================ =========== ============
REM System Idle Process              0 Services                   0          8 K
REM System                           4 Services                   0     20,992 K
REM Registry                        96 Services                   0     18,668 K
REM smss.exe                       568 Services                   0        628 K
REM csrss.exe                      860 Services                   0      3,180 K

REM =================

REM D:\xampp>taskkill /f /pid 6392
REM SUCCESS: The process with PID 6392 has been terminated.

REM D:\xampp>taskkill /f /pid 6416
REM SUCCESS: The process with PID 6416 has been terminated.

REM D:\xampp>taskkill /f /pid 580
REM ERROR: The process with PID 580 could not be terminated.
REM Reason: This is critical system process. Taskkill cannot end this process.