utilityName=WScript.Arguments.Item(0)
utilityMaxTime=30


'Setting up Database Connection
Set MyConn = CreateObject("ADODB.Connection")
MdbFilePath = "C:\softtag\Automations\Arghya\QueueManager\Queue.mdb"
MyConn.Open "Driver={Microsoft Access Driver (*.mdb)}; DBQ=" & MdbFilePath

Do
	
	SQL_query = "SELECT queue_id, REQUEST_TIME, Request_Details, Command,Requester,Start_Time from Queue "&_
				"where queue_id = (select min(queue_id) from Queue where utility_name='" & utilityName & "');"

	Set RS = MyConn.Execute(SQL_query)
	queueToStart=RS("queue_id")
	queueCommand=RS("Command")
	requestTime=RS("REQUEST_TIME")
	requestDetails=RS("Request_Details")
	requester=RS("Requester")
	startTime=RS("Start_Time")

	if(startTime <> "") then
		Wscript.Echo  "inside startTime<>null"
		executionTime = DateDiff("s",RS("Start_Time"),now())
		'Wscript.Echo executionTime
		if( executionTime < utilityMaxTime ) then
			Wscript.Echo "Waiting for remaining Minutes: " & utilityMaxTime-executionTime
			Wscript.Sleep (utilityMaxTime-executionTime)*1000
		end if

	end if


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