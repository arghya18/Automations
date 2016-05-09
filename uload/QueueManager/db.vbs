Set MyConn = CreateObject("ADODB.Connection")
    MdbFilePath = "C:\softtag\Automations\Arghya\Queue\Queue.mdb"
    MyConn.Open "Driver={Microsoft Access Driver (*.mdb)}; DBQ=C:\softtag\Automations\Arghya\Queue\Queue.mdb"
	
    SQL_query = "SELECT * "&_
                   "FROM QueueLog ;"
  
    Set RS = MyConn.Execute(SQL_query)
    WHILE NOT RS.EOF
       Wscript.echo "User" & RS("Utility_Name") &_
                 " Count" & RS("Command")
       RS.MoveNext
    WEND 
    RS.Close
    set RS = nothing  
    MyConn.close
    set MyConn = nothing
 