/**
This gets all the watchers that are attached to DOM elements.
 * https://stackoverflow.com/a/18526757/5081877

Note: https://docs.angularjs.org/api/ng/function/angular.element
Keep in mind that this function will not find elements by tag name / CSS selector. For lookups by tag name,
try instead angular.element(document).find(...) or $document.find(), or use the standard DOM APIs, e.g. document.querySelectorAll().
 */
function getAngularElement() {
	
	var angularElement = document.querySelectorAll("[ng-app]");
	if ( angularElement.length == 0 ) { // NodeList - length = 0
		angularElement = document.querySelectorAll("[data-ng-app]");
	}
	console.dir( angularElement );
	// appEle = document.getElementsByTagName('body');
	var root = angular.element( angularElement[0] );
	console.dir( root );
	
	return root;
}

function removeDuplicates( watchers ) {
	// Remove duplicate watchers
	var watchersWithoutDuplicates = [];
	angular.forEach(watchers, function(item) {
		if(watchersWithoutDuplicates.indexOf(item) < 0) {
			watchersWithoutDuplicates.push(item);
		}
	});
	console.log('After Removing duplicated Watch list : ', watchersWithoutDuplicates.length);
	return watchersWithoutDuplicates.length;
}

(function () { // https://gist.github.com/clouddueling/3f134bbd98a65f9bf9b5
	var root = getAngularElement(); 
	var watchers = [];
	var scopeList = ['$scope', '$isolateScope'];
	var angularTree = function (element) {
		angular.forEach(scopeList, function (scopeProperty) { 
			if (element.data() && element.data().hasOwnProperty( scopeProperty )) {
				angular.forEach(element.data()[scopeProperty].$$watchers, function (watcher) {
					watchers.push(watcher);
				});
			}
			angular.forEach(element.children(), function (childElement) {
				// $ - angular.element « https://docs.angularjs.org/api/ng/function/angular.element
				var childTree = $(childElement); // angular.element(childElement)
				angularTree( childTree );
			});
		});
		
	};
	angularTree(root);
	console.log('Final Watch List Count : ', watchers.length);
	removeDuplicates( watchers );
})();

