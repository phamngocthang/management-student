const body = document.querySelector("body");
const modeToggle = body.querySelector(".mode-toggle");
const sidebar = body.querySelector("nav");
const sidebarToggle = body.querySelector(".sidebar-toggle");

modeToggle.addEventListener("click", () => {
  body.classList.toggle("dark");
});

sidebarToggle.addEventListener("click", () => {
  sidebar.classList.toggle("close");
});
