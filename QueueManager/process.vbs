Set WShell = CreateObject("wscript.Shell")
command="cscript C:\softtag\Automations\Arghya\QueueManager\ANPStart.vbs"
Set oExec=WShell.Exec(command)
Set oExec2=WShell.Exec(command)
pid=oExec.ProcessID
pid2=oExec2.ProcessID
wscript.echo pid & "," & pid2

strComputer = "."
Set objWMIService = GetObject ("winmgmts:\\" & strComputer & "\root\cimv2")

Set colProcessList = objWMIService.ExecQuery ("Select * from Win32_Process Where ProcessID = " & pid)
wscript.echo colProcessList.Count

Wscript.Sleep 10000

Set colProcessList = objWMIService.ExecQuery ("Select * from Win32_Process Where ProcessID = " & pid2)
wscript.echo colProcessList.Count