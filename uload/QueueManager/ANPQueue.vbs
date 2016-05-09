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

'Check if any task is there in Queue
SQL_query = "select count(*) as QUEUE_COUNT from Queue "&_
			"where Utility_Name = '"& utilityName & "';"
Set RS = MyConn.Execute(SQL_query)
queueCount = RS("QUEUE_COUNT")
Wscript.echo queueCount

'Insert into Queue
On Error Resume Next
SQL_query = "insert into Queue(Utility_Name,Request_Details,Command,Requester,Request_Time) "&_
			"values('" & utilityName & "'," & requestDetails & "," & utilityCMD & "," & requester & ",now());"
MyConn.Execute(SQL_query)
insertErrNum = Err.Number
Wscript.echo "Insert Status" & insertErrNum
On Error GoTo 0

'If any task is already in progress and any new task comes then Add the current task to Queue and Exit
If ((queueCount <> 0) And (insertErrNum <> -2147217900) ) Then
	Wscript.echo "Send In-Queue Email"
	Wscript.Quit
End If

'If the unfinifshed task comes again after machine restart, it will restart the queue
Set fso = CreateObject("Scripting.FileSystemObject")
If (insertErrNum = -2147217900) Then
	
	SQL_query = "select count(*) as QUEUE_COUNT from Queue "&_
			"where Not(Start_Time) is Null and Command = "& utilityCMD & ";"
	Set RS = MyConn.Execute(SQL_query)
	queueCount = RS("QUEUE_COUNT")
	
	If(queueCount=1) Then
		fso.DeleteFile(queueManagerPath & utilityName & "Lock.txt")
		Wscript.echo "Queue Restarted!"
	Else
		Wscript.echo "Queue is not Restarted!"
		Wscript.Quit
	End If
	
End If

'Check the Lock file before processing the Queue
Set fso = CreateObject("Scripting.FileSystemObject")
If (fso.FileExists(queueManagerPath & utilityName & "Lock.txt")) Then
	Wscript.echo "Queue is already running, Quiting"
    Wscript.Quit
End If

'Creating Lock file before processing the Queue
Set fso = CreateObject("Scripting.FileSystemObject")
fso.CreateTextFile queueManagerPath & utilityName & "Lock.txt", True
Wscript.echo "Lock Created"

'If any task is not in progress execute the current task and other future task in FIFO basis
Do
	
	SQL_query = "SELECT queue_id, REQUEST_TIME, Request_Details, Command from Queue "&_
				"where queue_id = (select min(queue_id) from Queue where utility_name='" & utilityName & "');"

	Set RS = MyConn.Execute(SQL_query)
	queueToStart=RS("queue_id")
	queueCommand=RS("Command")
	requestTime=RS("REQUEST_TIME")
	requestDetails=RS("Request_Details")

	Wscript.echo "Running: " & queueToStart
	
	startTime=now()
	'Update start time of the current task before executing
	SQL_query = "UPDATE Queue "&_
				"SET  Start_Time = '" & startTime & "' where queue_id=" & queueToStart & ";"
	MyConn.Execute(SQL_query)

	'Starting the execution
	Set WShell = CreateObject("wscript.Shell")
	WShell.Run queueCommand,0,true
	
	'Insertion of completed task to QueueLog before deleting from Queue
	SQL_query = "insert into QueueLog(Utility_Name, Request_Details, Command,Requester,Request_Time, Start_Time, End_Time )"&_
			" values ('" & utilityName & "','" & requestDetails & "','" & queueCommand & "'," & requester & ",'" & requestTime & "','" & startTime & "',now());"
	MyConn.Execute(SQL_query)
	
	'Delete the executed task from Queue
	SQL_query = "DELETE FROM Queue "&_
				"where queue_id=" & queueToStart & ";"
	MyConn.Execute(SQL_query)
	
	'Check if any task is there in Queue
	SQL_query = "select count(*) as QUEUE_COUNT from Queue "&_
				"where Utility_Name = '"& utilityName & "';"
	Set RS = MyConn.Execute(SQL_query)
	queueCount = RS("QUEUE_COUNT")		

Loop While queueCount <> 0

'Deleting the lock file after processing the Queue
Set fso = CreateObject("Scripting.FileSystemObject")
fso.DeleteFile(queueManagerPath & utilityName & "Lock.txt")
Wscript.echo "Lock Deleted"

'Closing database Connection
MyConn.close
Set RS=Nothing
Set MyConn=Nothing