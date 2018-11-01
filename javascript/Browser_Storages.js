/**
 * Object, Array values for a key
 * https://stackoverflow.com/a/3357615/5081877
 * https://www.quora.com/What-is-the-difference-between-sessionstorage-localstorage-and-Cookies
 */
var count = 0;
while ( count < 4 ) {
	var xpathsList = [];
	if( sessionStorage.xpaths != 'undefined' ) {
		xpathsList = JSON.parse(sessionStorage.getItem("xpaths"));
		console.log('XPaths List : ', xpathsList);
	}
	console.log('sessionStorage.xpaths : ', sessionStorage.xpaths);

	var spyEdditor_XPath = '//window[1]//html/div[1]'+count;
	var parts = spyEdditor_XPath.split('//');
	console.log( parts );
	var xpath = '//'+parts[2];
	console.log('XPATH : ', xpath);

	xpathsList.push( xpath );

	sessionStorage.setItem('xpaths', JSON.stringify( xpathsList ) );
	console.log('sessionStorage.xpaths : ', sessionStorage.xpaths);

	count++;
}

