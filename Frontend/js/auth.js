const BASE_URL = "http://localhost:8080/api/v1";

function showToast(message, type = "success") {
  const existing = document.querySelector(".toast");
  if (existing) existing.remove();

  const toast = document.createElement("div");
  toast.className = `toast ${type}`;
  toast.textContent = message;
  document.body.appendChild(toast);

  requestAnimationFrame(() => {
    requestAnimationFrame(() => toast.classList.add("show"));
  });

  setTimeout(() => {
    toast.classList.remove("show");
    setTimeout(() => toast.remove(), 300);
  }, 3500);
}

async function registerUser() {
  const btn = document.getElementById("registerBtn");
  const body = {
    username: document.getElementById("username").value.trim(),
    email:    document.getElementById("email").value.trim(),
    password: document.getElementById("password").value,
  };

  if (!body.username || !body.email || !body.password) {
    showToast("Please fill in all fields.", "error");
    return;
  }

  btn.disabled = true;
  btn.innerHTML = `<span class="spinner" style="display:inline-block"></span> Creating account…`;

  try {
    const response = await fetch(BASE_URL + "/auth/signup", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    const text = await response.text();

    if (response.ok) {
      showToast("Account created! Redirecting to login…", "success");
      setTimeout(() => (window.location = "login.html"), 1500);
    } else {
      showToast(text || "Registration failed.", "error");
      btn.disabled = false;
      btn.innerHTML = "Create account";
    }
  } catch {
    showToast("Could not reach server. Try again.", "error");
    btn.disabled = false;
    btn.innerHTML = "Create account";
  }
}

async function login() {
  const btn = document.getElementById("loginBtn");
  const body = {
    username: document.getElementById("username").value.trim(),
    password: document.getElementById("password").value,
  };

  if (!body.username || !body.password) {
    showToast("Please fill in all fields.", "error");
    return;
  }

  btn.disabled = true;
  btn.innerHTML = `<span class="spinner" style="display:inline-block"></span> Signing in…`;

  try {
    const response = await fetch(BASE_URL + "/auth/signin", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body),
    });

    if (response.ok) {
      const token = await response.text();
      localStorage.setItem("token", token);
      window.location = "dashboard.html";
    } else {
      const text = await response.text();
      showToast(text || "Invalid credentials.", "error");
      btn.disabled = false;
      btn.innerHTML = "Sign in";
    }
  } catch {
    showToast("Could not reach server. Try again.", "error");
    btn.disabled = false;
    btn.innerHTML = "Sign in";
  }
}