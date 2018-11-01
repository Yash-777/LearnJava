/**
 * WebPage mouse over capturing objects Path.
 * 1. Create a PopUP with `ash` color which will not allow any actions over application.
 * 2. Apply listeners for the application so that user can capture mouse, keyboard events.
 * 3. Based on the events capture elements path, provide some options like CSS path (or) XPath.
 * 4. After that based on the XPath provide some actions like click, hover (or) send-keys.
 * 5. Perform those actions using JavaScript.
 * 
 * There are some browser tools that you can use in order to identify web elements in the DOM easier. These are:
	« Firebug for Firefox
	« Google Developer Tools for Chrome [Chrome « InspectElement - Copy - Copy selector|xpath]
		https://sqa.stackexchange.com/a/17078/14385
	« Web Inspector for Safari
 * 
 * 
 * Browser Extensions to Generate XPath onClick.
 *  « https://chrome.google.com/webstore/detail/xpathonclick/ikbfbhbdjpjnalaooidkdbgjknhghhbo
 *  « https://chrome.google.com/webstore/detail/xpath-generator/lpfjogcaifigkimnlkepjlkfhpdhebap
 *  
 * Application Video Recorder.
 *  « https://chrome.google.com/webstore/detail/hyfy-screen-video-recorde/kfhkikpdmehlpkaiplafjkaicdljldcf
 *  
 * document.evaluate(path, top, null, XPathResult.ANY_TYPE, null);
 * document.evaluate(path, top, null, XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null);
 * 
 * Event Bubbling and Capturing « https://javascript.info/bubbling-and-capturing
 * 
 * Google Chrome Extension: highlight the div that the mouse is hovering over
 * https://stackoverflow.com/a/4446039/5081877
 */
function description() {
	//spy_Events.attachKeydown();
	//spy_Events.attachClick();
	//spy_Events.detach();
}

/* 2. Apply listeners */
var spy_onMouse_Event = '',
	spy_onMouse_Tag = '',
	spy_onMouse_TagName = '',
	spyEdditor_XPath = '', spy_Status = '',
	spyEdditor_XPathEle = '',
	bubblingCount = 0;

var initialEventEle = 0;

