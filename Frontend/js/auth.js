const BASE_URL = window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1"
  ? "http://localhost:8080/api/v1"
  : "https://ai-content-repurposer.onrender.com/api/v1";

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

/* ── Interactive Background Dot Grid for Auth Pages ── */
function createDotGrid() {
  let container = document.querySelector('.dots-container');
  if (container) container.remove();

  container = document.createElement('div');
  container.className = 'dots-container';
  document.body.prepend(container);

  const cellSize = 32;
  const cols = Math.ceil(window.innerWidth / cellSize);
  const rows = Math.ceil(window.innerHeight / cellSize);

  for (let r = 0; r < rows; r++) {
    for (let c = 0; c < cols; c++) {
      const dot = document.createElement('div');
      dot.className = 'interactive-dot';
      dot.style.left = `${c * cellSize + cellSize / 2}px`;
      dot.style.top = `${r * cellSize + cellSize / 2}px`;
      container.appendChild(dot);
    }
  }
}

window.addEventListener('DOMContentLoaded', createDotGrid);
window.addEventListener('resize', () => {
  clearTimeout(window.resizeDotsTimer);
  window.resizeDotsTimer = setTimeout(createDotGrid, 200);
});

/* ── Cursor Follow Spotlight Shade for Auth Pages ── */
document.addEventListener("mousemove", (e) => {
  const bg = document.body;
  bg.style.setProperty("--mouse-x", `${e.clientX}px`);
  bg.style.setProperty("--mouse-y", `${e.clientY}px`);
});