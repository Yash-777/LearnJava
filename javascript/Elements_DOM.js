/**
 * http://usejsdoc.org/
 */
function injectScript( script_src ) {

	var getHeadTag = document.getElementsByTagName('head')[0];
	var newScriptTag = document.createElement('script');
		newScriptTag.type = 'text/javascript';
		newScriptTag.src  = script_src;
	getHeadTag.appendChild(newScriptTag);

}
var script_src = 'https://localhost:8443/ServerURL/users.js';
injectScript( script_src );