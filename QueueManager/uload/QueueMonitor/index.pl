#!C:/xampp/perl/bin/perl.exe

use 5.010;
use CGI;
use strict;
#use warnings;
use DBI;

my $cgi = new CGI;
my %input;
my $key;
for $key ( $cgi->param() ) {
	$input{$key} = $cgi->param($key);
}

my $sth;
my $sqlstatement;
my $dbh = DBI->connect(
    'dbi:ODBC:driver=microsoft access driver (*.mdb);' .
    'dbq=C:/softtag/Automations/Arghya/QueueManager/Queue.mdb'
)  or die $DBI::errstr;


#To fetch all the active utilities from Database
sub fetchutilities{
	$sqlstatement = 'select UTILITY_ID, UTILITY_NAME from UTILITYDETAILS where ACTIVE= true order by UTILITY_ID';
	$sth = $dbh->prepare($sqlstatement);
	$sth->execute() || 
       die "Could not execute SQL statement ... maybe invalid?"; 
	while (my @row = $sth->fetchrow_array) {
		if($row[1] eq $input{utilityselected}){
			print "<option selected value=\"".$row[1]."\">".$row[1]."</option>\n";
		}
		else{
			print "<option value=\"".$row[1]."\">".$row[1]."</option>\n";
		}
	}

	if($input{utilityselected} eq "0"){
		print "<option selected value=\"0\">ALL</option>\n";
	}
	else{
		print "<option value=\"0\">ALL</option>\n";
	}
}


#To fetch the tasks from the Queue
my $taskStatus;
sub fetchtasks{
	if($input{utilityselected} ne "-1"){
		my $rownum=1;

		if($input{utilityselected} eq "0"){
			$sqlstatement = 'select Requester, Request_Time, Start_Time, UTILITY_NAME, Request_Details from QUEUE order by UTILITY_NAME,Queue_ID asc';
			$sth = $dbh->prepare($sqlstatement);
			$sth->execute() || 
       		die "Could not execute SQL statement ... maybe invalid?";
       	}
		else{
			$sqlstatement = 'select Requester, Request_Time, Start_Time,UTILITY_NAME,Request_Details from QUEUE where UTILITY_NAME = ? order by Queue_ID asc';
			$sth = $dbh->prepare($sqlstatement);
			$sth->execute($input{utilityselected}) || 
       		die "Could not execute SQL statement ... maybe invalid?";
       	}
		
       	while (my @row = $sth->fetchrow_array) {
			if($row[2] ne ""){
				$taskStatus= "In Progress";
				print "<tr><td>$rownum</td><td>$row[3]</td><td>$row[4]</td><td>$row[0]</td><td>$row[1]</td><td>$row[2]</td><td style=\"background-color:Green\">$taskStatus</td></tr>\n";
			}
			else{
				$taskStatus= "In Queue";
				print "<tr><td>$rownum</td><td>$row[3]</td><td>$row[4]</td><td>$row[0]</td><td>$row[1]</td><td></td><td style=\"background-color:Yellow\">$taskStatus</td></tr>\n";
			} 
			$rownum=$rownum+1;
		}
	}

}

print qq(Content-type: text/html\n
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
    	<title>Queue Monitor</title>
    	<style type="text/css">
    		.textbox
		     {
		         border:1px solid black;
		         width:300px;height:30px; 
		         text-align:center; 
		         font-family:'Calibri';
		         font-size:medium;
		         color:black;
		     }

		     .text
		     {
		         font-family:'Calibri'; 
		         font-size:large; 
		         font-weight:bold;
		     }
		     table,th,tr,td
		     {
		     	border:1px solid black;
		     	font-family:'Calibri';
		        font-size:medium;
		        color:black;
		        text-align: center;

		     }
		     th, td {
		     	padding-left: 10px;
		     	padding-right: 10px;
		     }

		</style>
		<noscript>You need Javascript enabled for this to work</noscript>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
		<script type="text/javascript">
		    jQuery(document).ready(function () {
		        if (jQuery("#utilityselected").val() == -1){
		            jQuery("#tasktable").hide();
		        }
		    });
		</script>
	</head>

	<body style="margin:0px 0px 0px 0px;">
		<div style="background-color:#22EFFF; border-bottom:1px solid black; position:absolute; top:0px; left:0px; height:60px; width:100%; ">
        	<h1 style="text-align:center; background-color:#22EFFF; font-family:'Calibri';">
           	Barclays UK IIT - Utilities Queue Monitoring System
        	</h1>
		</div>
			
		<form method="POST" action="index.pl" name="form1">
			<div id="dropdown" style="position:absolute; top:20%; left:10%;">
				<label class="text">Select an Utility</label><br/>
				<select class="textbox" id="utilityselected" name="utilityselected" onchange="this.form.submit()">
		           	<option style="text-align:center;" value="-1">Choose Utility</option>
);
fetchutilities();
print qq(           	
		        </select>
			</div>
        
			<div id="tasktable" style="position:absolute; top:35%; left:15%;">
				<table class="table" style="width:100%">
				  <caption class="text">Task Status List</caption>
				  <tr>
				    <th>Serial No.</th>
				    <th>Utility Name</th> 
				    <th>Request Details</th>
				    <th>Requester</th>
				    <th>Request Time</th>
				    <th>Start Time</th>
				    <th>Status</th>
				  </tr>
);
fetchtasks();
print qq(      
				</table>
				<br/>
				<input class="otherbutton" style="width:130px; height:25px " id="refreshbutton" name="refreshbutton" type="submit" value="Refresh List" onclick="this.form.submit()" />
			</div>
		</form>

	</body>
</html>);

$dbh->disconnect;
