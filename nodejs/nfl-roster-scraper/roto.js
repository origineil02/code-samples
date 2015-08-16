var select = require('soupselect').select,
    htmlparser = require("htmlparser"),
    http = require('http-request'),
    sys = require('sys');

var host = 'http://www.rotoworld.com';

function get(url, parsingHook) {
  http.get({
	url: url,
}, function (err, res) {
	if (err) {
		console.error(err);
		return;
	}
	var handler = new htmlparser.DefaultHandler(parsingHook);
     var parser = new htmlparser.Parser(handler);
     parser.parseComplete(res.buffer.toString());
});
}
function fetchRoster(url) {
    get(url, function(err, dom) {
      console.log(url)
      var columns = select(dom, 'table.statstable tr.columnnames td')
      console.log(columns)
      var players = select(dom, 'table.statstable tr a')
      console.log(players)
      var arr = [];
      columns.forEach(function(col){
        arr.push(col.children[0].raw)
      })
      console.log(arr)
    })
}
function fetchTeams() {
  get(host + '/sports/nfl/football', function(err, dom){
  var teams = select(dom, 'div[id="tmTabNFL"]  a');
  teams.forEach(function(team){
    var url = host + team.attribs.href.replace('clubhouse', 'rosters')
    fetchRoster(url, function(err, dom){
      console.log(dom)
    });
  })
});
}

fetchTeams();

exports.module = {
  
}