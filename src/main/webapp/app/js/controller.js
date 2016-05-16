//global vars is a dirty style
var webSocket;
var map;

//Initialize websocket client endpoint and setting callbacks on his lifecycle ------------------------------------------
function initWebSocket(){
    webSocket = new WebSocket("ws://localhost:8180/signalmap");

    webSocket.onopen = function(event) {
         console.log("onopen");
    };

    webSocket.onmessage = function(event){
        console.log("onmessage");
        var sm = JSON.parse(event.data);

        var ci = sm.cellinfos.split(",");
        var providers = sm.providers.split(",");
        var checked = sm.checked;

        console.log("ci: " + ci);
        console.log("providers: " + providers);
        console.log("checked: " + checked);

        showMap(providers, checked);
    };

    webSocket.onclose = function(event){
        console.log("onclose");
    };

    webSocket.onerror = function(event){
        console.log("onerror: " + event.data);
    };
}

function sendQuery(cellinfo) {
    var smAction = {
        action: "inquire",
        cellInfo: cellinfo
    };
    webSocket.send(JSON.stringify(smAction));
}

function inquireMap(cellinfo){
    map.remove();
    sendQuery(cellinfo);
}


function showMap(providers, cellinfo){
    map = L.map('map').setView([53.2000600, 50.1500000], 6);
    mapLink = '<a href="http://openstreetmap.org">OpenStreetMap</a>';
    L.tileLayer(
        'http://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            attribution: '&copy; ' + mapLink + ' Contributors',
            maxZoom: 18
        }).addTo(map);



    var layers = [];
    layers.push('signalmap:' + cellinfo + '-all-bar');
    for (var i in providers) {
        layers.push('signalmap:' + cellinfo +'-' + providers[i] + '-bar');
    }

    console.log("layers: " + layers);

    var wmsLayer = L.tileLayer.wms('http://localhost:8080/geoserver/signalmap/wms?', {
        layers: layers, transparent:"true"
    }, {isBaseLayer:false}).addTo(map);

    var quakePoints = [
        [53.2000600, 50.1500000]
    ];

    var heat = L.heatLayer(quakePoints,{
        radius: 20,
        blur: 15,
        maxZoom: 17
    }).addTo(map);
}