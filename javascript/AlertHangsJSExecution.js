/**
 * https://codepen.io/Leontruong/pen/GoVQBg
 * https://stackoverflow.com/a/33142037/5081877
 * 
 * https://www.w3schools.com/jsref/met_win_alert.asp
 */
function sleep(delay) { t=setTimeout("flow()", delay); }
var i = 1;
function flow(){
	switch(i){ 
		case 0: clearTimeout(t); break;
		default : console.log('Running : '+ (i++) );
					sleep(1000 * 2);
					if(i === 50) { i = -1; }
	}
}
flow();

/*document.onkeypress = function ( event ) {
	var key = String.fromCharCode( event.which );
	alert('KEY-PRESS Key Pressed : '+key); 
	clearTimeout(t);
}
	document.body.onclick = function() {
	alert('Mouse CLICK action Performed');
	clearTimeout(t);
}*/

// http://api.jquery.com/one/
let timer_demo = {
	count: 0,
	timer: null,
	update: function() {
		this.count++;
		console.log(this.count);
		if( this.count == 5) {
			clearTimeout( this.timer );
		} else {
			this.timer = setTimeout(this.update.bind(this), 1000);
		}
	}
};
timer_demo.update();