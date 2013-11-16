/**
 * Use Parse.Cloud.define to define as many cloud functions as you want.
 */
Parse.Cloud.define("hello", function(request, response) {
	response.success("Hello world!");
});

Parse.Cloud.define("test", function(request, response) {
	Parse.Cloud.httpRequest({
		  url: 'http://www.google.com/',
		  success: function(httpResponse) {
		    console.log(httpResponse.text);
		  },
		  error: function(httpResponse) {
		    console.error('Request failed with response code ' + httpResponse.status);
		  }
		});
});