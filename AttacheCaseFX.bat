@echo off
REM TITLE=アタッシュケース

set toolPath=%~dp0\
set toolClassPath=%toolPath%classes;

start javaw -cp "%toolPath%classes;%toolPath%lib\jfoenix.jar;%toolPath%lib\poi\lib\commons-codec-1.10.jar;%toolPath%lib\poi\lib\commons-collections4-4.1.jar;%toolPath%lib\poi\lib\commons-logging-1.2.jar;%toolPath%lib\poi\lib\junit-4.12.jar;%toolPath%lib\poi\lib\log4j-1.2.17.jar;%toolPath%lib\poi\ooxml-lib\curvesapi-1.04.jar;%toolPath%lib\poi\ooxml-lib\xmlbeans-2.6.0.jar;%toolPath%lib\poi\poi-3.16-beta2.jar;%toolPath%lib\poi\poi-examples-3.16-beta2.jar;%toolPath%lib\poi\poi-excelant-3.16-beta2.jar;%toolPath%lib\poi\poi-ooxml-3.16-beta2.jar;%toolPath%lib\poi\poi-ooxml-schemas-3.16-beta2.jar;%toolPath%lib\poi\poi-scratchpad-3.16-beta2.jar;" com.tokuda.attachecase.gui.Execute
exit
