async function fetchTours() {
    try {
        const response = await fetch("data.json");
        const tours = await response.json();
        generateTourCards(tours);
    } catch (error) {
        console.log("Error fetching tours:", error);
    }
}


// Call fetchTours to start fetching and generating tour cards
fetchTours();



function createTourCard(tour) {
    const tourCard = document.createElement("div");
    tourCard.classList.add("tour-card");
    tourCard.id = "tour" + tour.id;

    const tourTitle = document.createElement("h3");
    tourTitle.textContent = tour.name;
    tourCard.appendChild(tourTitle);

    const tourDays = document.createElement("p");
    tourDays.textContent = `Number of days: ${tour.nbOfDays}`;
    tourCard.appendChild(tourDays);

    const includedSites = document.createElement("p");
    includedSites.textContent = "Included Sites:";
    tourCard.appendChild(includedSites);

    const siteList = document.createElement("ul");
    tourCard.appendChild(siteList);

    tour.days.forEach(day => {
        day.sites.forEach(site => {
            const siteItem = document.createElement("li");
            siteItem.textContent = site.name;
            siteList.appendChild(siteItem);
        });
    });

    const viewDetailsForm = createViewDetailsForm(tour.id);
    tourCard.appendChild(viewDetailsForm);

    return tourCard;
}

function createViewDetailsForm(tourId) {
    const form = document.createElement("form");
    form.method = "POST";
    form.action = "tour_details.html";

    const tourNumberInput = document.createElement("input");
    tourNumberInput.type = "hidden";
    tourNumberInput.name = "tourNumber";
    tourNumberInput.value = tourId;
    form.appendChild(tourNumberInput);

    const viewDetailsButton = document.createElement("button");
    viewDetailsButton.type = "submit";
    viewDetailsButton.textContent = "View Details";
    viewDetailsButton.classList.add("view-details-button");
    form.appendChild(viewDetailsButton);

    return form;
}

function generateTourCards(tours) {
    const tourList = document.querySelector(".tour-list");

    tours.forEach(tour => {
        const tourCard = createTourCard(tour);
        tourList.appendChild(tourCard);
    });
}

// Usage
const tours = [{
        "id": 1,
        "name": "Historical Paris",
        "nbOfDays": 4,
        "description": "Discover the rich history of Paris with this guided tour. Visit iconic landmarks and explore historical sites.",
        "days": [
            {
                "id": 1,
                "area": "Eiffel Tower",
                "distanceTravelled": "10 km",
                "nbOfSites": 3,
                "sites": [
                    {
                        "id": 1,
                        "name": "Eiffel Tower",
                        "localisation": "Champ de Mars, 5 Avenue Anatole France, 75007 Paris",
                        "historicalPeriod": "19th century",
                        "description": "The iconic landmark of Paris, offering breathtaking views of the city from its observation decks.",
                        "type": "Monument"
                    },
                    {
                        "id": 2,
                        "name": "Louvre Museum",
                        "localisation": "Rue de Rivoli, 75001 Paris",
                        "historicalPeriod": "12th century",
                        "description": "The world's largest art museum and a historic monument. Home to thousands of artworks including the famous Mona Lisa.",
                        "type": "Museum"
                    },
                    {
                        "id": 3,
                        "name": "Notre-Dame Cathedral",
                        "localisation": "6 Parvis Notre-Dame - Pl. Jean-Paul II, 75004 Paris",
                        "historicalPeriod": "12th century",
                        "description": "A masterpiece of Gothic architecture, known for its stunning stained glass windows and intricate details.",
                        "type": "Religious Site"
                    }
                ]
            },
            {
                "id": 2,
                "area": "Palace of Versailles",
                "distanceTravelled": "30 km",
                "nbOfSites": 2,
                "sites": [
                    {
                        "id": 4,
                        "name": "Palace of Versailles",
                        "localisation": "Place d'Armes, 78000 Versailles",
                        "historicalPeriod": "17th century",
                        "description": "A grand royal palace known for its opulent interiors, stunning gardens, and historical significance.",
                        "type": "Palace"
                    },
                    {
                        "id": 5,
                        "name": "The Hall of Mirrors",
                        "localisation": "Palace of Versailles, 78000 Versailles",
                        "historicalPeriod": "17th century",
                        "description": "A magnificent hall lined with mirrors, showcasing the artistic and architectural prowess of the time.",
                        "type": "Historical Site"
                    }
                ]
            }
        ]
    },
        {
            "id": 2,
            "name": "Artistic Paris",
            "nbOfDays": 3,
            "description": "Immerse yourself in the vibrant art scene of Paris. Visit world-renowned museums and explore artistic neighborhoods.",
            "days": [
                {
                    "id": 1,
                    "area": "Louvre Museum",
                    "distanceTravelled": "5 km",
                    "nbOfSites": 3,
                    "sites": [
                        {
                            "id": 2,
                            "name": "Louvre Museum",
                            "localisation": "Rue de Rivoli, 75001 Paris",
                            "historicalPeriod": "12th century",
                            "description": "The world's largest art museum and a historic monument. Home to thousands of artworks including the famous Mona Lisa.",
                            "type": "Museum"
                        },
                        {
                            "id": 6,
                            "name": "Musée d'Orsay",
                            "localisation": "1 Rue de la Légion d'Honneur, 75007 Paris",
                            "historicalPeriod": "19th century",
                            "description": "An art museum housed in a former railway station, featuring a vast collection of Impressionist and Post-Impressionist masterpieces.",
                            "type": "Museum"
                        },
                        {
                            "id": 7,
                            "name": "Montmartre",
                            "localisation": "Montmartre, 75018 Paris",
                            "historicalPeriod": "18th century",
                            "description": "A bohemian neighborhood known for its artistic legacy, charming streets, and the iconic Sacré-Cœur Basilica.",
                            "type": "Neighborhood"
                        }
                    ]
                },
                {
                    "id": 2,
                    "area": "Centre Pompidou",
                    "distanceTravelled": "8 km",
                    "nbOfSites": 2,
                    "sites": [
                        {
                            "id": 8,
                            "name": "Centre Pompidou",
                            "localisation": "Place Georges-Pompidou, 75004 Paris",
                            "historicalPeriod": "20th century",
                            "description": "A renowned contemporary art museum known for its unique architectural design and extensive modern art collection.",
                            "type": "Museum"
                        },
                        {
                            "id": 9,
                            "name": "Le Marais",
                            "localisation": "Le Marais, 75004 Paris",
                            "historicalPeriod": "12th century",
                            "description": "A historic district with a blend of medieval and contemporary charm. Explore its narrow streets, trendy boutiques, and historic landmarks.",
                            "type": "Neighborhood"
                        }
                    ]
                }
            ]
        }
    ];


generateTourCards(tours);
