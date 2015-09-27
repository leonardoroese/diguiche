if (checkhost()) {
	document.getElementById("codid").innerHTML = myid;
}

function resumecall(){
	myid = document.getElementById("myid").value;
	myhost = document.getElementById("myhost").value;
	document.getElementById("codid").innerHTML = myid;
}

function callLast(){
	if(myid != null && myid.trim().length > 0){
		document.getElementById("codid").innerHTML = myid;
		var formData = {
				device : myid,
				oper: "C"
			};
		$.ajax({
			url : myhost + "/ws/agent.jsp",
			type : "POST",
			data : formData,
			success : function(data, textStatus, jqXHR) {
				if (data.trim().indexOf("E:") >= 0) {
					alert(data.trim().substring(2));
				} else {
					document.getElementById("thisid").innerHTML = data.trim().substring(2);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Serviço não encontrado, verifique as configurações.");
			}
		});

	}
}

function callNext(){
	if(myid != null && myid.trim().length > 0){
		document.getElementById("codid").innerHTML = myid;
		var formData = {
				device : myid,
				oper: "N"
			};
		$.ajax({
			url : myhost + "/ws/agent.jsp",
			type : "POST",
			data : formData,
			success : function(data, textStatus, jqXHR) {
				if (data.trim().indexOf("E:") >= 0) {
					alert(data.trim().substring(2));
				} else {
					document.getElementById("thisid").innerHTML = data.trim().substring(2);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				alert("Serviço não encontrado, verifique as configurações.");
			}
		});
	
	}
}

setInterval(function() {
	syncDev("A");
}, 5000);