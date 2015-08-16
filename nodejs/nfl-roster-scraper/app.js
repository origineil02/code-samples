var select = require('soupselect').select,
    htmlparser = require("htmlparser"),
    http = require('http'),
    sys = require('sys');

var http = require('http');
var host = 'www.nfl.com';
var client = http.createClient(80, host);
var request;

teams = {
        "BUF" : { "abbr" : "BUF", "url" : "http://www.buffalobills.com/", "teamPage":"/teams/buffalobills/profile?team=BUF", "city" : "Buffalo", "nickname" : "Bills", "conference": "AFC", "division": "East", "shopId" : "Buffalo_Bills_Gear/source/bm-nflcom-Header-Bills", "facebook": "BuffaloBills", "twitter": "BuffaloBills" },
        "MIA" : { "abbr" : "MIA", "url" : "http://www.miamidolphins.com/", "teamPage":"/teams/miamidolphins/profile?team=MIA", "city" : "Miami", "nickname" : "Dolphins", "conference": "AFC", "division": "East", "shopId" : "Miami_Dolphins_Gear/source/bm-nflcom-Header-Dolphins", "facebook": "MiamiDolphins", "twitter": "MiamiDolphins" },
        "NE" : { "abbr" : "NE", "url" : "http://www.patriots.com/", "teamPage":"/teams/newenglandpatriots/profile?team=NE", "city" : "New England", "nickname" : "Patriots", "conference": "AFC", "division": "East", "shopId" : "New_England_Patriots_Gear/source/bm-nflcom-Header-Patriots", "facebook": "newenglandpatriots", "twitter": "PATRIOTS" },
        "NYJ" : { "abbr" : "NYJ", "url" : "http://www.newyorkjets.com/", "teamPage":"/teams/newyorkjets/profile?team=NYJ", "city" : "New York", "nickname" : "Jets", "conference": "AFC", "division": "East", "shopId" : "New_York_Jets_Gear/source/bm-nflcom-Header-Jets", "facebook": "Jets", "twitter": "nyjets" },
        "BAL" : { "abbr" : "BAL", "url" : "http://www.baltimoreravens.com/", "teamPage":"/teams/baltimoreravens/profile?team=BAL", "city" : "Baltimore", "nickname" : "Ravens", "conference": "AFC", "division": "North", "shopId" : "Baltimore_Ravens_Gear/source/bm-nflcom-Header-Ravens", "facebook": "baltimoreravens", "twitter": "Ravens" },
        "CIN" : { "abbr" : "CIN", "url" : "http://www.bengals.com/", "teamPage":"/teams/cincinnatibengals/profile?team=CIN", "city" : "Cincinnati", "nickname" : "Bengals", "conference": "AFC", "division": "North", "shopId" : "Cincinnati_Bengals_Gear/source/bm-nflcom-Header-Bengals", "facebook": "bengals", "twitter": "Bengals" },
        "CLE" : { "abbr" : "CLE", "url" : "http://www.clevelandbrowns.com/", "teamPage":"/teams/clevelandbrowns/profile?team=CLE", "city" : "Cleveland", "nickname" : "Browns", "conference": "AFC", "division": "North", "shopId" : "Cleveland_Browns_Gear/source/bm-nflcom-Header-Browns", "facebook": "clevelandbrowns", "twitter": "OfficialBrowns" },
        "PIT" : { "abbr" : "PIT", "url" : "http://www.steelers.com/", "teamPage":"/teams/pittsburghsteelers/profile?team=PIT", "city" : "Pittsburgh", "nickname" : "Steelers", "conference": "AFC", "division": "North", "shopId" : "Pittsburgh_Steelers_Gear/source/bm-nflcom-Header-Steelers", "facebook": "steelers", "twitter": "steelers" },
        "HOU" : { "abbr" : "HOU", "url" : "http://www.houstontexans.com/", "teamPage":"/teams/houstontexans/profile?team=HOU", "city" : "Houston", "nickname" : "Texans", "conference": "AFC", "division": "South", "shopId" : "Houston_Texans_Gear/source/bm-nflcom-Header-Texans", "facebook": "HoustonTexans", "twitter": "HoustonTexans" },
        "IND" : { "abbr" : "IND", "url" : "http://www.colts.com/", "teamPage":"/teams/indianapoliscolts/profile?team=IND", "city" : "Indianapolis", "nickname" : "Colts", "conference": "AFC", "division": "South", "shopId" : "Indianapolis_Colts_Gear/source/bm-nflcom-Header-Colts", "facebook": "colts", "twitter": "nflcolts" },
        "JAC" : { "abbr" : "JAC", "url" : "http://www.jaguars.com/", "teamPage":"/teams/jacksonvillejaguars/profile?team=JAC", "city" : "Jacksonville", "nickname" : "Jaguars", "conference": "AFC", "division": "South", "shopId" : "Jacksonville_Jaguars_Gear/source/bm-nflcom-Header-Jaguars", "facebook": "jacksonvillejaguars", "twitter": "jaguarsinsider" },
        "TEN" : { "abbr" : "TEN", "url" : "http://www.titansonline.com/", "teamPage":"/teams/tennesseetitans/profile?team=TEN", "city" : "Tennessee", "nickname" : "Titans", "conference": "AFC", "division": "South", "shopId" : "Tennessee_Titans_Gear/source/bm-nflcom-Header-Titans", "facebook": "titans", "twitter": "tennesseetitans" },
        "DEN" : { "abbr" : "DEN", "url" : "http://www.denverbroncos.com/", "teamPage":"/teams/denverbroncos/profile?team=DEN", "city" : "Denver", "nickname" : "Broncos", "conference": "AFC", "division": "West", "shopId" : "Denver_Broncos_Gear/source/bm-nflcom-Header-Broncos", "facebook": "DenverBroncos", "twitter": "Denver_Broncos" },
        "KC" : { "abbr" : "KC", "url" : "http://www.kcchiefs.com/", "teamPage":"/teams/kansascitychiefs/profile?team=KC", "city" : "Kansas City", "nickname" : "Chiefs", "conference": "AFC", "division": "West", "shopId" : "Kansas_City_Chiefs_Gear/source/bm-nflcom-Header-Chiefs", "facebook": "KansasCityChiefs", "twitter": "kcchiefs" },
        "OAK" : { "abbr" : "OAK", "url" : "http://www.raiders.com/", "teamPage":"/teams/oaklandraiders/profile?team=OAK", "city" : "Oakland", "nickname" : "Raiders", "conference": "AFC", "division": "West", "shopId" : "Oakland_Raiders_Gear/source/bm-nflcom-Header-Raiders", "facebook": "Raiders", "twitter": "RAIDERS" },
        "SD" : { "abbr" : "SD", "url" : "http://www.chargers.com/", "teamPage":"/teams/sandiegochargers/profile?team=SD", "city" : "San Diego", "nickname" : "Chargers", "conference": "AFC", "division": "West", "shopId" : "San_Diego_Chargers_Gear/source/bm-nflcom-Header-Chargers", "facebook": "chargers", "twitter": "chargers" },
        "DAL" : { "abbr" : "DAL", "url" : "http://www.dallascowboys.com/", "teamPage":"/teams/dallascowboys/profile?team=DAL", "city" : "Dallas", "nickname" : "Cowboys", "conference": "NFC", "division": "East", "shopId" : "Dallas_Cowboys_Gear/source/bm-nflcom-Header-Cowboys", "facebook": "DallasCowboys", "twitter": "dallascowboys" },
        "NYG" : { "abbr" : "NYG", "url" : "http://www.giants.com/", "teamPage":"/teams/newyorkgiants/profile?team=NYG", "city" : "New York", "nickname" : "Giants", "conference": "NFL", "division": "East", "shopId" : "New_York_Giants_Gear/source/bm-nflcom-Header-Giants", "facebook": "newyorkgiants", "twitter": "Giants" },
        "PHI" : { "abbr" : "PHI", "url" : "http://www.philadelphiaeagles.com/", "teamPage":"/teams/philadelphiaeagles/profile?team=PHI", "city" : "Philadelphia", "nickname" : "Eagles", "conference": "NFC", "division": "East", "shopId" : "Philadelphia_Eagles_Gear/source/bm-nflcom-Header-Eagles", "facebook": "philadelphiaeagles", "twitter": "Eagles" },
        "WAS" : { "abbr" : "WAS", "url" : "http://www.redskins.com/", "teamPage":"/teams/washingtonredskins/profile?team=WAS", "city" : "Washington", "nickname" : "Redskins", "conference": "NFC", "division": "East", "shopId" : "Washington_Redskins_Gear/source/bm-nflcom-Header-Redskins", "facebook": "redskins", "twitter": "Redskins" },
        "CHI" : { "abbr" : "CHI", "url" : "http://www.chicagobears.com/", "teamPage":"/teams/chicagobears/profile?team=CHI", "city" : "Chicago", "nickname" : "Bears", "conference": "NFC", "division": "North", "shopId" : "Chicago_Bears_Gear/source/bm-nflcom-Header-Bears", "facebook": "ChicagoBears", "twitter": "ChicagoBears" },
        "DET" : { "abbr" : "DET", "url" : "http://www.detroitlions.com/", "teamPage":"/teams/detroitlions/profile?team=DET", "city" : "Detroit", "nickname" : "Lions", "conference": "NFC", "division": "North", "shopId" : "Detroit_Lions_Gear/source/bm-nflcom-Header-Lions", "facebook": "DetroitLions", "twitter": "DetroitLionsNFL" },
        "GB" : { "abbr" : "GB", "url" : "http://www.packers.com/", "teamPage":"/teams/greenbaypackers/profile?team=GB", "city" : "Green Bay", "nickname" : "Packers", "conference": "NFC", "division": "North", "shopId" : "Green_Bay_Packers_Gear/source/bm-nflcom-Header-Packers", "facebook": "Packers", "twitter": "packers" },
        "MIN" : { "abbr" : "MIN", "url" : "http://www.vikings.com/", "teamPage":"/teams/minnesotavikings/profile?team=MIN", "city" : "Minnesota", "nickname" : "Vikings", "conference": "NFC", "division": "North", "shopId" : "Minnesota_Vikings_Gear/source/bm-nflcom-Header-Vikings", "facebook": "minnesotavikings", "twitter": "VikingsFootball" },
        "ATL" : { "abbr" : "ATL", "url" : "http://www.atlantafalcons.com/", "teamPage":"/teams/atlantafalcons/profile?team=ATL", "city" : "Atlanta", "nickname" : "Falcons", "conference": "NFC", "division": "South", "shopId" : "Atlanta_Falcons_Gear/source/bm-nflcom-Header-Falcons", "facebook": "atlantafalcons", "twitter": "Atlanta_Falcons" },
        "CAR" : { "abbr" : "CAR", "url" : "http://www.panthers.com/", "teamPage":"/teams/carolinapanthers/profile?team=CAR", "city" : "Carolina", "nickname" : "Panthers", "conference": "NFC", "division": "South", "shopId" : "Carolina_Panthers_Gear/source/bm-nflcom-Header-Panthers", "facebook": "CarolinaPanthers", "twitter": "Panthers" },
        "NO" : { "abbr" : "NO", "url" : "http://www.neworleanssaints.com/", "teamPage":"/teams/neworleanssaints/profile?team=NO", "city" : "New Orleans", "nickname" : "Saints", "conference": "NFC", "division": "South", "shopId" : "New_Orleans_Saints_Gear/source/bm-nflcom-Header-Saints", "facebook": "neworleanssaints", "twitter": "Saints" },
        "TB" : { "abbr" : "TB", "url" : "http://www.buccaneers.com/", "teamPage":"/teams/tampabaybuccaneers/profile?team=TB", "city" : "Tampa Bay", "nickname" : "Buccaneers", "conference": "NFC", "division": "South", "shopId" : "Tampa_Bay_Buccaneers_Gear/source/bm-nflcom-Header-Bucs", "facebook": "tampabaybuccaneers", "twitter": "TBBuccaneers" },
        "ARI" : { "abbr" : "ARI", "url" : "http://www.azcardinals.com/", "teamPage":"/teams/arizonacardinals/profile?team=ARI", "city" : "Arizona", "nickname" : "Cardinals", "conference": "NFC", "division": "West", "shopId" : "Arizona_Cardinals_Gear/source/bm-nflcom-Header-Cardinals", "facebook": "arizonacardinals", "twitter": "AZCardinals" },
        "STL" : { "abbr" : "STL", "url" : "http://www.stlouisrams.com/", "teamPage":"/teams/st.louisrams/profile?team=STL", "city" : "St. Louis", "nickname" : "Rams", "conference": "NFC", "division": "West", "shopId" : "St_Louis_Rams/source/bm-nflcom-Header-Rams", "facebook": "Rams", "twitter": "STLouisRams" },
        "SF" : { "abbr" : "SF", "url" : "http://www.sf49ers.com/", "teamPage":"/teams/sanfrancisco49ers/profile?team=SF", "city" : "San Francisco", "nickname" : "49ers", "conference": "NFC", "division": "West", "shopId" : "San_Francisco_49ers_Gear/source/bm-nflcom-Header-49ers", "facebook": "SANFRANCISCO49ERS", "twitter": "49ers" },
        "SEA" : { "abbr" : "SEA", "url" : "http://www.seahawks.com/", "teamPage":"/teams/seattleseahawks/profile?team=SEA", "city" : "Seattle", "nickname" : "Seahawks", "conference": "NFC", "division": "West", "shopId" : "Seattle_Seahawks_Gear/source/bm-nflcom-Header-Seahawks", "facebook": "Seahawks", "twitter": "Seahawks" }
      };
      
function fetchRoster(url) {
  
  var request = client.request('GET', '/',{'host': url});
request.on('response', function (response) {
    response.setEncoding('utf8');

    var body = "";
    response.on('data', function (chunk) {
        body = body + chunk;
    });

    response.on('end', function() {
         console.log(url)
        // now we have the whole body, parse it and select the nodes we want...
        var handler = new htmlparser.DefaultHandler(function(err, dom) {
            if (err) {
                sys.debug("Error: " + err);
            } else {
                 
                var map = {}
                var players = select(dom, 'table');
                console.log(players)
                players.forEach(function(player) {
                    console.log(player)
                })
                //console.log(map)
                 
            }
        });

        var parser = new htmlparser.Parser(handler);
        parser.parseComplete(body);
        console.log(body)
    });
});
request.end();
   
}

Object.keys(teams).forEach(function(key) {
  var team = teams[key]
  var url = host + team.teamPage.replace("profile", "roster");
  fetchRoster(url);
});

exports.module = {
  
}