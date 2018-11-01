/**
 * Perform an asynchronous HTTP (Ajax) request. « http://api.jquery.com/jquery.ajax/
 * charset = UTF-8 , iso-8859-1
 * 
 */
var methodHTTP = 'GET'; // "POST", "GET", "PUT"
var serverURL = 'http://localhost:8080/SteamingServlet/msg/locale';

var queryString = 'יאש';
			//'message:יאש';
var enobjects =  
	'message='+ encodeURIComponent( queryString );
	//encodeURIComponent( queryString ); // 500 - Internal Server Error

console.log( 'enobjects : ', enobjects);

var uri = serverURL;
var uri_enc = encodeURIComponent(uri);
var uri_dec = decodeURIComponent(uri_enc);
var res = uri_enc + "\n" + uri_dec;
console.log(res);

if (window.jQuery) {
	console.log('jQuery is loaded with version : ', jQuery().jquery);
	
	$.ajax({
		headers: 'Access-Control-Allow-Origin: *',
		crossdomain:true,
		
		contentType: 'Content-type: text/plain; charset=UTF-8;',
		datatype: 'text',
		data: enobjects,
		
		/*dataType: "json",
		data: JSON.stringify( enobjects ),
		contentType: "application/json;charset=UTF-8",*/
		
		
		method : methodHTTP,
		//type : methodHTTP, // jQuery prior to 1.9.0
		
		url: serverURL,
		
		beforeSend: function(jqXHR) {
			jqXHR.overrideMimeType('text/plan; charset=UTF-8;');
		},
		
		statusCode: {
			404: function() {
				console.log( "page not found" );
			}
		},
		success: function( data ) {
			console.log("Success : ", data);
		},
		error: function( xhr ) {
			console.log('Failed! : ', xhr.statusText, '\t', xhr.responseText);
		},
		complete: function() {
			console.log( "complete" );
		}
	}).done(function( data ) {
		console.log( "done : ", data );
	}).fail(function() {
		console.log( "error" );
	}).always(function() {
		console.log( "second complete" );
	});
	
} else { // XMLHttpRequest
	var xmlhttp = new XMLHttpRequest();
	
	if( methodHTTP == 'GET' ) {  console.log( "XMLHttpRequest - GET" );
		
		xmlhttp.open(methodHTTP, serverURL+'?'+enobjects, true);
		
		xmlhttp.setRequestHeader("Content-type","application/x-javascript; charset=UTF-8");
		xmlhttp.setRequestHeader("Access-Control-Allow-Origin","*");
		
		xmlhttp.send( );
	} else {  console.log( "XMLHttpRequest - POST" );
		
		xmlhttp.open(methodHTTP, serverURL, true);
		xmlhttp.setRequestHeader("Content-type","application/x-javascript; charset=UTF-8");
		xmlhttp.setRequestHeader("Access-Control-Allow-Origin","*");
		
		xmlhttp.send( enobjects );
	}
	
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
			console.log("Message :: ", xmlhttp.responseText);
			console.log("Decoded Message ::  ", decodeURIComponent( xmlhttp.responseText ) );

		} else {
			console.log("Status Code : ", xmlhttp.status);
		}
	}
}