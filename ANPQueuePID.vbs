utilityName="ANP"
utilityScriptPath="C:\softtag\Automations\Arghya\QueueManager\ANPStart.vbs"

utilityCMD="'cscript " & chr(34) & utilityScriptPath & chr(34) & " " & chr(34) & WScript.Arguments.Item(0) & chr(34) & "'"
requester="'Arghya.Saha'"
requestDetails = "'ANP 13/04/15 TABLE ALL DAY0'"

queueManagerPath="C:\softtag\Automations\Arghya\QueueManager\"

'Setting up Database Connection
Set MyConn = CreateObject("ADODB.Connection")
MdbFilePath = "C:\softtag\Automations\Arghya\QueueManager\Queue.mdb"
MyConn.Open "Driver={Microsoft Access Driver (*.mdb)}; DBQ=" & MdbFilePath

'Validate the request before putting into Queue by passing email key/subject line to another script file 
'-----------------------------------------------------------------------
'Code for Validation and generating acknowledgement number will go here
'-----------------------------------------------------------------------


'Insert into Queue if not already exists
On Error Resume Next
SQL_query = "insert into Queue(Utility_Name,Request_Details,Command,Requester,Request_Time) "&_
			"values('" & utilityName & "'," & requestDetails & "," & utilityCMD & "," & requester & ",now());"
MyConn.Execute(SQL_query)
insertErrNum = Err.Number
Wscript.echo "Insert Status" & insertErrNum
On Error GoTo 0

'Check if the Queue PID is already Running
SQL_query = "select  QUEUE_PID from UtilityDetails "&_
				"where Utility_Name = '"& utilityName & "';"
Set RS = MyConn.Execute(SQL_query)
queuePID = RS("QUEUE_PID")

strComputer = "."
Set objWMIService = GetObject ("winmgmts:\\" & strComputer & "\root\cimv2")

Set colProcessList = objWMIService.ExecQuery ("Select * from Win32_Process Where ProcessID = " & queuePID)
WScript.echo colProcessList.Count

If (colProcessList.Count <> 0) Then

	WScript.echo "Queue is already processing"
	WScript.Quit

End If

'Check the Lock file before processing the Queue
Set fso = CreateObject("Scripting.FileSystemObject")
If (fso.FileExists(queueManagerPath & utilityName & "Lock.txt")) Then
	Wscript.echo "Queue is already running, Quiting"
    Wscript.Quit
End If

Set WShell = CreateObject("wscript.Shell")
queueExecuter="cscript32 C:\softtag\Automations\Arghya\QueueManager\ExecuteQueue.vbs " & utilityName
WScript.echo queueExecuter
Set oExec=WShell.Exec(queueExecuter) 
queuePID=oExec.ProcessID

SQL_query = "UPDATE UtilityDetails "&_
				"SET  QUEUE_PID = '" & queuePID & "' where Utility_Name='" & utilityName & "';"
MyConn.Execute(SQL_query)

'Do
'WScript.echo oExec.StdOut.ReadLine
'Loop While oEXEc.Status=0
WScript.Sleep 2000
'WScript.echo oExec.StdOut.ReadAll & oExec.StdErr.ReadAll



'Closing database Connection
MyConn.close
Set RS=Nothing
Set MyConn=Nothing