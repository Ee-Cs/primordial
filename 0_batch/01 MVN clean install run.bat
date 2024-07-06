@echo off
set JAVA_HOME=C:\PROGRA~1\JAVA\JDK-22
set M2_HOME=c:\tools\apache-maven-3.9.5
::set JARFILE=target\Primordial-1.0.0-SNAPSHOT.jar
set JARFILE=target\Primordial-jar-with-dependencies.jar
set MAINCLASS=kp.Application
pushd %cd%
cd ..
call %M2_HOME%\bin\mvn clean install
pause

%JAVA_HOME%\bin\java -cp %JARFILE% %MAINCLASS%
pause
popd