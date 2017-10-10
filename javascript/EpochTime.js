/**
 * https://stackoverflow.com/a/12342913/5081877
 */
function epochToJsDate(ts){
	return new Date( ts*1000 );
}

function jsDateToEpoch(d){
	return (d.getTime()-d.getMilliseconds())/1000;
}
console.log( jsDateToEpoch(new Date() ) );

if( 1501512617 < 1501512675 ) {
	console.log('Less time');
}

var utc_string = '2017-07-31 20:20:17';
var local_string = (function(dtstr) {
	var t0 = new Date(dtstr);
	var t1 = Date.parse(t0.toUTCString().replace('GMT', ''));
	var t2 = (2 * t0) - t1;
	return new Date(t2).toString();
})(utc_string);

console.log('Epoch : ', local_string );
//Epoch :  Tue Aug 01 2017 01:50:17 GMT+0530 (India Standard Time)

var utc_string = '2017-07-31 20:20:17';
console.log( new Date(utc_string) );
