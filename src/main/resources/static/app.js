const input = document.getElementById("username");
const button = document.getElementById("roastBtn");
const status = document.getElementById("status");
const result = document.getElementById("result");

button.addEventListener("click", async () => {
    const username = input.value.trim();

    if (!username) {
        alert("Enter a GitHub username");
        return;
    }

    status.textContent = "Roasting...";
    result.textContent = "";

    try {
        const res = await fetch(`/api/roast/${username}`);

        if (!res.ok) {
            throw new Error("Server error");
        }

        const data = await res.json();

        result.innerHTML = `
            <p>${data.roast}</p>
            <ul>
                <li>⭐ Stars: ${data.stats.stars}</li>
                <li>🏆 Best Repo: ${data.stats.bestRepo}</li>
                <li>💀 Worst Repo: ${data.stats.worstRepo}</li>
                <li>🧠 Language: ${data.stats.primaryLanguage}</li>
            </ul>
        `;

        status.textContent = "";
    } catch (err) {
        status.textContent = "Failed to roast 😢";
        console.error(err);
    }
});
