@echo off
REM TITLE=アタッシュケース

set toolPath=%~dp0\
set toolClassPath=%toolPath%classes;

start javaw -cp "%toolPath%classes;%toolPath%lib\h2-1.4.193.jar;%toolPath%lib\jackson\jackson-annotations-2.8.7.jar;%toolPath%lib\jackson\jackson-core-2.8.7.jar;%toolPath%lib\jackson\jackson-databind-2.8.7.jar;%toolPath%lib\jdom2-2.0.6.jar;%toolPath%lib\jfoenix-1.1.0.jar;%toolPath%lib\juniversalchardet-1.0.3.jar;%toolPath%lib\old\db2\db2jcc.jar;%toolPath%lib\old\db2\db2jcc4.jar;%toolPath%lib\old\derby\derby.jar;%toolPath%lib\old\derby\derbyclient.jar;%toolPath%lib\old\derby\derbynet.jar;%toolPath%lib\old\derby\derbytools.jar;%toolPath%lib\old\ojdbc14.jar;%toolPath%lib\poi\lib\commons-codec-1.10.jar;%toolPath%lib\poi\lib\commons-collections4-4.1.jar;%toolPath%lib\poi\lib\commons-logging-1.2.jar;%toolPath%lib\poi\lib\junit-4.12.jar;%toolPath%lib\poi\lib\log4j-1.2.17.jar;%toolPath%lib\poi\ooxml-lib\curvesapi-1.04.jar;%toolPath%lib\poi\ooxml-lib\xmlbeans-2.6.0.jar;%toolPath%lib\poi\poi-3.16-beta2.jar;%toolPath%lib\poi\poi-examples-3.16-beta2.jar;%toolPath%lib\poi\poi-excelant-3.16-beta2.jar;%toolPath%lib\poi\poi-ooxml-3.16-beta2.jar;%toolPath%lib\poi\poi-ooxml-schemas-3.16-beta2.jar;%toolPath%lib\poi\poi-scratchpad-3.16-beta2.jar;" com.tokuda.attachecase.gui.Execute
exit
