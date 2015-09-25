var curkey = "";
var oldkeys;

if (checkhost()) {
	// Check
	resumecall();
}

var html5_audiotypes = {
	"mp3" : "audio/mpeg",
	"mp4" : "audio/mp4",
	"ogg" : "audio/ogg",
	"wav" : "audio/wav"
}

function createsoundbite(sound) {
	var html5audio = document.createElement('audio')
	if (html5audio.canPlayType) { // check support for HTML5 audio
		for (var i = 0; i < arguments.length; i++) {
			var sourceel = document.createElement('source')
			sourceel.setAttribute('src', arguments[i])
			if (arguments[i].match(/\.(\w+)$/i))
				sourceel.setAttribute('type', html5_audiotypes[RegExp.$1])
			html5audio.appendChild(sourceel)
		}
		html5audio.load()
		html5audio.playclip = function() {
			html5audio.pause()
			html5audio.currentTime = 0
			html5audio.play()
		}
		return html5audio
	} else {
		return {
			playclip : function() {
				throw new Error(
						"Este navegador não suporta controle de audio infelizmente")
			}
		}
	}
}
var alertsnd = createsoundbite("sound/snd.wav");

function resumecall(){
	myid = document.getElementById("myid").value;
	myhost = document.getElementById("myhost").value;
	setInterval(function() {
		checkUpdate(myhost, myid, curkey, oldkeys)
		}, 2000);
}

function blikkey() {
	document.getElementById('kbox').className = "curkey blink";
	setTimeout(function() {
		document.getElementById('kbox').className = "curkey";
	}, 4000);

}

function checkUpdate(myhost, myid, curkey, oldkeys) {
	var formData = {
		device : myid
	};
			$.ajax({
				url : myhost + "/ws/display.jsp",
				type : "POST",
				data : formData,
				success : function(data, textStatus, jqXHR) {
					if (data.trim().indexOf("E:") >= 0) {
						alert(data.trim());
						window.location.href = window.location.href;
					} else {
						eval("var mt = "+data.trim());
						if(mt != null){
							eval("var mtnew = "+mt.knew);
							eval("var mtold = "+mt.kold);
							// NEW KEY
							if(mtnew != null && mtnew[0] != undefined){
								if (curkey.trim() != mtnew[0].seq.trim()) {
									var composedkey = "";
									if (mtnew[0].tipo.trim() == "1")
										composedkey = "N";
									else
										composedkey = "P";
									for(var d = mtnew[0].seq.trim().length; d < 3;d++)
										composedkey = composedkey + "0";
									composedkey = composedkey
											+ mtnew[0].seq.trim();
									document.getElementById('kbox').innerHTML = composedkey;
									document.getElementById('kmesa').innerHTML = "Mesa "+ mtnew[0].mesa;
									if (document.getElementById('kbox').className != 'curkey blink')
										blikkey();
									alertsnd.playclip();
									curkey = mtnew[0].seq.trim();
								}
							}
							// NEW KEY
							if(mtold != null && mtold[0] != undefined){
								var boxh = document.getElementById('boxhist');
								var strbh = "";
								var strlk = "";
								strbh = strbh + "<div class='curkeyanth'>Senha</div>";
								strbh = strbh + "<div class='curboxanth'>Mesa</div><br>";
								for(var i = 0; i < mtold.length;i++){
									if(mtold[i].tipo.trim() == "1")
										strlk = "N";
									else
										strlk = "P";
									for(var d = mtold[i].seq.trim().length; d < 3;d++)
										strlk = strlk + "0";
									strlk = strlk + mtold[i].seq;
									strbh = strbh + "<div class='curkeyant'>"+strlk+"</div>";
									strbh = strbh + "<div class='curboxant'>"+mtold[i].mesa+"</div><br>";
								}
								boxh.innerHTML = strbh;
							}
						}
					}// ERROR MESSAGE E:xxx
				},
				error : function(jqXHR, textStatus, errorThrown) {
					alert("Serviço não encontrado, verifique as configurações.");
					window.location.href = window.location.href;
				}
			});

}
