console.log("main.js loaded");

window.addEventListener('DOMContentLoaded', () => {
    console.log("DOM ready");

    const menu = document.getElementById('menu');
	
    if (!menu) {
        console.error("Menu not found!");
        return;
    }

    menu.addEventListener('click', function (e) {
        let target = e.target;
		console.log("Raw clicked element:", target);
        // If user clicks inside the <a>, walk up to find the <a> element
        while (target && target.tagName !== 'A') {
            target = target.parentElement;
        }

        if (target && target.tagName === 'A') {
            e.preventDefault();

            const action = target.dataset.action;
            console.log("Clicked link for action:", action);

            if (action) {
                loadPage(action);
				if (action === "viewAccount") {
				    attachViewAccountDeleteHandler();
				}
            } else {
                console.warn("No data-action found on clicked link.");
            }
        }
    });
	//Body Listener
	document.body.addEventListener("click", function (e) {
		let el = e.target;

		while (el && !el.dataset.action && el !== document.body) {
			el = el.parentElement;
		}

		const action = el.dataset.action;
		if (action) {
			e.preventDefault();
			console.log("Global click: loading action:", action);
			loadPage(action);
		}
	});
    // Load default content
    loadPage('home');
});

function loadPage(action) {
    console.log("Loading:", action);
	if (action === "listAccounts") {
	        const user = getDecodedCookie("user");
	        if (user) {
	            action += `&username=${encodeURIComponent(user)}`;
	        }
	    }
    fetch('Controller?action=' + action)
        .then(response => {
            if (!response.ok) throw new Error("Fetch failed with status " + response.status);
            return response.text();
        })
        .then(html => {
            const content = document.getElementById('content');
            if (content) {
                content.innerHTML = html;
				handleDynamicAccountButtons();
            } 
			else {
                console.error("Content container not found!");
            }
			if (action === "viewAccount") {
				attachViewAccountDeleteHandler();
			}
			// handle actions for login POST and button to login page
			if (action === 'login') {
			        handleLoginForm();
			    }

			    if (action === 'home') {
			        handleLoginButtonOnHomePage();
			    }
        })
		// If action not caught throw error.
        .catch(error => {
            console.error("Fetch error:", error);
            const content = document.getElementById('content');
            if (content) {
                content.innerHTML = "<p>Error loading content.</p>";
            }
        });
}
function handleDynamicAccountButtons() {
    const content = document.getElementById('content');
    if (!content) return;

    content.addEventListener('click', (e) => {
        if (e.target && e.target.classList.contains('view-account-btn')) {
            const id = e.target.dataset.id;
            if (id) {
                loadPage(`viewAccount&id=${id}`);
            }
        }
    });
}
function handleLoginForm() {
    const form = document.getElementById("loginForm");
    if (!form) return;

    form.addEventListener("submit", (e) => {
        e.preventDefault();

        const formData = new FormData(form);
        const username = formData.get("username");
		const select = form.querySelector("#username");
		const selectedOption = select.options[select.selectedIndex];

		// Get the custom data-id attribute
		const studentID = selectedOption.dataset.id;

        // Set cookie (expires in 1 day)
        document.cookie = `user=${encodeURIComponent(username)}; path=/; max-age=${60 * 60 * 24}`;
		document.cookie = `studentID=${studentID}; path=/; max-age=${60 * 60 * 24}`;
        // Redirect to home
        loadPage("home");
		window.location.reload(); 
    });
}
function handleLoginButtonOnHomePage() {
    const loginBtn = document.getElementById("loginButton");
    if (loginBtn) {
        loginBtn.addEventListener("click", () => {
            loadPage("login");
        });
    }
}
function attachViewAccountDeleteHandler() {
    const deleteBtn = document.getElementById("deleteAccountBtn");
    if (!deleteBtn) return;
	console.log("Delete button clicked");
    deleteBtn.addEventListener("click", () => {
        const accountId = deleteBtn.dataset.accountId;
        if (!accountId) return;

        const confirmed = confirm(`Are you sure you want to delete account #${accountId}? This action cannot be undone.`);

        if (confirmed) {
            fetch("Controller?action=deleteAccount", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: `accountID=${encodeURIComponent(accountId)}`
            })
            .then(res => {
                if (!res.ok) throw new Error("Delete failed");
                // No need to read the response body
                alert("Account deleted successfully.");
                loadPage("listAccounts"); // Dynamically load list again
            })
            .catch(err => {
                alert("Error deleting account: " + err.message);
            });
        }
    });
}


function fetchContent(action) {
    fetch(`Controller?action=${action}`)
        .then(res => res.text())
        .then(html => {
            document.getElementById("main-content").innerHTML = html;
        })
        .catch(err => console.error("Error loading content:", err));
}
function logout() {
    document.cookie = "user=; path=/; max-age=0";
	document.cookie = "studentID=; path=/; max-age=0";
    loadPage("home");
	window.location.reload(); 
}
function getDecodedCookie(name) {
    const cookies = document.cookie.split(';');

    for (let cookie of cookies) {
        const [key, value] = cookie.trim().split('=');
        if (key === name) {
            return decodeURIComponent(value);
        }
    }

    return null;
}