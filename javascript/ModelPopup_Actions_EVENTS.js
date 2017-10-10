/**
 * https://github.com/Yash-777/SeleniumDriverAutomation/wiki/JavaScript-Popup-Boxes
 */

// window.captureEvents(Event.KEYPRESS);
// https://stackoverflow.com/q/1312524/5081877
document.onkeypress= function(event) {
	var key= String.fromCharCode(event? event.which : window.event.keyCode);
	if ( key=='a' || key=='A' ) {
		console.log('Key A pressed.');
		bootStrapModelPopu();
	}
};
document.onkeydown = function(e) {
	//do what you need to do
};
function addEventSimple(obj,evt,fn) {
	if (obj.addEventListener)
		obj.addEventListener(evt,fn,false);
	else if (obj.attachEvent)
		obj.attachEvent('on'+evt,fn);
} // method pulled from quirksmode.org for cross-browser compatibility

addEventSimple(window, "keydown", function(e) {
	// check keys
});

// https://stackoverflow.com/a/38614737/5081877
var bootStrapModelPopuAvailability = false;

function bootStrapModelPopu() {
	//console.log('OnClic Title element funciton.');
	// Tabindedx = -1 | modal-open = overflow : hidden | 
	if (window.jQuery) {  
		//console.log('jQuery is loaded'); // modal fade in
		
		var ele = $(window.document).find('.modal.fade.in');
		console.log(ele);
		if ( ele.length == 1 ) {
			if( !bootStrapModelPopuAvailability ) {
				console.log('****** Element modal fade in - open State');
				$(document).off('focusin.modal');
			}
			bootStrapModelPopuAvailability = true;
		} else {
			bootStrapModelPopuAvailability = false;
			//console.log('Element not found');
		}

	} else {
		//console.log("jQuery is not loaded"); // location.reload();
	}
}
