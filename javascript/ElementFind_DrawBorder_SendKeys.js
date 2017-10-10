/**
 * Find Given HTML String availability over Current DOM
 * Creating Element as a Layer over HTML
 * 
 * http://jsfiddle.net/jayeshcp/e7d2bs59/
 * http://jsfiddle.net/amit14/LHKh8/4/ - http://jsfiddle.net/z6u4t/27/
 */
function setStyleOption( htmlEle ) {
	htmlEle.style.setProperty( 'outline', "3px solid #45FF17","important");
}
function removeStyleOption( htmlEle ) {
	htmlEle.style.removeProperty("outline");
}

function sendKeys( htmlEle, value ) {
	htmlEle.value = value;
}

/* ===== Find Given HTML String availability over Current DOM ===== */
function findXMLTreeString( targetEleString, xpath ) {
	var nodeType = document;//document.body;
		var dom_XMLString = new XMLSerializer().serializeToString( nodeType );
		dom_XMLString = dom_XMLString.replace( new RegExp('\\s+', 'gm'), ' ' );
		
		if( dom_XMLString.indexOf( targetEleString ) >= 0 ) {
			return true;
		}
		
		var element = document.evaluate( xpath, window.document, null, 9, null ).singleNodeValue;
		var inputUser = element.outerHTML.replace( new RegExp('\\s+', 'gm'), ' ' );
		
		if( inputUser.indexOf( targetEleString ) >= 0) {
			return true;
		}
		
		var dom_HtmlString = document.documentElement.innerHTML.replace( new RegExp('\\s+', 'gm'), ' ' );
		var val = dom_HtmlString.indexOf( targetEleString );
		if( val >= 0) {
			return true;
		}
		return false;
}

/* ===== Creating Element as a Layer over HTML ===== */
var myID = 'FreezDocument';
function layerOverHTML( myID ) {
	var freezeBody = window.document.createElement('div');
	freezeBody.id = myID;

	var scroll_Height = document.documentElement.scrollHeight;
	var scroll_Width = document.documentElement.scrollWidth;
	/*
	 * Picture a game of 52 cards. If the ace of spades was at the bottom we'd say it had z-index:1;.
	 * If the queen of hearts was at the top we'd say she had z-index:52;.
	 */
	var layers_CSS_JOSN = {
				'position': 'absolute',
				'top': 0, 'left': 0,
				'z-index': '2147483640',
				'width': scroll_Width+'px',
				'height': scroll_Height+'px',
				'background-color' : '#F8F6D8',
				'opacity' : '0.5'
		}
	var str = JSON.stringify( layers_CSS_JOSN );

	// https://stackoverflow.com/a/16577007/5081877
	String.prototype.allReplace = function(obj) {
		var retStr = this;
		for (var x in obj) {
			retStr = retStr.replace(new RegExp(x, 'g'), obj[x]);
		}
		return retStr;
	};
	str = str.allReplace({'\'': '', '"':'', ',': ';', '{': '', '}': ''});
	console.log( str );
		
	freezeBody.setAttribute('style', str );
	var mainbody = document.getElementsByTagName('html')[0];
	mainbody.appendChild(freezeBody);
}

function romoveLayerOverHTML( myID ) {
	try{
		var html = document.getElementsByTagName('html')[0];
		var child = document.getElementById( myID );
		html.removeChild(child);
	} catch (e) {

	}
}
/* ===== 	Convert RGB to HEX:
	Below Code « https://stackoverflow.com/a/19765382/5081877
	ES6 « https://stackoverflow.com/a/39077686/5081877 ===== */
function rgbToHex(R,G,B) {
	var rgb = B | (G << 8) | (R << 16);
	return '#' + (0x1000000 + rgb).toString(16).slice(1)
}
function hex2rgb(hex) {
	// long version
	r = hex.match(/^#([0-9a-f]{2})([0-9a-f]{2})([0-9a-f]{2})$/i);
	if (r) {
			return r.slice(1,4).map(function(x) { return parseInt(x, 16); });
	}
	// short version
	r = hex.match(/^#([0-9a-f])([0-9a-f])([0-9a-f])$/i);
	if (r) {
			return r.slice(1,4).map(function(x) { return 0x11 * parseInt(x, 16); });
	}
	return null;
}

rgbToHex( 37,255,83 ); // "25FF53"
rgbToHex( 34, 255, 153 ); // "2f9"