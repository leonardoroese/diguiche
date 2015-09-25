var myid = document.getElementById('myid').value;
var myhost = document.getElementById('myhost').value;

function checkhost(){
	if (myid.trim().length <= 0 || myhost.trim().length <= 0) {
		var strhtml = "<div style='width: 100%; text-align: center;'>";
		strhtml = strhtml + "<strong>Configuração</strong><br><br>";
		strhtml = strhtml
				+ "<label class='conflabel'>Host: </label><input class='confinput' type='text' name='cfgHost' id='cfgHost' value='"
				+ myhost + "' /><br><br>";
		strhtml = strhtml
				+ "<label class='conflabel'>Dispositivo(ID): </label><input class='confinput' type='text' name='cfgId' id='cfgId' value='"
				+ myid + "' />";
		strhtml = strhtml
				+ "<br><br><input class='confsubmit' type='submit' name='subCFG' id='subCFG' value='OK' "
				+ "onclick=\"document.getElementById('myid').value=document.getElementById('cfgId').value; "
				+ "document.getElementById('myhost').value=document.getElementById('cfgHost').value;"
				+ "resumecall();"
				+ "document.getElementById('msgbox').style.display='none';"
				+ "\"/>";

		strhtml = strhtml + "</div>";

		document.getElementById('msginner').innerHTML = strhtml;
		document.getElementById('msgbox').style.display = "block";
		return false;
	} else {
		return true;
	}
}