var xpathsList = new Array();
var useCapturing = true; // Add and Remove my own click event with some id.
var spy_Events = {
	listnerApplyW : window.document.body,
	listnerApply : window.document,
	initSession : function() { // spy_Events.initSession
		if( sessionStorage.xpaths != 'undefined' ) {
			var keyData = sessionStorage.getItem("xpaths");
			if( keyData == null || keyData == '' ) {
				sessionStorage.setItem('xpaths', JSON.stringify( xpathsList ) );
				keyData = sessionStorage.getItem("xpaths");
			}
			xpathsList = JSON.parse( keyData ); // "Unexpected end of JSON input"
			// console.log(keyData, '\tXPaths List : ', xpathsList);
		}
	},
	/*
	 * https://developer.mozilla.org/en-US/docs/Web/API/EventTarget/addEventListener
	 * https://developer.mozilla.org/en-US/docs/Web/Events
	 * 
		for(let elem of document.querySelectorAll('*')) {
			elem.addEventListener("click", e => alert(`Capturing: ${elem.tagName}`), true);
			elem.addEventListener("click", e => alert(`Bubbling: ${elem.tagName}`));
		}
	 */
	attachClick : function() {
		if (window.addEventListener) { // W3C DOM
			for (let el of spy_Events.listnerApply.getElementsByTagName('*')) {
				el.addEventListener('mouseover', spy_Events.MouseOver);
			}
			
			/*let panel = document.getElementById('xpath-generator');
			for (let el of panel.getElementsByTagName('*')) {
				el.removeEventListener('mouseover', spy_Events.MouseOver);
			}
			panel.removeEventListener('mouseover', spy_Events.MouseOver);*/
		}
	},
	attachKeydown : function() {
		if (window.addEventListener) { // W3C DOM
			
			for (let el of spy_Events.listnerApply.getElementsByTagName('*')) {
				el.addEventListener('mousemove', spy_Events.MouseMove);
				el.addEventListener('mouseout', spy_Events.MouseOut);
				el.addEventListener('keydown', spy_Events.KeyDown);
			}
			// window.addEventListener("message", spy_Events.postMessagelistener, false);
		}
	},
	detach : function() {
		if (window.removeEventListener) {  // W3C DOM - mouseover, mousemove, mouseout, keydown, click
			for (let el of spy_Events.listnerApply.getElementsByTagName('*')) {
				el.removeEventListener('mousemove', spy_Events.MouseMove);
				el.removeEventListener('mouseout', spy_Events.MouseOut);
				el.removeEventListener('keydown', spy_Events.KeyDown);
			
				el.removeEventListener('mouseover', spy_Events.MouseOver);
			}
			//window.removeEventListener("message", spy_Events.postMessagelistener, false);
		}
	},
	MouseOver : function( MouseEvent ) {
		spy_onMouse_Event = MouseEvent;
		try {
			//console.log('Mouse Event : ', MouseEvent);
			
			spy_onMouse_Tag = MouseEvent.target || MouseEvent.srcElement || MouseEvent.toElement;
			spy_onMouse_TagName = spy_onMouse_Tag.nodeName || spy_onMouse_Tag.tagName;
			
			spy_onMouse_Tag.addEventListener("click", spy_Events.MouseClick, useCapturing);
			//spy_Events.MouseClick( MouseEvent, "click" );
			
		} catch( e ) {
			spy_Status = 'Exception in MouseOver : '+ e;
		}
	},
	MouseClick : function( ) {
		try {
			var event = spy_onMouse_Event;
			
			spy_Events.initSession();
			
			spy_Events.spyPath( event );
			console.log('\t XPATH : ', spyEdditor_XPath );
			
			spy_onMouse_Tag.removeEventListener("click", spy_Events.MouseClick, useCapturing); // Capturing
			
			event.stopImmediatePropagation();
			event.preventDefault();
			event.stopPropagation();
			
			var xpath = spyEdditor_XPath;
			
			if (xpathsList.indexOf(xpath) < 0) {
				xpathsList.push( xpath );
			} else {
				console.log('Duplicate element : ', xpath);
			}
			
			sessionStorage.setItem('xpaths', JSON.stringify( xpathsList ) );
			console.log('sessionStorage.xpaths : ', sessionStorage.xpaths);
			
		} catch( e ) {
			spy_Status = 'Exception in MouseClick : '+ e;
		}
	},
	MouseMove : function( MouseEvent ) {
		spy_onMouse_Event = MouseEvent;
		try {
			//console.log('Mouse Event : ', MouseEvent);
			
			spy_onMouse_Tag = MouseEvent.target || MouseEvent.srcElement || MouseEvent.toElement;
			spy_onMouse_TagName = spy_onMouse_Tag.nodeName || spy_onMouse_Tag.tagName;
			
			// .xpath-Generator { background-color: rgb(225, 192, 220); outline: rgb(204, 0, 0) solid 1px; }
			//spy_onMouse_Tag.style.setProperty ('border', '1px solid #0099cc', 'important');
			
			spy_onMouse_Tag.classList.add('spy_highlight');
			
			/*spy_Events.spyPath( spy_onMouse_Event );
			console.log('XPATH : ', spyEdditor_XPath );*/
		
		} catch( e ) {
			spy_Status = 'Exception in MouseMove : '+ e;
		}
	},
	MouseOut : function ( MouseEvent ) {
		spy_onMouse_Event = MouseEvent;
		try {
			spy_onMouse_Tag = MouseEvent.target || MouseEvent.srcElement || MouseEvent.toElement;
			//spy_onMouse_Tag.style.removeProperty("border");
			
			spy_onMouse_Tag.classList.remove('spy_highlight');
			
		} catch(e) {
			if(e.indexOf("is undefined") !== -1) {
				spy_Status = 'Exception in Mouseout : '+ e;
			}
		}
	},
	KeyDown : function( KeyboardEvent ) {
		try {
			keyDownCode = KeyboardEvent.keyCode || KeyboardEvent.which;
			console.log("Key Event Code : ", keyDownCode);
			if( keyDownCode == 17 ) { // CTRL key value 17
				// ctrlKey: true, shiftKey: false, altKey: false
				spy_Events.initSession();
				
				spy_Events.spyPath( spy_onMouse_Event );
				console.log('XPATH : ', spyEdditor_XPath );
				
				// '//window[1]//html/div[1]' « ["", "window[1]", "html/div[1]"]
				/*var parts = spyEdditor_XPath.split('//');
				var xpath = '//'+parts[2];
				console.log(parts, '« XPATH : ', xpath);*/
				
				var xpath = spyEdditor_XPath;
				if (xpathsList.indexOf(xpath) < 0) {
					xpathsList.push( xpath );
				} else {
					console.log('Duplicate element : ', xpath);
				}
				
				sessionStorage.setItem('xpaths', JSON.stringify( xpathsList ) );
				console.log('sessionStorage.xpaths : ', sessionStorage.xpaths);
			}
			spy_Events.activate();
		} catch(e) {
			spy_Status = 'Exception in KeyDown : '+ e;
		}
	},
	/*If the parent window (or) child Frames, IFrames posts any message
	 * this listener is Called and the function code gets executed.
	 * 
	 * var child = window.document.getElementsByTagName('FRAME');
	 * child.contentWindow.postMessage('Some Message', '*');
	 */
	postMessagelistener : function ( event ) {
		var string;
		if( event.data ) {
			string = event.data;
			console.log('Event Data : ', string );
		}
	},
	spyPath : function ( mouseEvent ) {
		console.log('Mouse Event Element - To find its Path.');
		var tagIndex = null;
		spyEdditor_XPath = '';
		var window_size = 1;
		try{ 
			// e - eventTag
			e = mouseEvent.target || mouseEvent.srcElement || mouseEvent.originalTarget;
			spyEdditor_XPathEle = e.outerHTML;

			for (var path = ''; e && e.nodeType == 1; e = e.parentNode) {
				var currentTagName = e.tagName || e.nodeName || e.localName;
				currentTagName = currentTagName.toUpperCase();
				
				var siblings = e.parentNode.children; // Same Tree level Elements
				var sameTagCount = 0;
				for ( var i = 0; siblings && ( i < siblings.length ); i++ ) {
					
					if( siblings[i].tagName == currentTagName ) {
						
						sameTagCount++;
						if ( siblings[i] == e ) { // Found Exact Element
							tagIndex = sameTagCount;
						}
					}
				}
				
				if( currentTagName == 'BODY' || currentTagName == 'HTML' ) {
					tagIndex = null;
				}
				
				tagIndex = ( tagIndex ) ? ('[' + tagIndex + ']') : '';
				
				// Ex: //main[1]/div[1]/article[1]/h2[@name='nameNode'][@type='input'][1]
				// path == '' means add to last element only.
				if( path == '' ) {
					path = '/' + currentTagName.toLowerCase();
					
					var idValue = undefined; // e.id;
					var classVlaue = undefined; // e.className;
					var nameVlaue = e.name;
					var typeVlaue = e.type;
					
					/* attributes:NamedNodeMap « length:4 « https://www.w3schools.com/xml/dom_namednodemap.asp
						0:id [nodeName:"id",value:"Summary"]
						1:name [nodeName:"name",value:"nameNode"]
						2:class [nodeName:"class",value:"classnode"]
						3:type [nodeName:"type",value:"input"]
					*/
					//if( nameVlaue == undefined || typeVlaue == undefined ) {
						var attributeList = e.attributes;
						var mapType = attributeList.getNamedItem('type');
						var mapName = attributeList.getNamedItem('name');
						if( mapType !=  null && mapType != '' ) {
							// <input type='BUTTON' />
							// typeVlaue = 'button', mapType = 'BUTTON'
							if( typeVlaue != undefined && mapType != typeVlaue) {
								typeVlaue = mapType.value;
							}
						}
						if( mapName !=  null && mapName != '' ) {
							if( nameVlaue != undefined && mapName != nameVlaue) {
								nameVlaue = mapName.value;
							}
						}
					//}
				
					// Get Current Tag Text and replace special characters.
					var tagText = e.textContent.replace(/&amp;/g,'&').trim();
					// Button with innerText Value Ex: Submit, Login ...
					if( tagText && currentTagName == 'BUTTON' && currentTagName == 'NOBR') {
						path += "[.=\""+tagText+"\"]";
					}
					
					if( idValue != undefined && idValue != '') {
						path += "[@id='"+idValue+"']";
						if( classVlaue != undefined && classVlaue != '') {
							path += "[@class='"+classVlaue+"']";
						}if( nameVlaue != undefined && nameVlaue != '') {
							path += "[@name='"+nameVlaue+"']";
						}
					} else if( classVlaue != undefined && classVlaue != '') {
						path += "[@class='"+classVlaue+"']";
						if( nameVlaue != undefined && nameVlaue != '') {
							path += "[@name='"+nameVlaue+"']";
						}
					} else if( nameVlaue != undefined && nameVlaue != '') {
						path += "[@name='"+nameVlaue+"']";
						if( typeVlaue != undefined && typeVlaue != '') {
							path += "[@type='"+typeVlaue+"']";
						}
					} else if( typeVlaue != undefined && typeVlaue != '') {
						path += "[@type='"+typeVlaue+"']";
					}
					path += tagIndex;
				} else {
					path='/' + currentTagName.toLowerCase() + tagIndex + path;
				}
				
				var uniquePathCount = 0;
				uniquePathCount = window.document.evaluate('count(/'+path+')', window.document, null, 0, null);
				
				// We are going to break the loop if we find Unique Locator in DOM.
				if( uniquePathCount.numberValue == 1 ) {
					// path  = '//window['+window_size+']/'+path;
					path  = '/'+path;
					spyEdditor_XPath = path;
					break; // To Break for loop.
				}
			}
		} catch(e) {
			spy_Status = 'Exception in spyPath : '+ e;
		}
	},
	createCSS : function () {
		var spy_Styles = window.document.createElement('style');
		spy_Styles.setAttribute("type","text/css");
		spy_Styles.id = 'spy_CSS';
		
		if (window.document.head.firstChild) {
			window.document.head.insertBefore( spy_Styles, window.document.head.firstChild );
		} else {
			window.document.head.appendChild( spy_Styles );
		}
		//window.document.getElementsByTagName("style")[0].innerHTML
		spy_Styles.innerHTML
			=
			".spy_highlight { background-color: rgb(225, 192, 220); outline: rgb(204, 0, 0) solid 1px; }"+
			".spy_inputs:focus { border:1px solid #AAB3F5 !important;}" +
			".spy_inputs { " +
				"-webkit-appearance: textfield;" +
				"-webkit-writing-mode: horizontal-tb;" +
				"background-color: white !important;" +
				"padding: 2px 5px !important; " +
				"text-shadow: none !important;" +
				"word-wrap: break-word; word-break: break-all;" +
			"}" +
			"#spy_SelectType {"+  
				"-webkit-user-select: inline; display:inline !important;" +
				"letter-spacing: 0px !important;" +
				"line-height: 0px !important; " +
				
				"-webkit-appearance: menulist; -moz-appearance: menulist; "+
				"-ms-appearance: menulist; -o-appearance: menulist;"+
				
				"appearance: menulist;"+
				"width: 120px !important;"+
				"height: 25px !important;" +
				"padding: 1px 1px !important;" +
				"font-size: 12px !important;" +
				"text-align-last: center !important;" +
			"}"+
			".spy_Buttons{" +
				"color: #000 !important;"+
				"background:#DBE3FF !important;;" +
				"border:1px solid #B2B7C7 !important;" +
				
				"box-shadow:none !important;" +
				"-webkit-box-shadow: none !important;"+
				"height:25px; " +
				"width: auto;" +
				
				"cursor:pointer;"+
				"font-size:12px !important;"+
				
				"text-align: center !important;"+
				"min-width: 50px; !important"+
				
				"letter-spacing: 0px !important;" +
				"line-height: 0px !important; " +
				
				"text-transform: uppercase !important;"+
				"font-family: ProximaNova-Semibold !important;" +
			"}"+
			".spy_Buttons:hover {" +
				"color: #FFF !important;"+
				"background-color: #566977 !important;" +
				"box-shadow:none !important;" +
				"border-color: #759AB5 !important;"+
			"}"
			;
	}

};

/* 1. Creating a Model PopUP */
var modelPopUp = function () {
	var options = {};
	var modelPopUpID = 'popup777'
	
	options.append = function append() {
		//freezeScreen( modelPopUpID );
		spy_Events.createCSS();
		spy_Events.attachKeydown();
		//spy_Events.attachClick();
	}
	
	options.remove = function remove() {
		//unFreezeScreen( modelPopUpID )
	}
	
	return options;
}

function stopInspect() {
	for (let el of document.getElementsByTagName('*')) {
		el.removeEventListener('mouseover', inspectHandler);
	}
}

document.modelPopUp = modelPopUp();
modelPopUp().append();