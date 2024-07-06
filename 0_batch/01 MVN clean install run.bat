@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-22
set M2_HOME=c:\tools\apache-maven-3.9.5
set JARFILE=target\Primordial-jar-with-dependencies.jar
set MAIN_CLASS=kp.Application
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install
%JAVA_HOME%\bin\java -cp %JARFILE% %MAIN_CLASS%
copy ..\large-file-reading-challenge\data_cities\*.csv ..\large-file-reading-challenge\data\city_temperatures.csv
pause
popd