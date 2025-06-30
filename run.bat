@echo off

mvn -q clean compile
IF %ERRORLEVEL% NEQ 0 (
    echo Compile failed
    exit /b 1
)


mvn -q dependency:build-classpath -Dmdep.outputFile=cp.txt
IF %ERRORLEVEL% NEQ 0 (
    echo Failed to build classpath
    exit /b 1
)


set /p CP=<cp.txt
set CP=%CP%;target\classes


java -cp "%CP%" Main %*
