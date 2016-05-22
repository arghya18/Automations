Const DeleteReadOnly = TRUE

Set objFSO = CreateObject("Scripting.FileSystemObject")
objFSO.DeleteFile("E:\QTDemo\*.*"), DeleteReadOnly
objFSO.DeleteFolder("E:\QTDemo\*"), DeleteReadOnly