utilityName=WScript.Arguments.Item(0)

'Setting up Database Connection
Set MyConn = CreateObject("ADODB.Connection")
MdbFilePath = "C:\softtag\Automations\Arghya\QueueManager\Queue.mdb"
MyConn.Open "Driver={Microsoft Access Driver (*.mdb)}; DBQ=" & MdbFilePath

Do
	
	SQL_query = "SELECT queue_id, REQUEST_TIME, Request_Details, Command,Requester from Queue "&_
				"where queue_id = (select min(queue_id) from Queue where utility_name='" & utilityName & "');"

	Set RS = MyConn.Execute(SQL_query)
	queueToStart=RS("queue_id")
	queueCommand=RS("Command")
	requestTime=RS("REQUEST_TIME")
	requestDetails=RS("Request_Details")
	requester=RS("Requester")

	Wscript.echo "Running: " & queueToStart & ":" & requester & ":" & requestDetails
	
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
			" values ('" & utilityName & "','" & requestDetails & "','" & queueCommand & "','" & requester & "','" & requestTime & "','" & startTime & "',now());"
	'Wscript.echo SQL_query
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

MyConn.close
Set RS=Nothing
Set MyConn=Nothing