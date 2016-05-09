Randomize
utilityName="ANP"
utilityCMD="'cscript ANPStart.vbs " & WScript.Arguments.Item(0) & "'"
requester="'Arghya.Saha'"
queueManagerPath="C:\softtag\Automations\Arghya\QueueManager\"

'Setting up Database Connection
Set MyConn = CreateObject("ADODB.Connection")
   MdbFilePath = "C:\softtag\Automations\Arghya\QueueManager\Queue.mdb"
   MyConn.Open "Driver={Microsoft Access Driver (*.mdb)}; DBQ=" & MdbFilePath

'Check if any task is there in Queue
SQL_query = "select count(*) as QUEUE_COUNT from Queue "&_
			"where Utility_Name = '"& utilityName & "';"
Set RS = MyConn.Execute(SQL_query)
queueCount = RS("QUEUE_COUNT")
Wscript.echo queueCount


'Insert into Queue
On Error Resume Next
SQL_query = "insert into Queue(Utility_Name,Command,Requester,Request_Time) "&_
			"values('" & utilityName & "'," & utilityCMD & "," & requester & ",now());"
MyConn.Execute(SQL_query)
insertErrNum = Err.Number
Wscript.echo "Insert Status" & insertErrNum
On Error GoTo 0

'If any task is already in progress and any new task comes then Add the current task to Queue and Exit
If ((queueCount <> 0) And (insertErrNum <> -2147217900) ) Then
	Wscript.echo "Send In-Queue Email"
	Wscript.Quit
End If

'If old tasks are resent after machine restart, it will restart the queue
Set fso = CreateObject("Scripting.FileSystemObject")
If (insertErrNum = -2147217900) Then

	If(Not fso.FileExists(queueManagerPath & utilityName & "Restart.txt")) Then
		fso.CreateTextFile queueManagerPath & utilityName & "Restart.txt", True
		fso.DeleteFile(queueManagerPath & utilityName & "Lock.txt")
		Wscript.echo "Queue Restarted!"
	Else
		Wscript.echo "Queue is Already Restarted!"
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

'If any task is not in progress execute the task and other future task in FIFO basis
Do
	SQL_query = "SELECT queue_id, REQUEST_TIME, Command from Queue "&_
				"where queue_id = (select min(queue_id) from Queue where utility_name='" & utilityName & "');"

	Set RS = MyConn.Execute(SQL_query)
	queueToStart=RS("queue_id")
	queueCommand=RS("Command")
	requestTime=RS("REQUEST_TIME")
	
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
	SQL_query = "insert into QueueLog(Utility_Name,Command,Requester,Request_Time, Start_Time, End_Time )"&_
			" values ('" & utilityName & "','" & queueCommand & "'," & requester & ",'" & requestTime & "','" & startTime & "',now());"
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

Set fso = CreateObject("Scripting.FileSystemObject")
fso.DeleteFile(queueManagerPath & utilityName & "Lock.txt")
Wscript.echo "Lock Deleted"

If(fso.FileExists(queueManagerPath & utilityName & "Restart.txt")) Then
	fso.DeleteFile(queueManagerPath & utilityName & "Restart.txt")
	Wscript.echo "Restart Flag deleted"
End If
'Closing database Connection
MyConn.close
Set RS=Nothing
Set MyConn=Nothing