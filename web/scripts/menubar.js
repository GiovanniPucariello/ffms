/* Function for showing and hiding elements that use 'display:none' to hide */
function toggleDisplay(targetId) {
    if (document.getElementById) {
        target = document.getElementById(targetId);
        if (target.style.display == "none"){
            target.style.display = "";
        } else {
            target.style.display = "none";
        }
    }
}
window.onload = initializeMenus;