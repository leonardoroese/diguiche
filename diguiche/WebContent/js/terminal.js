if (checkhost()) {
	document.getElementById("codid").innerHTML = myid;
}

function resumecall() {
	myid = document.getElementById("myid").value;
	myhost = document.getElementById("myhost").value;
}

function callNew(tipo) {
	if (myid != null && myid.trim().length > 0) {
		var formData = {
			device : myid,
			tipo : tipo
		};
		$.ajax({
			url : myhost + "/ws/terminal.jsp",
			type : "POST",
			data : formData,
			success : function(data, textStatus, jqXHR) {
				if (data.trim().indexOf("E:") >= 0) {
					alert(data.trim().substring(2));
				} else {
					document.getElementById("thisid").innerHTML = data.trim()
							.substring(2);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Serviço não encontrado, verifique as configurações.");
			}
		});

	}
}

setInterval(function() {
	syncDev("T");
}, 5000);