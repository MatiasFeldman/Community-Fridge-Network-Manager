document.addEventListener("DOMContentLoaded", function() {
    const profileIcon = document.getElementById("profile-icon");
    const profileDropdown = document.getElementById("profile-dropdown");
    const userNameDisplay = document.getElementById("user-name");


    const userRole = localStorage.getItem("userRole");
    const userName = localStorage.getItem("userName");


    if (userRole && userName) {

        userNameDisplay.textContent = userName;
        userNameDisplay.style.display = "inline";
        profileDropdown.style.display = "none";


        profileIcon.addEventListener("click", function(e) {
            e.preventDefault();
            if (profileDropdown.style.display === "none" || profileDropdown.style.display === "") {
                profileDropdown.style.display = "block";
            } else {
                profileDropdown.style.display = "none";
            }
        });


        document.getElementById("logout-btn").addEventListener("click", function() {

            localStorage.removeItem("userRole");
            localStorage.removeItem("userName");

            window.location.href = "/login";
        });

    } else {
        profileIcon.addEventListener("click", function(e) {
            e.preventDefault();
            window.location.href = "/login";
        });
    }

    document.addEventListener("click", function(event) {
        if (!profileIcon.contains(event.target) && !profileDropdown.contains(event.target)) {
            profileDropdown.style.display = "none";
        }
    });
});

