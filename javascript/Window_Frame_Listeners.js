// Window Code « https://stackoverflow.com/a/35773587/5081877
// var ele = $x("//frame"); ele
var dynamicEle = 
	"<div id='myElement'>"+
		"<span >Increment Value : </span>"+
		"<input id = 'incrementVal' style='height: 25px !important; width: 150px !important;' type='text' />"+
	"</div>";

var scroll_Width = document.documentElement.scrollWidth;
var nodeEle = window.document.createElement('div');
	nodeEle.id = 'myNode';
	nodeEle.innerHTML = dynamicEle;
	nodeEle.setAttribute('style', 'position: absolute;top: 0; left: 0; z-index: 2147483640;'+
						'width : '+scroll_Width+'px; height: 40px; background-color:rgb(20, 217, 80); opacity: 1.5;');
	
(function() {
	var html = document.getElementsByTagName('html')[0];
	// TypeError: Failed to execute 'appendChild' on 'Node': parameter 1 is not of type 'Node'.
	html.appendChild( nodeEle );
	
	// https://stackoverflow.com/a/34746459/5081877
	if (window.addEventListener) {
		window.addEventListener("message", postMessagelistener, false);
	} else {
		window.attachEvent("onmessage", postMessagelistener);
	}
})();
function postMessagelistener(event) {
	var requestStr;
	if( event.data ) {
		//requestStr = event.data;
		requestStr = JSON.parse(event.data);
		console.log('Window : ',  requestStr);
		
		var ele = document.getElementById('incrementVal');
		if( requestStr.data ) {
			console.log('Frame Listener sending data...');
			document.highlight_Elem();
			ele.value = requestStr.data;
		} else if ( requestStr.ready ) {
			console.log('Frame Listener is ready...');
			ele.value = 'Ready...';
			
			// After frame ready, Calling frame message listeners.
			postMessagetoAll_IFrame();
		}
	}
}
		
document.highlight_Elem = function() {
	for( var i = 300; i < 1000; i = i+300) {
		setTimeout("showBorder()", i );
		setTimeout("hideBorder()", i + 150);
	}
}

function showBorder() {
	try {
		var ele = document.getElementById('incrementVal');
		ele.style.setProperty ('border', '2px dashed red', 'important')
	} catch(e) {
		console.log('Exception in showBorder : ', e);
	}
}
function hideBorder() {
	try {
		var ele = document.getElementById('incrementVal');
		ele.style.removeProperty("border"); // outline
		//ele.style.visibility = "hidden";
	} catch(e) {
		console.log('Exception in hideBorder : ', e);
	}
}

function postMessagetoAll_IFrame() {
	var iframes = window.document.getElementsByTagName('IFRAME');
	for(var i = 0; i < iframes.length; i++) {
		try {
			var innerFrames = iframes[i].contentWindow.document.getElementsByTagName('IFRAME');
			innerFrames[0].contentWindow.postMessage(objTarget, '*');
			// window.parent.parent.focus();
		} catch(e) {
			console.log('Inner Frames Exception : ', e );
		}
		iframes[i].contentWindow.postMessage(JSON.stringify({ready:true}), '*');
		// window.parent.focus();
	}
}

// ===== Frame Code =====
var timeOut;
var valueInc = 1;
var originurl;
var win = window;

function sleep( delaySec ) {
	timeOut = win.setTimeout("callbackFun()", delaySec);
}
			
function postMessagelistener( event ) {
	var temp = win.document.referrer;
	originurl = temp.substr(0, temp.length-1);
	if( temp == '') {
		temp = win.document.URL;
		originurl = temp.substr(0, temp.length-1);
	}

	console.log("Listener Started to post Message from Frame to Window.\n\t", originurl);
	
	var requestStr;
	if( event.data ) {
		//requestStr = event.data;
		requestStr = JSON.parse(event.data);
		if( requestStr.ready ) {
			console.log('Call from parent window.');
			callbackFun();
		}
		
	} else {
		console.log('Call from parent window.');
	}
}

function callbackFun(){
	switch( valueInc ){ 
		case 0: clearTimeout( timeOut ); break;
		default : console.log('Frame Running : '+ ( valueInc++ ) );
				respond({data:valueInc}, originurl);
				
				// parent.document.getElementById('incrementVal').value = valueInc;
				sleep(1000 * 10);
				if(valueInc === 10) { valueInc = -1; }
	}
}
function respond(response, origin){
	win.parent.postMessage(JSON.stringify(response), "*");
}

function init(){
	console.log('Setup postMessage event listeners');
	// Setup postMessage event listeners
	if (win.addEventListener) {
		win.addEventListener('message', postMessagelistener, false);
	} else if(win.attachEvent) {
		win.attachEvent('onmessage', postMessagelistener);
	}
	
	// Tell the parent window we're ready.
	win.parent.postMessage(JSON.stringify({ready:true}),"*");

}
init();
