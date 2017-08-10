/**
 * Test URLs : http://www.sony.co.in/
 */
/*=====  ===== */
function getBrowserWindowLoacation() {
	var application = {}; // 

	application.innerHeight = window.innerHeight;
	application.clientHeight = window.document.documentElement.clientHeight;
	application.scrollHeight = window.document.documentElement.scrollHeight;
	if( window.document.documentElement.clientHeight === window.innerHeight+17 ) {
		application.scrollBarX = true;
	}
	console.log('Application : ', application);


	var browserWindow = {};

	browserWindow.top = window.screenY != undefined ? window.screenY : window.screenTop;
	browserWindow.left = window.screenLeft != undefined ? window.screenLeft : window.screenX;

	browserWindow.width = window.outerWidth;
	browserWindow.height = window.outerHeight;

	browserWindow.right = screen.width - ( window.outerWidth + window.screenLeft );
	browserWindow.bottom = screen.height - ( window.outerHeight + window.screenTop );

	//browserWindow.addressURI = window.location.href; // https://stackoverflow.com/a/38199254/5081877
	console.log('Browser Window Position : ', browserWindow);
	console.log('Top + Height + Bottom = ', browserWindow.top + browserWindow.height + browserWindow.bottom);
	console.log('Left + Width + Right = ', browserWindow.left + browserWindow.width + browserWindow.right);

	var screenResolution = {};
	screenResolution.height = screen.height;
	screenResolution.width = screen.width;
	screenResolution.availWidth = screen.availWidth;
	screenResolution.availHeight = screen.availHeight;

	console.log('Platform Display Screen : ', screenResolution );

	if( screen.height != screen.availHeight ) screenResolution.taskBar = "Bottom | Top";
}