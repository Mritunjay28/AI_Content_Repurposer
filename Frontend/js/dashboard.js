const BASE_URL = window.location.hostname === "localhost" || window.location.hostname === "127.0.0.1"
  ? "http://localhost:8080/api/v1"
  : "https://ai-content-repurposer.onrender.com/api/v1";

const token = localStorage.getItem("token");

if (!token) window.location = "login.html";

window.onload = loadHistory;

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

function setOutput(twitterThread, linkedinPost, blogSummary) {
  const fields = [
    { id: "twitterThread",  value: twitterThread },
    { id: "linkedinPost",   value: linkedinPost  },
    { id: "blogSummary",    value: blogSummary   },
  ];

  fields.forEach(({ id, value }) => {
    const el = document.getElementById(id);
    if (!el) return;
    el.textContent = value || "";

    // Reset copy button
    const card = el.closest(".output-card");
    if (card) {
      const btn = card.querySelector(".copy-btn");
      if (btn) {
        btn.textContent = "Copy";
        btn.classList.remove("copied");
      }
    }
  });
}

function copyContent(fieldId) {
  const el = document.getElementById(fieldId);
  const text = el ? el.textContent : "";
  if (!text) return;

  navigator.clipboard.writeText(text).then(() => {
    const card = el.closest(".output-card");
    const btn  = card ? card.querySelector(".copy-btn") : null;
    if (btn) {
      btn.textContent = "Copied ✓";
      btn.classList.add("copied");
      setTimeout(() => {
        btn.textContent = "Copy";
        btn.classList.remove("copied");
      }, 2000);
    }
  });
}

async function generateContent() {
  const youtubeUrl = document.getElementById("youtubeUrl").value.trim();
  if (!youtubeUrl) {
    showToast("Paste a YouTube URL first.", "error");
    return;
  }

  const btn = document.getElementById("generateBtn");
  btn.classList.add("loading");
  btn.disabled = true;

  try {
    const response = await fetch(BASE_URL + "/content/generate", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token,
      },
      body: JSON.stringify({ youtubeUrl }),
    });

    if (response.status === 401) {
      localStorage.removeItem("token");
      window.location = "login.html";
      return;
    }

    if (!response.ok) {
      showToast("Generation failed. Try again.", "error");
      return;
    }

    const data = await response.json();
    setOutput(data.twitterThread, data.linkedinPost, data.blogSummary);
    showToast("Content generated!", "success");
    loadHistory();
  } catch {
    showToast("Could not reach server.", "error");
  } finally {
    btn.classList.remove("loading");
    btn.disabled = false;
  }
}

async function loadHistory() {
  try {
    const response = await fetch(BASE_URL + "/content/history", {
      headers: { "Authorization": "Bearer " + token },
    });

    if (!response.ok) return;

    const histories = await response.json();
    const container = document.getElementById("historyContainer");

    if (!container) return;
    container.innerHTML = "";

    if (!histories || histories.length === 0) {
      container.innerHTML = `<p class="sidebar-empty">No history yet.<br>Generate your first post.</p>`;
      return;
    }

    histories.forEach((history) => {
      const item = document.createElement("div");
      item.className = "history-item";
      item.setAttribute("data-id", history.id);

      let label = history.youtubeUrl || "Unknown URL";
      try {
        const u = new URL(history.youtubeUrl);
        const v = u.searchParams.get("v");
        label = v ? `youtu.be/${v}` : label;
      } catch {}

      item.innerHTML = `
        <div class="history-item-icon">▶</div>
        <span class="history-item-url" title="${history.youtubeUrl}">${label}</span>
        <button class="history-item-delete" title="Delete history item">🗑</button>
      `;

      item.addEventListener("click", () => {
        document.querySelectorAll(".history-item").forEach((el) => el.classList.remove("active"));
        item.classList.add("active");
        loadHistoryItem(history.id);
      });

      const deleteBtn = item.querySelector(".history-item-delete");
      deleteBtn.addEventListener("click", async (e) => {
        e.stopPropagation();
        if (confirm("Are you sure you want to delete this history item?")) {
          await deleteHistoryItem(history.id, item);
        }
      });

      container.appendChild(item);
    });
  } catch {
    /* silently skip sidebar errors */
  }
}

async function loadHistoryItem(id) {
  try {
    const response = await fetch(`${BASE_URL}/content/history/${id}`, {
      headers: { "Authorization": "Bearer " + token },
    });

    if (!response.ok) {
      showToast("Could not load history item.", "error");
      return;
    }

    const data = await response.json();
    setOutput(data.twitterThread, data.linkedinPost, data.blogSummary);
  } catch {
    showToast("Could not reach server.", "error");
  }
}

function clearOutput() {
  setOutput("", "", "");
  const placeholders = {
    twitterThread: "Your Twitter thread will appear here…",
    linkedinPost: "Your LinkedIn post will appear here…",
    blogSummary: "Your blog summary will appear here…"
  };
  for (const [id, val] of Object.entries(placeholders)) {
    const el = document.getElementById(id);
    if (el) {
      el.innerHTML = `<span class="placeholder-text">${val}</span>`;
    }
  }
}

async function deleteHistoryItem(id, itemElement) {
  try {
    const response = await fetch(`${BASE_URL}/content/history/${id}`, {
      method: "DELETE",
      headers: { "Authorization": "Bearer " + token },
    });

    if (response.status === 401) {
      localStorage.removeItem("token");
      window.location = "login.html";
      return;
    }

    if (!response.ok) {
      showToast("Could not delete history item.", "error");
      return;
    }

    showToast("History item deleted.", "success");

    if (itemElement.classList.contains("active")) {
      clearOutput();
    }

    loadHistory();
  } catch {
    showToast("Could not reach server.", "error");
  }
}

function logout() {
  localStorage.removeItem("token");
  window.location = "login.html";
}

/* ── Cursor Follow Spotlight Shade for Dashboard Background ── */
document.addEventListener("mousemove", (e) => {
  const bg = document.body;
  bg.style.setProperty("--mouse-x", `${e.clientX}px`);
  bg.style.setProperty("--mouse-y", `${e.clientY}px`);
});