document.addEventListener("DOMContentLoaded", () => {
    const searchInput = document.getElementById("location");
    searchInput.addEventListener("input", onInput);
});

async function onInput() {
    const query = document.getElementById("location").value;
    console.log("searching for " + query);
    const res = await fetch(`https://api-adresse.data.gouv.fr/search/?q=${encodeURIComponent(query)}`);
    if (res.ok) {
        const result = await res.json();
        console.log(result.features);
        const suggestions = result.features.map(feature => feature.properties.label);
        const el = document.querySelector("#location-list");
        for (const suggestion of suggestions) {
            el.innerHTML += `<option value="${suggestion}" />`;
        }
    } else {
        console.log("Error: " + res.status);
    }
}