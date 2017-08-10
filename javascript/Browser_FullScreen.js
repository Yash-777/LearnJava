/** 
 * Browser Full Screen Mode Size
 * F11 -
 * https://stackoverflow.com/a/10082234/5081877
 * https://stackoverflow.com/a/7525760/5081877
 * */
/* ===== F11 - Key Event [ Automatically hide the task-bar in desktop mode]===== 
 * https://hacks.mozilla.org/2012/01/using-the-fullscreen-api-in-web-browsers/
 * http://robnyman.github.io/fullscreen/
 * http://www.css-jquery-design.com/2013/11/javascript-jquery-fullscreen-browser-window-html5-technology/
 * */
var isInFullScreen = (document.fullScreenElement && document.fullScreenElement !== null) 
					|| (document.mozFullScreen || document.webkitIsFullScreen);

if (isInFullScreen) {
	cancelFullScreen(document);
} else {
	requestFullScreen( document.body );
}

function keyEventF11() { // For Internet Explorer
	var wscript = new ActiveXObject("WScript.Shell");
	if (wscript !== null) {
		wscript.SendKeys("{F11}");
	}
}
function cancelFullScreen(el) {
	var requestCancelMethod = el.cancelFullScreen || el.webkitCancelFullScreen || el.mozCancelFullScreen || el.exitFullscreen;
	if ( requestCancelMethod ) { // cancel full screen.
		requestCancelMethod.call(el);
	} else if (typeof window.ActiveXObject !== "undefined") { // Older IE.
		keyEventF11();
	}
}

function requestFullScreen(el) {
	var requestMethod = el.requestFullScreen || el.webkitRequestFullScreen || el.mozRequestFullScreen || el.msRequestFullscreen;
	if (requestMethod) { // Native full screen.
		requestMethod.call(el);
	} else if (typeof window.ActiveXObject !== "undefined") { // Older IE.
		keyEventF11();
	}
}