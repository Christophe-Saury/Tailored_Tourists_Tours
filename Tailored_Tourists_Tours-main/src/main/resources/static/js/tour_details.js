document.addEventListener("DOMContentLoaded", async function () {
    const urlParams = new URLSearchParams(window.location.search);

    /**
     * @type {{[p: string]: string|number|number[]}}
     */
    const body = Object.fromEntries([...urlParams.entries()]
        .map(([key, value]) => [key, isNaN(+value) ? value : +value]));

    const [x, y] = await getCoordinates(body.location);
    body.posX = x;
    body.posY = y;

    // get list of centuries
    let to =0;
    let from = 0;
    if(body.siecle1>body.siecle2){
        to = body.siecle2;
        from = body.siecle1;
    } else {
        to = body.siecle1;
        from = body.siecle2;
    }
    const centuries = [];
    for(let i = to; i <= from; i++) {
        centuries.push(i);
    }
    body.centuries = centuries;

    // get nb of days
    body.nbDays = body.duration;
    body.destinationCount = body.maxNbOfSites;

    console.log(body);
    const result = await fetch("/api/compute-trip", {
        method: "POST",
        body: JSON.stringify(body),
        headers: {
            "Content-Type" : "application/json"
        }
    });
    if (result.ok) {
        const tours = await result.json();
        console.log(tours);
        displayTourDetails(tours);
    } else {
        alert("Error: " + result.status);
    }
});

function displayTourDetails(tours) {
    var tourName = tours.tours[0]["name"];


    var tourDetailsSection = document.querySelector(".tour-details");

    document.getElementById("tour-name").innerHTML = tourName;


    var days = tours.tours[0]["days"]; // Get the array of days

    var siteListDiv = createSiteList();

    for (var i = 0; i < days.length; i++) {
        var day = days[i];

        var dayDiv = createDayDiv(i + 1);
        var daySitesList = createDaySitesList(day["sites"]);

        dayDiv.appendChild(daySitesList);

        var viewItineraryButton = createViewItineraryButton(i + 1);
        dayDiv.appendChild(viewItineraryButton);

        siteListDiv.appendChild(dayDiv);
    }

    tourDetailsSection.appendChild(siteListDiv);

    function createSiteList() {
        var siteListDiv = document.createElement("div");
        siteListDiv.className = "site-list";
        return siteListDiv;
    }

    function createDayDiv(dayNumber) {
        var dayDiv = document.createElement("div");
        dayDiv.className = "day";

        var dayHeader = document.createElement("h4");
        dayHeader.innerHTML = "Day " + dayNumber;

        dayDiv.appendChild(dayHeader);
        return dayDiv;
    }

    function createDaySitesList(sites) {
        var daySitesList = document.createElement("ul");

        for (var j = 0; j < sites.length; j++) {
            var site = sites[j];

            var siteListItem = document.createElement("li");

            var siteDetails = document.createElement("div");
            siteDetails.className = "site-details";
            var siteName = document.createElement("h5");
            siteName.innerHTML = site["name"];
            var siteDescription = document.createElement("p");
            siteDescription.innerHTML = site["description"];
            var siteLocation = document.createElement("p");
            siteLocation.innerHTML = site["localisation"];
            siteLocation.classList.add("site-location");

            siteDetails.appendChild(siteName);
            siteDetails.appendChild(siteDescription);
            siteDetails.appendChild(siteLocation);

            siteListItem.appendChild(siteDetails);

            daySitesList.appendChild(siteListItem);
        }

        return daySitesList;
    }

    function createViewItineraryButton(dayNumber) {
        var buttonContainer = document.createElement("div");
        buttonContainer.className = "itinerary-button-container";

        var button = document.createElement("a");
        button.className = "itinerary-button";
        button.href = "Map_Itinerary.html?day=" + dayNumber;
        button.innerHTML = "View Itinerary";

        var hiddenInput = document.createElement("input");
        hiddenInput.type = "hidden";
        hiddenInput.name = "day";
        hiddenInput.value = dayNumber;

        button.appendChild(hiddenInput);
        buttonContainer.appendChild(button);

        return buttonContainer;
    }

    // Hide the loading message
    var loadingMessage = document.getElementById("loading-message");
    loadingMessage.style.display = "none";
}


async function getCoordinates(city) {
    const res = await fetch(`https://api-adresse.data.gouv.fr/search/?q=${encodeURIComponent(city)}`);
    if (res.ok) {
        const result = await res.json();
        return [result.features[0].properties.x, result.features[0].properties.y];
    } else {
        console.log("Error: " + res.status);
    }
}